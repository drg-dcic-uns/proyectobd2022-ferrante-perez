package vuelos.modelo.empleado.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.EmpleadoBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloClaseBean;
import vuelos.modelo.empleado.beans.InstanciaVueloClaseBeanImpl;
import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.ReservaBean;
import vuelos.modelo.empleado.beans.ReservaBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOReservaDatosPrueba;

public class DAOReservaImpl implements DAOReserva {

	private static Logger logger = LoggerFactory.getLogger(DAOReservaImpl.class);
	
	//conexión para acceder a la Base de Datos
	private Connection conexion;
	
	public DAOReservaImpl(Connection conexion) {
		this.conexion = conexion;
	}
		
	
	@Override
	public int reservarSoloIda(PasajeroBean pasajero, 
							   InstanciaVueloBean vuelo, 
							   DetalleVueloBean detalleVuelo,
							   EmpleadoBean empleado) throws Exception {
		logger.info("Realiza la reserva de solo ida con pasajero {}", pasajero.getNroDocumento());
		
		/**
		 * TODO (parte 2) Realizar una reserva de ida solamente llamando al Stored Procedure (S.P.) correspondiente. 
		 *      Si la reserva tuvo exito deberá retornar el número de reserva. Si la reserva no tuvo éxito o 
		 *      falla el S.P. deberá propagar un mensaje de error explicativo dentro de una excepción.
		 *      La demás excepciones generadas automáticamente por algun otro error simplemente se propagan.
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 *		
		 * 
		 * @throws Exception. Deberá propagar la excepción si ocurre alguna. Puede capturarla para loguear los errores
		 *		   pero luego deberá propagarla para que el controlador se encargue de manejarla.
		 *
		 * try (CallableStatement cstmt = conexion.prepareCall("CALL PROCEDURE reservaSoloIda(?, ?, ?, ?, ?, ?, ?)"))
		 * {
		 *  ...
		 * }
		 * catch (SQLException ex){
		 * 			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		 *  		throw ex;
		 * } 
		 */
		
		int resultado;
		
		try (CallableStatement cstmt = conexion.prepareCall("CALL reservaSoloIda(?, ?, ?, ?, ?, ?, ?)")){
			cstmt.setString(1, vuelo.getNroVuelo());
			cstmt.setDate(2, vuelos.utils.Fechas.convertirDateADateSQL(vuelo.getFechaVuelo()));
			cstmt.setString(3, detalleVuelo.getClase());
			cstmt.setString(4, pasajero.getTipoDocumento());
			cstmt.setInt(5, pasajero.getNroDocumento());
			cstmt.setInt(6, empleado.getLegajo());
			cstmt.registerOutParameter(7, java.sql.Types.NUMERIC);
			
			cstmt.execute();
			resultado = cstmt.getInt(7);
			switch (resultado) {
				case -1: throw new Exception("SQLEXCEPTION!, transacción abortada'");
				case -2: throw new Exception("El empleado no es válido.");
				case -3: throw new Exception("El pasajero no está registrado.");
				case -4: throw new Exception("El vuelo no es válido.");
				case -5: throw new Exception("No se puede reservas en menos de 15 dias.");
				case -6: throw new Exception("No hay asientos disponibles.");
				default: logger.debug("Reserva compleatada con exito.");
			}

		}catch (SQLException ex){
			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		   	throw ex;
			}  
		
		return resultado;
	}
	
