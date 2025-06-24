package implementacion;

import tdas.ABBPrecipitacionesTDA;
import tdas.ColaPrioridadTDA;
import tdas.ColaStringTDA;
import tdas.ConjuntoStringTDA;
import tdas.ConjuntoTDA;
import tdas.DiccionarioSimpleStringTDA;
import tdas.DiccionarioSimpleTDA;

public class ArbolPrecipitaciones implements ABBPrecipitacionesTDA {

	class nodoArbol {
		String campo;
		DiccionarioSimpleStringTDA mensualPrecipitaciones;
		ABBPrecipitacionesTDA hijoIzquierdo;
		ABBPrecipitacionesTDA hijoDerecho;
	}
	
	private nodoArbol raiz;

	@Override
	public void inicializar() {
		raiz = null;
	}

	@Override
	public void agregar(String valor) {
		if(raiz == null){
			raiz = new nodoArbol();
			raiz.campo = valor;
			raiz.hijoDerecho = new ArbolPrecipitaciones();
			raiz.hijoDerecho.inicializar();
			raiz.hijoIzquierdo = new ArbolPrecipitaciones();
			raiz.hijoIzquierdo.inicializar();
			raiz.mensualPrecipitaciones = new DiccionarioSimpleString();
			raiz.mensualPrecipitaciones.inicializarDiccionario();
		}else if(esMenor(valor, raiz())){
			raiz.hijoIzquierdo.agregar(valor);
		}
		else if(esMayor(valor, raiz())){
			raiz.hijoDerecho.agregar(valor);
		}
	}

	@Override
	public void agregarMedicion(String valor, String anio, String mes, int dia, int precipitacion) {
		if(!Utils.Fecha.fechaValida(Integer.valueOf(anio), Integer.valueOf(mes), dia)){
			return;
		}
		String periodo = Utils.Periodo.periodo(anio, mes);

		nodoArbol nodo = buscar(raiz, valor);
		if(nodo != null){
			nodo.mensualPrecipitaciones.agregar(periodo, dia, precipitacion);
		}else{
			agregar(valor);
			nodo = buscar(raiz, valor);
			nodo.mensualPrecipitaciones.agregar(periodo, dia, precipitacion);
		}
	}
	private nodoArbol buscar(nodoArbol nodo, String campo){
		if(nodo == null){
			return null;
		}
		if(nodo.campo.equals(campo)){
			return nodo;
		}else if(esMenor(campo, nodo.campo)){
			return buscar(((ArbolPrecipitaciones)nodo.hijoIzquierdo).raiz, campo);
		}else if(esMayor(campo, nodo.campo)){
			return buscar(((ArbolPrecipitaciones)nodo.hijoDerecho).raiz, campo);
		}
		return null;
	}
	@Override
	public void eliminar(String valor) {
		// Caso 1. El nodo a eliminar es la raiz y es una hoja
		if(raiz != null
			&& raiz.hijoIzquierdo.arbolVacio()
			&& raiz.hijoDerecho.arbolVacio()
			&& raiz.campo == valor
		){
			raiz = null;
		}
		// Caso 2. El nodo a eliminar es la raiz y tiene solo hijo izquierdo
		else if(raiz != null
		&& !raiz.hijoIzquierdo.arbolVacio()
		&& raiz.hijoDerecho.arbolVacio()
		&& raiz.campo == valor){
			ABBPrecipitacionesTDA mayorHijo = mayor(raiz.hijoIzquierdo);
			raiz.campo = mayorHijo.raiz();
			raiz.hijoIzquierdo = mayorHijo.hijoIzq();
			raiz.hijoIzquierdo.eliminar(valor);
		}
		// Caso 3: El nodo a eliminar es la raiz y tiene solo hijo derecho
		else if(raiz != null
		&& raiz.hijoIzquierdo.arbolVacio()
		&& !raiz.hijoDerecho.arbolVacio()
		&& raiz.campo == valor){
			ABBPrecipitacionesTDA mayorHijo = menor(raiz.hijoDerecho);
			raiz.campo = mayorHijo.raiz();
			raiz.hijoDerecho = mayorHijo.hijoDer();
			raiz.hijoDerecho.eliminar(valor);
		}
		// Caso 4: El nodo a eliminar es la raíz y tiene ambos hijos
		// Leer ABB.java con los metodos de reemplazo
		// Usamos in-order sucessor
		else if(
			raiz != null
			&& raiz.campo == valor
			&& !raiz.hijoDerecho.arbolVacio()
			&& !raiz.hijoIzquierdo.arbolVacio()
		){
			ABBPrecipitacionesTDA mayorIzq = mayor(raiz.hijoIzquierdo);
			nodoArbol nodoReemplazante = obtenerNodo(mayorIzq);
			raiz.campo = nodoReemplazante.campo;
			raiz.mensualPrecipitaciones = nodoReemplazante.mensualPrecipitaciones;
			raiz.hijoIzquierdo.eliminar(nodoReemplazante.campo);
		}
		// Casos 5 y 6: Buscar en subárboles
		else if(raiz != null && raiz.campo != null && esMayor(raiz.campo, valor)){
			raiz.hijoIzquierdo.eliminar(valor);
		}
		else if(raiz != null && raiz.campo != null && esMenor(raiz.campo, valor)){
			raiz.hijoDerecho.eliminar(valor);
		}
	}
	private nodoArbol obtenerNodo(ABBPrecipitacionesTDA arbol){
		return ((ArbolPrecipitaciones)arbol).raiz;
	}
    private ABBPrecipitacionesTDA menor(ABBPrecipitacionesTDA arbol){
        if(arbol.hijoIzq().arbolVacio()){
            return arbol;
        }else{
            return menor(arbol.hijoIzq());
        }
    }
    private ABBPrecipitacionesTDA mayor(ABBPrecipitacionesTDA arbol){
        if(arbol.hijoDer().arbolVacio()){
            return arbol;
        }else{
            return mayor(arbol.hijoDer());
        }
    }
	@Override
	public String raiz() {
		return raiz.campo;
	}
	
