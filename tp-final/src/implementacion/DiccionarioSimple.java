package implementacion;

import tdas.ConjuntoTDA;

public class DiccionarioSimple implements tdas.DiccionarioSimpleTDA {

	class nodo{
		int clave;
		int valor;
		nodo siguiente;
	}
	
	private nodo[] buckets;
	private int capacidad;
	private int size;
	private static final int CAPACIDAD_INICIAL = 16;
	private static final double FACTOR_CARGA = 0.75;
	
	/**
	 * Inicializa el diccionario simple creando un hash table vacío.
	 * 
	 * Configuración inicial:
	 * - Capacidad inicial: 16 buckets
	 * - Tamaño: 0 elementos
	 * - Array de buckets: todos los índices apuntan a null
	 * 
	 * Este método debe llamarse antes de usar cualquier otra operación del diccionario.
	 * 
	 * Ejemplo de uso:
	 * DiccionarioSimple dic = new DiccionarioSimple();
	 * dic.inicializar(); // Ahora está listo para usar
	 * 
	 * Complejidad: O(1) - Operación constante
	 */
	@Override
	public void inicializar() {
		capacidad = CAPACIDAD_INICIAL;
		buckets = new nodo[capacidad];
		size = 0;
	}

	/**
	 * Función hash que mapea una clave entera a un índice válido del array de buckets.
	 * 
	 * Esta función es fundamental para el funcionamiento del hash table ya que:
	 * - Distribuye las claves uniformemente entre los buckets disponibles
	 * - Garantiza que el índice esté en el rango [0, capacidad-1]
	 * - Permite acceso directo O(1) a los elementos
	 * 
	 * Algoritmo:
	 * 1. Math.abs(clave): Convierte la clave a un valor positivo para evitar índices negativos
	 * 2. % capacidad: Operación módulo que reduce el valor al rango [0, capacidad-1]
	 * 
	 * Ejemplo: Si capacidad=16 y clave=42
	 * - Math.abs(42) = 42
	 * - 42 % 16 = 10
	 * - Resultado: bucket índice 10
	 * 
	 * @param clave La clave entera a mapear
	 * @return Índice del bucket donde debe almacenarse/buscarse la clave (0 ≤ resultado < capacidad)
	 */
	private int hash(int clave) {
		return Math.abs(clave) % capacidad;
	}

	/**
	 * Redimensiona automáticamente el hash table cuando el factor de carga excede el límite.
	 * 
	 * Esta función es crítica para mantener el rendimiento O(1) del hash table:
	 * - Previene que las listas enlazadas en los buckets se vuelvan muy largas
	 * - Mantiene el factor de carga bajo 0.75 para minimizar colisiones
	 * - Redistribuye elementos con nueva función hash para mejor distribución
	 * 
	 * ¿Cuándo se ejecuta?
	 * - Cuando size >= capacidad * FACTOR_CARGA (0.75)
	 * - Ejemplo: capacidad=16, se redimensiona cuando size>=12
	 * 
	 * Proceso de redimensionamiento:
	 * 1. Guardar referencia al array actual (viejosBuckets)
	 * 2. Duplicar la capacidad (16 → 32 → 64...)
	 * 3. Crear nuevo array de buckets con nueva capacidad
	 * 4. Resetear size a 0
	 * 5. Re-insertar todos los elementos usando nueva función hash
	 * 
	 * ¿Por qué rehash?
	 * - Con nueva capacidad, hash(clave) % capacidad da diferentes índices
	 * - Ejemplo: clave=23, antes hash(23)%16=7, después hash(23)%32=23
	 * - Esto redistribuye elementos y reduce colisiones
	 * 
	 * Complejidad: O(n) donde n es el número de elementos
	 * Frecuencia: Logarítmica en el número total de inserciones
	 * 
	 * Nota: Se usa agregar() para reinsertar, lo que recalcula automáticamente
	 * los índices con la nueva capacidad y maneja correctamente las colisiones.
	 */
	private void redimensionar() {
		if (size >= capacidad * FACTOR_CARGA) {
			nodo[] viejosBuckets = buckets;
			int viejaCapacidad = capacidad;
			
			capacidad *= 2;
			buckets = new nodo[capacidad];
			size = 0;
			
			for (int i = 0; i < viejaCapacidad; i++) {
				nodo actual = viejosBuckets[i];
				while (actual != null) {
					agregar(actual.clave, actual.valor);
					actual = actual.siguiente;
				}
			}
		}
	}

