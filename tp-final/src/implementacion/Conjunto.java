package implementacion;

import java.util.Random;
import tdas.ConjuntoTDA;

public class Conjunto implements ConjuntoTDA {
	
	class nodo {
		int valor;
		nodo siguiente;
	}
	
	private nodo primero;
	private int cantidad;
	private Random r;
	
	@Override
	public void inicializar() {
		primero = null;
		cantidad = 0;
		r = new Random();
	}

	@Override
	public void agregar(int valor) {
		if(!pertenece(valor)){
			nodo nuevoNodo = new nodo();
			nuevoNodo.valor = valor;
			nuevoNodo.siguiente = primero;
			primero = nuevoNodo;
			cantidad++;
		}
	}

	@Override
	public boolean pertenece(int valor) {
		nodo actual = primero;
		while(actual != null){
			if(actual.valor == valor){
				return true;
			}
			actual = actual.siguiente;
		}
		return false;
	}

	@Override
	public void sacar(int valor) {
		if(primero == null){
			return;
		}
		// Caso 1. El primero es el valor
		if(primero.valor == valor){
			primero = primero.siguiente;
			cantidad --;
			return;
		}
		// Caso 2. El primero no es el valor.
		nodo actual = primero;
		while(actual.siguiente != null){
			if(actual.siguiente.valor == valor){
				actual.siguiente = actual.siguiente.siguiente;
				cantidad--;
				return;
			}
			actual = actual.siguiente;
		}
	}

	@Override
	public int elegir() {
		int qty = r.nextInt(cantidad);
		nodo actual = primero;
		for (int i = 0; i < qty; i++) {
			actual = actual.siguiente;
		}
		return actual.valor;
	}

	@Override
	public boolean estaVacio() {
		return primero == null;
	}

}