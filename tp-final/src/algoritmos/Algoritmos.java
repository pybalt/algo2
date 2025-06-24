package algoritmos;

import implementacion.ColaString;
import implementacion.ColaPrioridad;
import implementacion.DiccionarioSimple;
import implementacion.Utils;
import tdas.ABBPrecipitacionesTDA;
import tdas.ColaPrioridadTDA;
import tdas.ColaStringTDA;
import tdas.ConjuntoTDA;
import tdas.DiccionarioSimpleTDA;
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

		DiccionarioSimple acumuladorDias = new DiccionarioSimple();
		acumuladorDias.inicializar();
		DiccionarioSimple contadorCampos = new DiccionarioSimple();
		contadorCampos.inicializar();

		recopilarPrecipitacionesTodosLosCampos(arbol, periodo, acumuladorDias, contadorCampos);
		
		ColaPrioridad resultado = new ColaPrioridad();
		resultado.inicializarCola();
		
		ConjuntoTDA dias = acumuladorDias.obtenerClaves();
		while(!dias.estaVacio()){
			int dia = dias.elegir();
			int totalPrecipitacion = acumuladorDias.recuperar(dia);
			int cantidadCampos = contadorCampos.recuperar(dia);
			int promedio = cantidadCampos > 0 ? totalPrecipitacion / cantidadCampos : 0;
			resultado.acolarPrioridad(promedio, dia);
			dias.sacar(dia);
		}
		
		return resultado;
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
		String periodo = Utils.Periodo.periodo(anio, mes);
		
		int[] contadores = {0, 0}; // [totalPrecipitacion, cantidadCampos]
		recopilarPrecipitacionesDia(arbol, periodo, dia, contadores);
		
		if(contadores[1] == 0) {
			return 0.0f;
		}
		
		return (float) contadores[0] / contadores[1];
	}

	/**
	 * Devuelve el campo que recibio mas lluvia 
	 * */
	public String campoMasLLuvisoHistoria() {
		String[] resultado = {null, String.valueOf(Integer.MIN_VALUE)}; // [campo, maxLluvia]
		encontrarCampoMasLluvioso(arbol, resultado);
		return resultado[0];
	}

	/**
	 * Devuelve los campos con una cantidad de lluvia en un periodo determinado que es mayor al
	 * promedio de lluvia en un periodo determinado
	 * */
	public ColaString camposConLLuviaMayorPromedio(int anio, int mes) {
		String periodo = Utils.Periodo.periodo(anio, mes);
		
		int totalLluvia = 0;
		int cantidadCampos = 0;
		int[] contadores = {totalLluvia, cantidadCampos};
		calcularPromedioPerido(arbol, periodo, contadores);
		
		if(contadores[1] == 0) {
			ColaString resultado = new ColaString();
			resultado.inicializarCola();
			return resultado;
		}
		
		float promedio = (float) contadores[0] / contadores[1];
		
		// Encontrar campos que superan el promedio
		ColaString resultado = new ColaString();
		resultado.inicializarCola();
		encontrarCamposMayorPromedio(arbol, periodo, promedio, resultado);
		
		return resultado;
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
	
	private void recopilarPrecipitacionesDia(ABBPrecipitacionesTDA arbol, String periodo, int dia, int[] contadores) {
		if(arbol.arbolVacio()) return;
		
		// El método precipitaciones() del árbol devuelve PROMEDIOS de todos los campos
		// Para un día específico de un campo específico, necesitamos usar el campo individual
		ABBPrecipitacionesTDA campoActual = arbol;
		ColaPrioridadTDA precipitaciones = campoActual.precipitaciones(periodo);
		
		// Buscar el día específico en las precipitaciones de este campo
		while(!precipitaciones.colaVacia()) {
			if(precipitaciones.prioridad() == dia) {
				contadores[0] += precipitaciones.primero(); // Acumular precipitación
				contadores[1]++; // Contar campo
				break;
			}
			precipitaciones.desacolar();
		}
		
		// Recursión en subárboles
		if(!arbol.hijoIzq().arbolVacio()) {
			recopilarPrecipitacionesDia(arbol.hijoIzq(), periodo, dia, contadores);
		}
		if(!arbol.hijoDer().arbolVacio()) {
			recopilarPrecipitacionesDia(arbol.hijoDer(), periodo, dia, contadores);
		}
	}
	
	private void encontrarCampoMasLluvioso(ABBPrecipitacionesTDA arbol, String[] resultado) {
		if(arbol.arbolVacio()) return;
		
		// Calcular lluvia total del campo actual
		int lluviaTotal = 0;
		ColaStringTDA periodos = arbol.periodos();
		while(!periodos.colaVacia()) {
			String periodo = periodos.primero();
			lluviaTotal += calcularLluviaPeriodo(arbol, periodo);
			periodos.desacolar();
		}
		
		// Comparar con el máximo actual
		int maxActual = Integer.parseInt(resultado[1]);
		if(lluviaTotal > maxActual) {
			resultado[0] = arbol.raiz();
			resultado[1] = String.valueOf(lluviaTotal);
		}
		
		// Recursión en subárboles
		if(!arbol.hijoIzq().arbolVacio()) {
			encontrarCampoMasLluvioso(arbol.hijoIzq(), resultado);
		}
		if(!arbol.hijoDer().arbolVacio()) {
			encontrarCampoMasLluvioso(arbol.hijoDer(), resultado);
		}
	}
	
	private void calcularPromedioPerido(ABBPrecipitacionesTDA arbol, String periodo, int[] contadores) {
		if(arbol.arbolVacio()) return;
		
		// Verificar si este campo tiene datos para el período
		ColaStringTDA periodos = arbol.periodos();
		boolean tienePeriodo = false;
		while(!periodos.colaVacia()) {
			if(periodos.primero().equals(periodo)) {
				tienePeriodo = true;
				break;
			}
			periodos.desacolar();
		}
		
		if(tienePeriodo) {
			int lluvia = calcularLluviaPeriodo(arbol, periodo);
			contadores[0] += lluvia; // Acumular lluvia total
			contadores[1]++; // Contar campos
		}
		
		// Recursión en subárboles
		if(!arbol.hijoIzq().arbolVacio()) {
			calcularPromedioPerido(arbol.hijoIzq(), periodo, contadores);
		}
		if(!arbol.hijoDer().arbolVacio()) {
			calcularPromedioPerido(arbol.hijoDer(), periodo, contadores);
		}
	}
	
	private void encontrarCamposMayorPromedio(ABBPrecipitacionesTDA arbol, String periodo, float promedio, ColaString resultado) {
		if(arbol.arbolVacio()) return;
		
		// Verificar si este campo tiene datos para el período
		ColaStringTDA periodos = arbol.periodos();
		boolean tienePeriodo = false;
		while(!periodos.colaVacia()) {
			if(periodos.primero().equals(periodo)) {
				tienePeriodo = true;
				break;
			}
			periodos.desacolar();
		}
		
		if(tienePeriodo) {
			int lluvia = calcularLluviaPeriodo(arbol, periodo);
			if(lluvia > promedio) {
				resultado.acolar(arbol.raiz());
			}
		}
		
		// Recursión en subárboles
		if(!arbol.hijoIzq().arbolVacio()) {
			encontrarCamposMayorPromedio(arbol.hijoIzq(), periodo, promedio, resultado);
		}
		if(!arbol.hijoDer().arbolVacio()) {
			encontrarCamposMayorPromedio(arbol.hijoDer(), periodo, promedio, resultado);
		}
	}
	
	private void recopilarPrecipitacionesTodosLosCampos(ABBPrecipitacionesTDA arbol, String periodo, 
														DiccionarioSimpleTDA acumulador, 
														DiccionarioSimpleTDA contador) {
		if(arbol.arbolVacio()) return;
		
		// Obtener precipitaciones de este campo para el período
		ColaPrioridadTDA precipitaciones = arbol.precipitaciones(periodo);
		
		while(!precipitaciones.colaVacia()) {
			int dia = precipitaciones.prioridad();
			int precipitacion = precipitaciones.primero();
			
			// Acumular precipitación
			if(acumulador.obtenerClaves().pertenece(dia)) {
				int actual = acumulador.recuperar(dia);
				acumulador.agregar(dia, actual + precipitacion);
			} else {
				acumulador.agregar(dia, precipitacion);
			}
			
			// Contar campos
			if(contador.obtenerClaves().pertenece(dia)) {
				int actualCount = contador.recuperar(dia);
				contador.agregar(dia, actualCount + 1);
			} else {
				contador.agregar(dia, 1);
			}
			
			precipitaciones.desacolar();
		}
		
		// Recursión en subárboles
		if(!arbol.hijoIzq().arbolVacio()) {
			recopilarPrecipitacionesTodosLosCampos(arbol.hijoIzq(), periodo, acumulador, contador);
		}
		if(!arbol.hijoDer().arbolVacio()) {
			recopilarPrecipitacionesTodosLosCampos(arbol.hijoDer(), periodo, acumulador, contador);
		}
	}
}