	/**
	 * Agrega una nueva asociación clave-valor al diccionario o actualiza el valor si la clave ya existe.
	 * 
	 * Comportamiento:
	 * - Si la clave ya existe: actualiza su valor
	 * - Si la clave no existe: crea nueva entrada al principio del bucket correspondiente
	 * - Incrementa el tamaño solo cuando se agrega una nueva clave
	 * - Puede disparar redimensionamiento automático si factor de carga > 0.75
	 * 
	 * Proceso:
	 * 1. Calcular hash(clave) para encontrar bucket correcto
	 * 2. Buscar clave en la lista enlazada del bucket
	 * 3. Si existe: actualizar valor y terminar
	 * 4. Si no existe: crear nuevo nodo al inicio del bucket
	 * 5. Verificar si necesita redimensionamiento
	 * 
	 * Ejemplo:
	 * dic.agregar(42, 100); // Nueva entrada
	 * dic.agregar(42, 200); // Actualiza valor a 200
	 * 
	 * @param clave La clave entera a agregar/actualizar
	 * @param valor El valor asociado a la clave
	 * 
	 * Complejidad: O(1) promedio, O(n) peor caso (todas las claves chocan en un bucket)
	 * Nota: El redimensionamiento es O(n) pero amortizado a O(1) por la frecuencia logarítmica
	 */
	@Override
	public void agregar(int clave, int valor) {
		int indice = hash(clave);
		nodo actual = buckets[indice];
		
		while(actual != null) {
			if(actual.clave == clave) {
				actual.valor = valor;
				return;
			}
			actual = actual.siguiente;
		}
		
		nodo nuevoNodo = new nodo();
		nuevoNodo.clave = clave;
		nuevoNodo.valor = valor;
		nuevoNodo.siguiente = buckets[indice];
		buckets[indice] = nuevoNodo;
		size++;
		
		redimensionar();
	}

	/**
	 * Elimina una clave y su valor asociado del diccionario.
	 * 
	 * Comportamiento:
	 * - Si la clave existe: la elimina y decrementa el tamaño
	 * - Si la clave no existe: no hace nada (operación segura)
	 * - Mantiene la integridad de las listas enlazadas en los buckets
	 * 
	 * Proceso:
	 * 1. Calcular hash(clave) para encontrar bucket correcto
	 * 2. Verificar si el bucket está vacío → terminar
	 * 3. Si el primer nodo tiene la clave → eliminarlo y ajustar puntero
	 * 4. Si no, recorrer lista enlazada hasta encontrar la clave
	 * 5. Eliminar nodo y reconectar lista
	 * 
	 * Casos especiales:
	 * - Bucket vacío: return inmediato
	 * - Primer nodo: buckets[indice] = actual.siguiente
	 * - Nodo intermedio: ajustar punteros de nodos adyacentes
	 * 
	 * Ejemplo:
	 * dic.eliminar(42); // Elimina clave 42 si existe
	 * dic.eliminar(99); // No hace nada si clave 99 no existe
	 * 
	 * @param clave La clave entera a eliminar
	 * 
	 * Complejidad: O(1) promedio, O(k) peor caso donde k es el tamaño del bucket
	 * Precondición: El diccionario debe estar inicializado
	 */
	@Override
	public void eliminar(int clave) {
		int indice = hash(clave);
		nodo actual = buckets[indice];
		
		if(actual == null) {
			return;
		}
		
		if(actual.clave == clave) {
			buckets[indice] = actual.siguiente;
			size--;
			return;
		}
		
		while(actual.siguiente != null) {
			if(actual.siguiente.clave == clave) {
				actual.siguiente = actual.siguiente.siguiente;
				size--;
				return;
			}
			actual = actual.siguiente;
		}
	}