	@Override
	public void eliminarMedicion(String valor, String anio, String mes, int dia) {
		String periodo = Utils.Periodo.periodo(anio, mes);
		nodoArbol nodo = buscar(raiz, valor);
		if(nodo == null){
			return;
		}
		if (nodo.mensualPrecipitaciones.claves().pertenece(periodo)){
			nodo.mensualPrecipitaciones.recuperar(periodo).eliminar(dia);
		}
	}

	@Override
	public ColaStringTDA periodos() {
		ColaStringTDA resultado = new ColaString();
		resultado.inicializarCola();

		ConjuntoStringTDA periodosUnicos = new ConjuntoString();
		periodosUnicos.inicializar();

		recolectarPeriodos(raiz, periodosUnicos);
		while(!periodosUnicos.estaVacio()){
			String periodo = periodosUnicos.elegir();
			resultado.acolar(periodo);
			periodosUnicos.sacar(periodo);
		}
		return resultado;
	}
	private void recolectarPeriodos(nodoArbol nodo, ConjuntoStringTDA periodos){
		if(nodo!=null){
			ConjuntoStringTDA claves = nodo.mensualPrecipitaciones.claves();
			ConjuntoStringTDA clavesCopia = new ConjuntoString();
			clavesCopia.inicializar();
			while(!claves.estaVacio()){
				String periodo = claves.elegir();
				clavesCopia.agregar(periodo);
				periodos.agregar(periodo);
				claves.sacar(periodo);
			}
			while(!clavesCopia.estaVacio()){
				String periodo = clavesCopia.elegir();
				claves.agregar(periodo);
				clavesCopia.sacar(periodo);
			}

			recolectarPeriodos(((ArbolPrecipitaciones)nodo.hijoIzquierdo).raiz, periodos);
			recolectarPeriodos(((ArbolPrecipitaciones)nodo.hijoDerecho).raiz, periodos);
		}
	}
	@Override
	public ColaPrioridadTDA precipitaciones(String periodo) {
		ColaPrioridad resultado = new ColaPrioridad();
		resultado.inicializarCola();
		
		// Usar un diccionario para acumular precipitaciones por día
		DiccionarioSimple acumuladorDias = new DiccionarioSimple();
		acumuladorDias.inicializar();
		DiccionarioSimple contadorCampos = new DiccionarioSimple();
		contadorCampos.inicializar();
		
		// Recopilar precipitaciones de todo el árbol
		recopilarPrecipitacionesPeriodo(raiz, periodo, acumuladorDias, contadorCampos);
		
		// Calcular promedios y armar la cola resultado
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
	
	private void recopilarPrecipitacionesPeriodo(nodoArbol nodo, String periodo, 
												DiccionarioSimpleTDA acumulador, 
												DiccionarioSimpleTDA contador) {
		if(nodo == null) return;
		
		// Procesar nodo actual
		if(nodo.mensualPrecipitaciones.claves().pertenece(periodo)) {
			DiccionarioSimpleTDA precipitacionesNodo = nodo.mensualPrecipitaciones.recuperar(periodo);
			ConjuntoTDA dias = precipitacionesNodo.obtenerClaves();
			
			while(!dias.estaVacio()) {
				int dia = dias.elegir();
				int precipitacion = precipitacionesNodo.recuperar(dia);
				
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
				
				dias.sacar(dia);
			}
		}
		
		// Recursión en subárboles
		recopilarPrecipitacionesPeriodo(((ArbolPrecipitaciones)nodo.hijoIzquierdo).raiz, periodo, acumulador, contador);
		recopilarPrecipitacionesPeriodo(((ArbolPrecipitaciones)nodo.hijoDerecho).raiz, periodo, acumulador, contador);
	}
	
	@Override
	public ABBPrecipitacionesTDA hijoIzq() {
		return raiz.hijoIzquierdo;
	}

	@Override
	public ABBPrecipitacionesTDA hijoDer() {
		return raiz.hijoDerecho;
	}

	@Override
	public boolean arbolVacio() {
		return raiz == null;
	}
	
	/**
	 * Determina si el primer valor es menor que el segundo valor
	 * */
	private boolean esMenor(String v1, String v2) {
		return v1.compareToIgnoreCase(v2) < 0;
	}
	
	/**
	 * Determina si el primer valor es mayor que el segundo valor
	 * */
	private boolean esMayor(String v1, String v2) {
		return v1.compareToIgnoreCase(v2) > 0;
	}
}
