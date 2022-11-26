package vuelos.modelo.empleado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vuelos.modelo.empleado.beans.PasajeroBean;
import vuelos.modelo.empleado.beans.PasajeroBeanImpl;
import vuelos.modelo.empleado.dao.datosprueba.DAOPasajeroDatosPrueba;

public class DAOPasajeroImpl implements DAOPasajero {

	private static Logger logger = LoggerFactory.getLogger(DAOPasajeroImpl.class);
	
	private static final long serialVersionUID = 1L;

	//conexión para acceder a la Base de Datos
	private Connection conexion;
	
	public DAOPasajeroImpl(Connection conexion) {
		this.conexion = conexion;
	}


	@Override
	public PasajeroBean recuperarPasajero(String tipoDoc, int nroDoc) throws Exception {	

		PasajeroBean pasajero = null;
		String sql = "SELECT * FROM pasajeros WHERE doc_tipo = '"+tipoDoc+"' and doc_nro = "+nroDoc+";";
		
		try {
			PreparedStatement stmt = conexion.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				pasajero = new PasajeroBeanImpl();
				pasajero.setTipoDocumento(rs.getString("doc_tipo"));
				pasajero.setNroDocumento(rs.getInt("doc_nro"));
				pasajero.setApellido(rs.getString("apellido"));
				pasajero.setNombre(rs.getString("nombre"));
				pasajero.setDireccion(rs.getString("direccion"));
				pasajero.setTelefono(rs.getString("telefono"));
				pasajero.setNacionalidad(rs.getString("nacionalidad"));
				
				logger.info("El DAO retorna al pasajero {} {}", pasajero.getApellido(), pasajero.getNombre());
			}
		}catch(SQLException ex) {
			logger.error("SQLException: " + ex.getMessage());
			logger.error("SQLState: " + ex.getSQLState());
			logger.error("VendorError: " + ex.getErrorCode());		   
			throw new Exception("Error en la conexión con la BD.");
			}
		
		return pasajero;
	}
	
	

}