	/**
	 * Recupera el valor asociado a una clave específica.
	 * 
	 * Comportamiento:
	 * - Si la clave existe: devuelve su valor asociado
	 * - Si la clave no existe: devuelve 0 (según especificación del TDA)
	 * 
	 * Proceso:
	 * 1. Calcular hash(clave) para encontrar bucket correcto
	 * 2. Recorrer lista enlazada del bucket
	 * 3. Comparar cada nodo hasta encontrar la clave
	 * 4. Devolver valor si se encuentra, 0 si no
	 * 
	 * Optimización hash table:
	 * - Solo busca en un bucket específico (no en todo el diccionario)
	 * - Búsqueda promedio muy rápida gracias a la distribución uniforme
	 * 
	 * Ejemplo:
	 * dic.agregar(42, 100);
	 * int valor = dic.recuperar(42); // Retorna 100
	 * int noExiste = dic.recuperar(99); // Retorna 0
	 * 
	 * @param clave La clave entera cuyo valor se quiere recuperar
	 * @return El valor asociado a la clave, o 0 si la clave no existe
	 * 
	 * Complejidad: O(1) promedio, O(k) peor caso donde k es el tamaño del bucket
	 * Precondición: El diccionario debe estar inicializado
	 * Postcondición: El diccionario permanece sin cambios
	 */
	@Override
	public int recuperar(int clave) {
		int indice = hash(clave);
		nodo actual = buckets[indice];
		
		while(actual != null) {
			if(actual.clave == clave) {
				return actual.valor;
			}
			actual = actual.siguiente;
		}
		
		return 0;
	}

	/**
	 * Obtiene un conjunto con todas las claves presentes en el diccionario.
	 * 
	 * Comportamiento:
	 * - Crea un nuevo ConjuntoTDA con todas las claves del diccionario
	 * - El conjunto resultante no tiene duplicados (propiedad del conjunto)
	 * - El orden de las claves depende del recorrido de los buckets
	 * 
	 * Proceso:
	 * 1. Crear nuevo conjunto vacío
	 * 2. Recorrer todos los buckets del hash table (0 a capacidad-1)
	 * 3. Para cada bucket, recorrer su lista enlazada
	 * 4. Agregar cada clave encontrada al conjunto
	 * 5. Devolver conjunto completo
	 * 
	 * Características del resultado:
	 * - Contiene exactamente las claves que están en el diccionario
	 * - No contiene duplicados (garantizado por ConjuntoTDA)
	 * - Es una copia independiente (modificar conjunto no afecta diccionario)
	 * 
	 * Ejemplo:
	 * dic.agregar(10, 100); dic.agregar(20, 200); dic.agregar(30, 300);
	 * ConjuntoTDA claves = dic.obtenerClaves(); // Contiene {10, 20, 30}
	 * 
	 * @return Un nuevo conjunto con todas las claves del diccionario
	 * 
	 * Complejidad: O(n) donde n es el número total de elementos en el diccionario
	 * Nota: Debe visitar todos los elementos para construir el conjunto
	 * Precondición: El diccionario debe estar inicializado
	 * Postcondición: El diccionario permanece sin cambios
	 */
	@Override
	public ConjuntoTDA obtenerClaves() {
		ConjuntoTDA conjunto = new Conjunto();
		conjunto.inicializar();
		
		for (int i = 0; i < capacidad; i++) {
			nodo actual = buckets[i];
			while(actual != null) {
				conjunto.agregar(actual.clave);
				actual = actual.siguiente;
			}
		}
		
		return conjunto;
	}

}
