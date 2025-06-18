package algoritmos;

import implementacion.ColaString;
import tdas.ColaPrioridadTDA;
import tdas.ABBPrecipitacionesTDA;

public class Algoritmos {
	private ABBPrecipitacionesTDA arbol;
	
	public Algoritmos(ABBPrecipitacionesTDA arbol) {
		this.arbol = arbol;
	}
	/**
	 * Agrega una medicion a un campo determinado, en una fecha determinada
	 * */
	public void agregarMedicion(String campo, int anio, int mes, int dia, int precipitacion) {
		
	}
	
	/**
	 * Elimina una medicions a un campo determinado, en una fecha determinada
	 * */
	public void eliminarMedicion(String campo, int anio, int mes, int dia) {
		
	}
	
	/**
	 * Elimina un campo determinado recibido como parametro
	 * */
	public void eliminarCampo(String campo) {
		
	}
	
	/**
	 * Devuelve una cola con prioridad con las precipitaciones promedio de cada dia de un mes y año 
	 * determinado en todos los campos
	 * */
	public ColaPrioridadTDA medicionesMes(int anio, int mes){
		return null;
	}
	
	/**
	 * Devuelve una cola con prioridad con las precipitaciones de cada dia de un mes y año 
	 * determinado en un campo determinado
	 * */
	public ColaPrioridadTDA medicionesCampoMes(String campo, int anio, int mes){
		return null;
	}

	/**
	 * Devuelve el numero de mes donde mas llovio entre todos los meses de todos los años de cualquier campo
	 * */
	public int mesMasLluvioso() {
		return 0;
	}
	
	/**
	 * Devuelve el promedio de precipitaciones caidas en un dia, mes y anio determinado en todos los campos
	 * */
	public float promedioLluviaEnUnDia(int anio, int mes, int dia) {
		return 0;
	}

	/**
	 * Devuelve el campo que recibio mas lluvia 
	 * */
	public String campoMasLLuvisoHistoria() {
		return null;
	}

	/**
	 * Devuelve los campos con una cantidad de lluvia en un periodo determinado que es mayor al
	 * promedio de lluvia en un periodo determinado
	 * */
	public ColaString camposConLLuviaMayorPromedio(int anio, int mes) {
		return null;
	}
	
}
