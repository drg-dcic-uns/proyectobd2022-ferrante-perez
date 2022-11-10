package vuelos.modelo.empleado.dao;

import java.util.ArrayList;
import java.util.Date;

import vuelos.modelo.empleado.beans.DetalleVueloBean;
import vuelos.modelo.empleado.beans.InstanciaVueloBean;
import vuelos.modelo.empleado.beans.UbicacionesBean;

public interface DAOVuelos {

	/**
	 * Recupera las instancias de vuelo que correponden a la fecha, origen y destino dados.
	 * 
	 * @param fechaVuelo
	 * @param origen
	 * @param destino
	 * @return
	 */
	ArrayList<InstanciaVueloBean> recuperarVuelosDisponibles(Date fechaVuelo, UbicacionesBean origen, UbicacionesBean destino) throws Exception;

	/**
	 * Recupera las clases, asientos disponibles de cada clase y precio del vuelo pasado por parametro.
	 * 
	 * @param vuelo
	 * @return
	 */
	ArrayList<DetalleVueloBean> recuperarDetalleVuelo(InstanciaVueloBean vuelo) throws Exception;

	/**
	 * Recupera las instancias de vuelos que tiene reserva de nroReserva
	 * 
	 * @param nroReserva
	 * @return
	 */
	ArrayList<InstanciaVueloBean> recuperarVuelo(int nroReserva) throws Exception;
	
	/**
	 * Recupera los detalles de un vuelo con una clase expecifica
	 * @param vuelo
	 * @param clase
	 * @return
	 */
	DetalleVueloBean recuperarDetalleVueloClase(InstanciaVueloBean vuelo, String clase) throws Exception;
}
