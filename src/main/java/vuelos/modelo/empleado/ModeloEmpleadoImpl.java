package vuelos.modelo.empleado;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.ModeloImpl;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.beans.UbicacionesBeanImpl;
import vuelos.modelo.empleado.dao.DAOEmpleado;
import vuelos.modelo.empleado.dao.DAOEmpleadoImpl;
import vuelos.modelo.empleado.dao.DAOPasajero;
import vuelos.modelo.empleado.dao.DAOPasajeroImpl;
import vuelos.modelo.empleado.dao.DAOReserva;
import vuelos.modelo.empleado.dao.DAOReservaImpl;
import vuelos.modelo.empleado.dao.DAOVuelos;
import vuelos.modelo.empleado.dao.DAOVuelosImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOUbicacionesDatosPrueba;

public class ModeloEmpleadoImpl extends ModeloImpl implements ModeloEmpleado {

	private static Logger logger = LoggerFactory.getLogger(ModeloEmpleadoImpl.class);	

	
	private Integer legajo = null;
	
	public ModeloEmpleadoImpl() {
		logger.debug("Se crea el modelo Empleado.");
	}
	

	@Override
	public boolean autenticarUsuarioAplicacion(String legajo, String password) throws Exception {
		logger.info("Se intenta autenticar el legajo {} con password {}", legajo, password);
		
		boolean success = false;
		DAOEmpleado dao = new DAOEmpleadoImpl(this.conexion);
		EmpleadoBean empleado = null;
		MessageDigest md = MessageDigest.getInstance("MD5");
		String md5Pass;
		
		try {
			empleado = dao.recuperarEmpleado(Integer.parseInt(legajo));
		} catch (NumberFormatException e) {
			logger.debug("Error parseando legajo");
			return success;
		}
		
		md.update(password.getBytes());
		md5Pass = DatatypeConverter.printHexBinary(md.digest());
		
		if (empleado != null && (empleado.getPassword().equalsIgnoreCase(md5Pass))) {
			this.legajo = empleado.getLegajo();
			success = true;
		}
		return success;
	}
	
	@Override
	public ArrayList<String> obtenerTiposDocumento() {
		logger.info("recupera los tipos de documentos.");
		
		ArrayList<String> tipos = new ArrayList<String>();
		
		String sql = "SELECT DISTINCT doc_tipo FROM pasajeros";
		ResultSet rs = this.consulta(sql);
		
		try {
			while(rs.next()) {
				String tipo = rs.getString("doc_tipo");
				tipos.add(tipo);
			} 
			}catch (SQLException ex){
				logger.error("SQLException: " + ex.getMessage());
				logger.error("SQLState: " + ex.getSQLState());
				logger.error("VendorError: " + ex.getErrorCode());				   
				}	
		return tipos;
	}		
	
	@Override
	public EmpleadoBean obtenerEmpleadoLogueado() throws Exception {
		logger.info("Solicita al DAO un empleado con legajo {}", this.legajo);
		if (this.legajo == null) {
			logger.info("No hay un empleado logueado.");
			throw new Exception("No hay un empleado logueado. La sesión terminó.");
		}
		
		DAOEmpleado dao = new DAOEmpleadoImpl(this.conexion);
		return dao.recuperarEmpleado(this.legajo);
	}	

	@Override
	public ArrayList<UbicacionesBean> recuperarUbicaciones() throws Exception {

		logger.info("recupera las ciudades que tienen aeropuertos.");
		ArrayList<UbicacionesBean> ubicaciones = new ArrayList<UbicacionesBean>();

		String sql = "SELECT * FROM ubicaciones";
		try {
			ResultSet rs = this.consulta(sql);

			while(rs.next()) {
				UbicacionesBean ubicacion = new UbicacionesBeanImpl();
				ubicacion.setPais(rs.getString("pais"));
				ubicacion.setEstado(rs.getString("estado"));
				ubicacion.setCiudad(rs.getString("ciudad"));
				ubicacion.setHuso(rs.getInt("huso"));

				ubicaciones.add(ubicacion);
			}
			rs.close();
		} catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());		   
			throw new Exception("Error en la conexión con la BD.");
		}

		return ubicaciones;
	}


	@Override
	public ArrayList<InstanciaVueloBean> obtenerVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen, UbicacionesBean destino) throws Exception {
		
		logger.info("Recupera la lista de vuelos disponibles para la fecha {} desde {} a {}.", fechaVuelo, origen, destino);

		DAOVuelos dao = new DAOVuelosImpl(this.conexion);		
		return dao.recuperarVuelosDisponibles(fechaVuelo, origen, destino);
	}
	
	@Override
	public ArrayList<DetalleVueloBean> obtenerDetalleVuelo(InstanciaVueloBean vuelo) throws Exception {

		logger.info("Recupera la cantidad de asientos y precio del vuelo {} .", vuelo.getNroVuelo());
		
		DAOVuelos dao = new DAOVuelosImpl(this.conexion);		
		return dao.recuperarDetalleVuelo(vuelo);
	}


	@Override
	public PasajeroBean obtenerPasajero(String tipoDoc, int nroDoc) throws Exception {
		logger.info("Solicita al DAO un pasajero con tipo {} y nro {}", tipoDoc, nroDoc);
		
		DAOPasajero dao = new DAOPasajeroImpl(this.conexion);
		return dao.recuperarPasajero(tipoDoc, nroDoc);
	}


	@Override
	public ReservaBean reservarSoloIda(PasajeroBean pasajero, InstanciaVueloBean vuelo, DetalleVueloBean detalleVuelo)
			throws Exception {
		logger.info("Se solicita al modelo realizar una reserva solo ida");

		EmpleadoBean empleadoLogueado = this.obtenerEmpleadoLogueado();
		
		DAOReserva dao = new DAOReservaImpl(this.conexion);
		int nroReserva = dao.reservarSoloIda(pasajero, vuelo, detalleVuelo, empleadoLogueado);
		
		ReservaBean reserva = dao.recuperarReserva(nroReserva); 
		return reserva;
	}


	@Override
	public ReservaBean reservarIdaVuelta(PasajeroBean pasajeroSeleccionado, 
									 InstanciaVueloBean vueloIdaSeleccionado,
									 DetalleVueloBean detalleVueloIdaSeleccionado, 
									 InstanciaVueloBean vueloVueltaSeleccionado,
									 DetalleVueloBean detalleVueloVueltaSeleccionado) throws Exception {
		
		logger.info("Se solicita al modelo realizar una reserva de ida y vuelta");
		
		EmpleadoBean empleadoLogueado = this.obtenerEmpleadoLogueado();
		
		DAOReserva dao = new DAOReservaImpl(this.conexion);
		
		int nroReserva = dao.reservarIdaVuelta(pasajeroSeleccionado, 
									 vueloIdaSeleccionado, 
									 detalleVueloIdaSeleccionado, 
									 vueloVueltaSeleccionado, 
									 detalleVueloVueltaSeleccionado, 
									 empleadoLogueado);
		
		ReservaBean reserva = dao.recuperarReserva(nroReserva); 
		return reserva;		
	}
}
