package implementacion;

import java.util.Random;
import tdas.ConjuntoStringTDA;

public class ConjuntoString implements ConjuntoStringTDA {

	class nodo {
		String valor;
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
	
	private int hash(String valor) {
		if (valor == null) return 0;
		return Math.abs(valor.hashCode()) % capacidad;
	}
	
	private void redimensionar() {
		if (size >= capacidad * FACTOR_CARGA) {
			nodo[] viejosBuckets = buckets;
			int viejaCapacidad = capacidad;
			
			capacidad *= 2;
			buckets = new nodo[capacidad];
			size = 0;
			
			// Reinsertar todos los elementos recorriendo las cadenas
			for (int i = 0; i < viejaCapacidad; i++) {
				nodo actual = viejosBuckets[i];
				while (actual != null) {
					agregarSinRedimensionar(actual.valor);
					actual = actual.siguiente;
				}
			}
		}
	}
	
	private void agregarSinRedimensionar(String valor) {
		int indice = hash(valor);
		
		nodo actual = buckets[indice];
		while (actual != null) {
			if (actual.valor.equals(valor)) {
				return; // Ya existe - Ignoramos
			}
			actual = actual.siguiente;  // Mismo hash, manejamos colisiones.
		}
		
		nodo nuevo = new nodo();
		nuevo.valor = valor;
		nuevo.siguiente = buckets[indice];
		buckets[indice] = nuevo;
		size++;
	}

	@Override
	public void agregar(String valor) {
		redimensionar();
		agregarSinRedimensionar(valor);
	}

	@Override
	public boolean pertenece(String valor) {
		int indice = hash(valor);
		
		nodo actual = buckets[indice];
		// Accedemos a la posicion donde deberia estar
		// y verificamos que efectivamente esté
		while (actual != null) {
			if (actual.valor.equals(valor)) {
				return true;
			}
			actual = actual.siguiente;
		}
		
		return false;
	}
	@Override
	public String elegir() {
		if (estaVacio()) return null;
		
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

	@Override
	public void sacar(String valor) {
		int indice = hash(valor);
		
		if (buckets[indice] == null) return;
		
		// Caso 1: Primer elemento de la cadena
		if (buckets[indice].valor.equals(valor)) {
			buckets[indice] = buckets[indice].siguiente;
			size--;
			return;
		}
		
		// Caso 2: Elemento en NO es el primero de la cadena
		nodo actual = buckets[indice];
		while (actual.siguiente != null) {
			if (actual.siguiente.valor.equals(valor)) {
				actual.siguiente = actual.siguiente.siguiente;
				size--;
				return;
			}
			actual = actual.siguiente;
		}
	}

}
