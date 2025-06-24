package algoritmos;

import implementacion.ColaString;
import implementacion.Utils;
import tdas.ABBPrecipitacionesTDA;
import tdas.ColaPrioridadTDA;
import tdas.ColaStringTDA;
public class Algoritmos {
	private ABBPrecipitacionesTDA arbol;
	
	public Algoritmos(ABBPrecipitacionesTDA arbol) {
		this.arbol = arbol;
	}
	/**
	 * Agrega una medicion a un campo determinado, en una fecha determinada
	 * */
	public void agregarMedicion(String campo, int anio, int mes, int dia, int precipitacion) {
		String anioToString = String.valueOf(anio);
		String mesToString = String.valueOf(mes);
		this.arbol.agregarMedicion(campo, anioToString, mesToString, dia, precipitacion);
	}
	
	/**
	 * Elimina una medicions a un campo determinado, en una fecha determinada
	 * */
	public void eliminarMedicion(String campo, int anio, int mes, int dia, int precipitacion) {
		this.arbol.eliminarMedicion(campo, campo, campo, dia);
	}
	
	/**
	 * Elimina un campo determinado recibido como parametro
	 * */
	public void eliminarCampo(String campo) {
		this.arbol.eliminar(campo);
	}
	
	/**
	 * Devuelve una cola con prioridad con las precipitaciones promedio de cada dia de un mes y año 
	 * determinado en todos los campos
	 * */
	public ColaPrioridadTDA medicionesMes(int anio, int mes){
		String periodo = Utils.Periodo.periodo(anio, mes);
		return this.arbol.precipitaciones(periodo);
	}

	/**
	 * Devuelve una cola con prioridad con las precipitaciones de cada dia de un mes y año 
	 * determinado en un campo determinado
	 * */
	public ColaPrioridadTDA medicionesCampoMes(String campo, int anio, int mes){
		String periodo = Utils.Periodo.periodo(anio, mes);
		ABBPrecipitacionesTDA nodoCampo = buscarCampo(arbol, campo);
		return nodoCampo.precipitaciones(periodo);
	}

	/**
	 * Devuelve el numero de mes donde mas llovio entre todos los meses de todos los años de cualquier campo
	 * */
	public int mesMasLluvioso() {
		int[] resultado = {-1, Integer.MIN_VALUE}; // [mes, maxLluvia]
		int indiceMes = 0;
		int indiceLluvia = 1;
		encontrarMesMasLluvioso(arbol, resultado, indiceMes, indiceLluvia);
		return resultado[0];
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

	/* Metodos privados */
	
	private ABBPrecipitacionesTDA buscarCampo(ABBPrecipitacionesTDA nodo, String nombreCampo){
		if(nodo.arbolVacio()){
			return null;
		}
		String raizActual = nodo.raiz();

		if(raizActual.equalsIgnoreCase(nombreCampo)){
			return nodo;
		}
		if(nombreCampo.compareToIgnoreCase(raizActual)<0){
			return buscarCampo(nodo.hijoIzq(), nombreCampo);
		}
		if(nombreCampo.compareToIgnoreCase(raizActual) > 0){
			return buscarCampo(nodo.hijoDer(), nombreCampo);
		}
		return null;
	}
	/**
	 * Mi interpretacion de lo que se pide es:
	 * 	Devolver el mes mas lluvioso entre todos los periodos
	 * 	De los datos:
	 * 	- 202501: 55mm
	 * 	- 202402: 150mm
	 * 	- 202302: 150mm
	 * 	- 200101: 250mm
	 * El algoritmo devolveria que el mes es 01, porque
	 * 200101 es el mayor valor registrado (250mm).
	 * 
	 * Otra alternativa es sumar todos los 01 de todos los años,
	 * y en este caso, el que no elegí, daría 02 (150mm+150mm = 300mm).
	 * @param arbol
	 * @param listaMeses
	 */
	private void encontrarMesMasLluvioso(ABBPrecipitacionesTDA arbol, int[] resultado, int indiceMes, int indiceLluvia) {
		if(arbol.arbolVacio()) return;

		ColaStringTDA periodos = arbol.periodos();
		while(!periodos.colaVacia()) {
			String periodo = periodos.primero();
			int mes = Utils.Periodo.obtenerMes(periodo);
			int lluvia = calcularLluviaPeriodo(arbol, periodo);
			
			if(lluvia > resultado[1]) {
				resultado[indiceMes] = mes;
				resultado[indiceLluvia] = lluvia;
			}
			periodos.desacolar();
		}
		if(!arbol.hijoDer().arbolVacio()){
			encontrarMesMasLluvioso(arbol.hijoDer(), resultado, indiceMes, indiceLluvia);
		}
		if(!arbol.hijoIzq().arbolVacio()){
			encontrarMesMasLluvioso(arbol.hijoIzq(), resultado, indiceMes, indiceLluvia);
		}
	}
	private int calcularLluviaPeriodo(ABBPrecipitacionesTDA arbol, String periodo){
		int lluviaPeriodo = 0;
		ColaPrioridadTDA precipitaciones = arbol.precipitaciones(periodo);
		while(!precipitaciones.colaVacia()){
			lluviaPeriodo += precipitaciones.primero();
			precipitaciones.desacolar();
		}
		return lluviaPeriodo;
	}
}