	@Override
	public int reservarIdaVuelta(PasajeroBean pasajero, 
				 				 InstanciaVueloBean vueloIda,
				 				 DetalleVueloBean detalleVueloIda,
				 				 InstanciaVueloBean vueloVuelta,
				 				 DetalleVueloBean detalleVueloVuelta,
				 				 EmpleadoBean empleado) throws Exception {
		
		logger.info("Realiza la reserva de ida y vuelta con pasajero {}", pasajero.getNroDocumento());
		/**
		 * TODO (parte 2) Realizar una reserva de ida y vuelta llamando al Stored Procedure (S.P.) correspondiente. 
		 *      Si la reserva tuvo exito deberá retornar el número de reserva. Si la reserva no tuvo éxito o 
		 *      falla el S.P. deberá propagar un mensaje de error explicativo dentro de una excepción.
		 *      La demás excepciones generadas automáticamente por algun otro error simplemente se propagan.
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 * 
		 * @throws Exception. Deberá propagar la excepción si ocurre alguna. Puede capturarla para loguear los errores
		 *		   pero luego deberá propagarla para que se encargue el controlador.
		 *
		 * try (CallableStatement ... )
		 * {
		 *  ...
		 * }
		 * catch (SQLException ex){
		 * 			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		 *  		throw ex;
		 * } 
		 */
		
		
		
		int resultado;
		
		try (CallableStatement cstmt = conexion.prepareCall("CALL reservaIdaVuelta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){
			cstmt.setString(1, vueloIda.getNroVuelo());
			cstmt.setDate(2, vuelos.utils.Fechas.convertirDateADateSQL(vueloIda.getFechaVuelo()));
			cstmt.setString(3, detalleVueloIda.getClase());
			cstmt.setString(4, vueloVuelta.getNroVuelo());
			cstmt.setDate(5, vuelos.utils.Fechas.convertirDateADateSQL(vueloVuelta.getFechaVuelo()));
			cstmt.setString(6, detalleVueloVuelta.getClase());
			cstmt.setString(7, pasajero.getTipoDocumento());
			cstmt.setInt(8, pasajero.getNroDocumento());
			cstmt.setInt(9, empleado.getLegajo());
			cstmt.registerOutParameter(10, java.sql.Types.NUMERIC);
			
			cstmt.execute();
			
			resultado = cstmt.getInt(10);
			switch (resultado) {
				case -1: throw new Exception("SQLEXCEPTION!, transacción abortada'");
				case -2: throw new Exception("El empleado no es válido.");
				case -3: throw new Exception("El pasajero no está registrado.");
				case -4: throw new Exception("El vuelo no es válido.");
				case -5: throw new Exception("No se puede reservas en menos de 15 dias.");
				case -6: throw new Exception("No hay asientos disponibles.");
				default: logger.debug("Reserva compleatada con exito");
			}
		}catch (SQLException ex){
			logger.debug("Error al consultar la BD. SQLException: {}. SQLState: {}. VendorError: {}.", ex.getMessage(), ex.getSQLState(), ex.getErrorCode());
		   	throw ex;
			}  
		
		return resultado;
	}
	
	@Override
	public ReservaBean recuperarReserva(int codigoReserva) throws Exception {
		
		logger.info("Solicita recuperar información de la reserva con codigo {}", codigoReserva);
		
		/**
		 * TODO (parte 2) Debe realizar una consulta que retorne un objeto ReservaBean donde tenga los datos de la
		 *      reserva que corresponda con el codigoReserva y en caso que no exista generar una excepción.
		 *
		 * 		Debe poblar la reserva con todas las instancias de vuelo asociadas a dicha reserva y 
		 * 		las clases correspondientes.
		 * 
		 * 		Los objetos ReservaBean además de las propiedades propias de una reserva tienen un arraylist
		 * 		con pares de instanciaVuelo y Clase. Si la reserva es solo de ida va a tener un unico
		 * 		elemento el arreglo, y si es de ida y vuelta tendrá dos elementos. 
		 * 
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOReservaImpl(...)).
		 */
		ReservaBean reserva = null;
		String sql = "SELECT * FROM reservas NATURAL JOIN reserva_vuelo_clase WHERE numero= "+codigoReserva+";";
		DAOPasajero pas = new DAOPasajeroImpl(conexion);
		DAOEmpleado emp = new DAOEmpleadoImpl(conexion);
		DAOVuelos vue = new DAOVuelosImpl(conexion);
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				reserva = new ReservaBeanImpl();
				reserva.setNumero(rs.getInt("numero"));
				reserva.setFecha(rs.getDate("fecha_vuelo"));
				reserva.setVencimiento(rs.getDate("vencimiento"));
				reserva.setEstado(rs.getString("estado"));
				reserva.setPasajero(pas.recuperarPasajero(rs.getString("doc_tipo"), rs.getInt("doc_nro")));
				reserva.setEmpleado(emp.recuperarEmpleado(rs.getInt("legajo")));
				
				ArrayList<InstanciaVueloClaseBean> vuelosYClases = new ArrayList<InstanciaVueloClaseBean>();
				
				ArrayList<InstanciaVueloBean> vuelos = vue.recuperarVuelo(codigoReserva);
				Iterator<InstanciaVueloBean> itVuelos = vuelos.iterator();
				
				InstanciaVueloClaseBean vuelosClase = new InstanciaVueloClaseBeanImpl();
				
				while(itVuelos.hasNext()) {
					InstanciaVueloBean auxVuelo = itVuelos.next();
					vuelosClase.setVuelo(auxVuelo);
					vuelosClase.setClase(vue.recuperarDetalleVueloClase(auxVuelo, rs.getString("clase")));
					vuelosYClases.add(vuelosClase);
				}
				
				reserva.setVuelosClase(vuelosYClases);
				
				if(reserva.getVuelosClase().size()==1)
					reserva.setEsIdaVuelta(false);
				else reserva.setEsIdaVuelta(true);
			}
		}catch(SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());		   
			throw new Exception("Error en la conexión con la BD.");}
		
		logger.debug("Se recuperó la reserva: {}, {}", reserva.getNumero(), reserva.getEstado());
		return reserva;		
		
	}
	

}
