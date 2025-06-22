package implementacion;

import java.util.Random;
import tdas.ConjuntoTDA;

public class Conjunto implements ConjuntoTDA {
	
	class nodo {
		int valor;
		nodo siguiente;
	}
	
	private nodo[] buckets;
	private int capacidad;
	private int size;
	private Random r;
	private static final int CAPACIDAD_INICIAL = 16;
	private static final double FACTOR_CARGA = 0.85;
	
	@Override
	public void inicializar() {
		capacidad = CAPACIDAD_INICIAL;
		buckets = new nodo[capacidad];
		size = 0;
		r = new Random();
	}
	
	private int hash(int valor) {
		return Math.abs(valor) % capacidad;
	}
	
	private void redimensionar() {
		if (size >= capacidad * FACTOR_CARGA) {
			nodo[] viejosBuckets = buckets;
			int viejaCapacidad = capacidad;
			
			capacidad *= 2;
			buckets = new nodo[capacidad];
			size = 0;
			
			// Reinsertar todos los elementos recalculando hash
			for (int i = 0; i < viejaCapacidad; i++) {
				nodo actual = viejosBuckets[i];
				while (actual != null) {
					agregarSinRedimensionar(actual.valor);
					actual = actual.siguiente;
				}
			}
		}
	}
	
	private void agregarSinRedimensionar(int valor) {
		int indice = hash(valor);

		nodo actual = buckets[indice];
		while (actual != null) {
			if (actual.valor == valor) {
				return; // Ya existe - ignorar
			}
			actual = actual.siguiente;
		}

		nodo nuevo = new nodo();
		nuevo.valor = valor;
		nuevo.siguiente = buckets[indice];
		buckets[indice] = nuevo;
		size++;
	}

	@Override
	public void agregar(int valor) {
		redimensionar();
		agregarSinRedimensionar(valor);
	}

	@Override
	public boolean pertenece(int valor) {
		int indice = hash(valor);
		
		nodo actual = buckets[indice];
		while (actual != null) {
			if (actual.valor == valor) {
				return true;
			}
			actual = actual.siguiente;
		}
		
		return false;
	}

	@Override
	public void sacar(int valor) {
		int indice = hash(valor);
		
		if (buckets[indice] == null) return;
		
		// Caso 1: Primer elemento de la cadena
		if (buckets[indice].valor == valor) {
			buckets[indice] = buckets[indice].siguiente;
			size--;
			return;
		}
		
		// Caso 2: Elemento no es el primero
		nodo actual = buckets[indice];
		while (actual.siguiente != null) {
			if (actual.siguiente.valor == valor) {
				actual.siguiente = actual.siguiente.siguiente;
				size--;
				return;
			}
			actual = actual.siguiente;
		}
	}

	@Override
	public int elegir() {		
		int indice;
		do {
			indice = r.nextInt(capacidad);
		} while (buckets[indice] == null);
		
		// ¿Y si hay una colision? 
		// Tendriamos que ademas elegir aleatoriamente dentro de las colisiones
		nodo actual = buckets[indice];
		int longitud = 1;
		nodo temp = actual.siguiente;
		while (temp != null) {
			longitud++;
			temp = temp.siguiente;
		}
		
		int posicion = r.nextInt(longitud);
		for (int i = 0; i < posicion; i++) {
			actual = actual.siguiente;
		}
		
		return actual.valor;
	}

	@Override
	public boolean estaVacio() {
		return size == 0;
	}

}