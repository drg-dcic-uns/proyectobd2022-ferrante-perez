package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.AeropuertoBean;
import vuelos.modelo.empleado.beans.AeropuertoBeanImpl;
import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.DetalleVueloBeanImpl;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBeanImpl;
import vuelos.modelo.empleado.beans.UbicacionesBean;
import vuelos.modelo.empleado.beans.UbicacionesBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOVuelosDatosPrueba;

public class DAOVuelosImpl implements DAOVuelos {

	private static Logger logger = LoggerFactory.getLogger(DAOVuelosImpl.class);
	
	//conexión para acceder a la Base de Datos
	private Connection conexion;
	
	public DAOVuelosImpl(Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public ArrayList<InstanciaVueloBean> recuperarVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen, UbicacionesBean destino)  throws Exception {
		/** 
		 * TODO Debe retornar una lista de vuelos disponibles para ese día con origen y destino según los parámetros. 
		 *      Debe propagar una excepción si hay algún error en la consulta.    
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOVuelosImpl(...)).  
		 */
		/**Datos estáticos de prueba. Quitar y reemplazar por código que recupera los datos reales.
		ArrayList<InstanciaVueloBean> resultado = DAOVuelosDatosPrueba.generarVuelos(fechaVuelo);  
		
		return resultado;
		Fin datos estáticos de prueba.
		*/
		logger.info("Lista de vuelos de la fecha {}", vuelos.utils.Fechas.convertirDateAString(fechaVuelo));
		ArrayList<InstanciaVueloBean> resultado = new ArrayList<InstanciaVueloBean>();
		
		String sql = "SELECT DISTINCT nro_vuelo, modelo, fecha, dia_sale, hora_sale, hora_llega, tiempo_estimado, codigo_aero_sale, nombre_aero_sale, ciudad_sale, codigo_aero_llega, nombre_aero_llega, ciudad_llega"+
					" FROM vuelos_disponibles WHERE fecha='"+vuelos.utils.Fechas.convertirDateADateSQL(fechaVuelo)+
					"' and ciudad_sale='"+origen.getCiudad()+"' and ciudad_llega='"+destino.getCiudad()+"';";
		try {
			PreparedStatement stmt = conexion.prepareStatement(sql);
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next()) {
				logger.info("Lista de vuelos de la fecha {}", vuelos.utils.Fechas.convertirDateAString(fechaVuelo));
				InstanciaVueloBean vuelo = new InstanciaVueloBeanImpl();
				vuelo.setNroVuelo(rs.getString("nro_vuelo"));
				vuelo.setModelo(rs.getString("modelo"));
				vuelo.setDiaSalida(rs.getString("dia_sale"));
				vuelo.setHoraSalida(rs.getTime("hora_sale"));
				vuelo.setHoraLlegada(rs.getTime("hora_llega"));
				vuelo.setTiempoEstimado(rs.getTime("tiempo_estimado"));
				vuelo.setFechaVuelo(vuelos.utils.Fechas.convertirStringADateSQL(rs.getString("fecha")));
				
				AeropuertoBean aeroSale = new AeropuertoBeanImpl();
				aeroSale.setCodigo(rs.getString("codigo_aero_sale"));
				aeroSale.setNombre(rs.getString("nombre_aero_sale"));
				aeroSale.setUbicacion(origen);
				vuelo.setAeropuertoSalida(aeroSale);
				
				AeropuertoBean aeroLlega = new AeropuertoBeanImpl();
				aeroLlega.setCodigo(rs.getString("codigo_aero_llega"));
				aeroLlega.setNombre(rs.getString("nombre_aero_llega"));
				aeroLlega.setUbicacion(destino);
				vuelo.setAeropuertoLlegada(aeroLlega);
				
				resultado.add(vuelo);
			}
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());		   
			throw new Exception("Error en la conexión con la BD.");
	   }
		return resultado;
		
	}

	@Override
	public ArrayList<DetalleVueloBean> recuperarDetalleVuelo(InstanciaVueloBean vuelo) throws Exception {
		/** 
		 * TODO Debe retornar una lista de clases, precios y asientos disponibles de dicho vuelo.		   
		 *      Debe propagar una excepción si hay algún error en la consulta.    
		 *      
		 *      Nota: para acceder a la B.D. utilice la propiedad "conexion" que ya tiene una conexión
		 *      establecida con el servidor de B.D. (inicializada en el constructor DAOVuelosImpl(...)).
		 */
		
		ArrayList<DetalleVueloBean> resultado = new ArrayList<DetalleVueloBean>();
		
		String sql = "SELECT clase, precio, asientos_disponibles FROM vuelos_disponibles WHERE fecha='"+vuelos.utils.Fechas.convertirDateADateSQL(vuelo.getFechaVuelo())+"' and nro_vuelo='"+vuelo.getNroVuelo()+"';";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(sql);
			ResultSet rs= stmt.executeQuery(sql);
			while(rs.next()) {
				DetalleVueloBean dvuelo = new DetalleVueloBeanImpl();
				dvuelo.setAsientosDisponibles(rs.getInt("asientos_disponibles"));
				dvuelo.setClase(rs.getString("clase"));
				dvuelo.setPrecio(rs.getInt("precio"));
				dvuelo.setVuelo(vuelo);
				
				resultado.add(dvuelo);
			}
		}catch (SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());		   
			throw new Exception("Error en la conexión con la BD.");
	   }
		return resultado; 
	}
}
