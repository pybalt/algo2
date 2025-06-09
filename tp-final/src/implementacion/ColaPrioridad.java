package implementacion;

import tdas.ColaPrioridadTDA;

public class ColaPrioridad implements ColaPrioridadTDA {
	
	class nodo {
		int prioridad;
		int valor;
		nodo siguiente;
	}
	
	private nodo primero;
	@Override
	public void inicializarCola() {
		primero = null;
	}


	@Override
	public void acolarPrioridad(int valor, int prioridad) {
		nodo nuevoNodo = creaNodo(valor, prioridad);
		/*
		 * Primer escenario, una de dos:
		 * 1. Cola vacia
		 * 2. Nuevo nodo es mas prioritario que el primero
		 */
		if(primero == null || prioridad < primero.prioridad){
			nuevoNodo.siguiente = primero;
			primero = nuevoNodo;
			return;
		}
		/*
		 * Segundo escenario, insertamos ordenado
		 * Para insertar ordenado:
		 * 1. Vamos recorriendo la lista.
		 * 2. Si el nodo siguiente tiene menos(o igual) prioridad,
		 * 		quiere decir que estamos en el limite entre
		 * 		mayor prioridad <= prioridad insertada <= menor prioridad
		 * 3. El ultimo caso seria que insertamos justo al final de la cola.
		 * En ese caso tenemos que asegurarnos que terminamos de recorrer
		 * los nodos, es decir, el siguiente del actual es nulo
		 */
		nodo actual = primero;

		while(actual.siguiente != null && actual.siguiente.prioridad <= prioridad){
			actual = actual.siguiente;
		}
		// Reordenamos referencias
		nuevoNodo.siguiente = actual.siguiente;
		actual.siguiente = nuevoNodo;
	}

	private nodo creaNodo(int valor, int prioridad){
		nodo nuevoNodo = new nodo();
		nuevoNodo.valor = valor;
		nuevoNodo.prioridad = prioridad;
		return nuevoNodo;
	}

	@Override
	public void desacolar() {
		if(primero != null){
			primero = primero.siguiente;
		}
	}


	@Override
	public int primero() {
		return primero.valor;
	}


	@Override
	public int prioridad() {
		return primero.prioridad;
	}


	@Override
	public boolean colaVacia() {
		if(primero == null){
			return true;
		}
		return false;
	}

}