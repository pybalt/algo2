package implementacion;

import tdas.ConjuntoStringTDA;
import tdas.DiccionarioSimpleStringTDA;
import tdas.DiccionarioSimpleTDA;

public class DiccionarioSimpleString implements DiccionarioSimpleStringTDA {

	class nodo{
		String periodo;
		DiccionarioSimpleTDA precipitacionesMes;
		nodo siguiente;
	}
	
	private nodo[] buckets;
	private int capacidad;
	private int size;
	private static final int CAPACIDAD_INICIAL = 16;
	private static final double FACTOR_CARGA = 0.75;
	
	/**
	 * Inicializa el diccionario de strings creando un hash table vacío para periodos.
	 * 
	 * Configuración inicial:
	 * - Capacidad inicial: 16 buckets para periodos
	 * - Tamaño: 0 periodos almacenados
	 * - Array de buckets: todos los índices apuntan a null
	 * 
	 * Este diccionario está diseñado para almacenar periodos (strings como "202401") 
	 * que mapean a DiccionarioSimpleTDA internos para las precipitaciones diarias.
	 * 
	 * Estructura resultante:
	 * - Nivel 1: Hash table de periodos (este diccionario)
	 * - Nivel 2: Cada periodo contiene un DiccionarioSimple para días→precipitaciones
	 * 
	 * Ejemplo de uso:
	 * DiccionarioSimpleString dic = new DiccionarioSimpleString();
	 * dic.inicializarDiccionario(); // Listo para agregar periodos
	 * 
	 * Complejidad: O(1) - Operación constante
	 */
	@Override
	public void inicializarDiccionario() {
		capacidad = CAPACIDAD_INICIAL;
		buckets = new nodo[capacidad];
		size = 0;
	}

	/**
	 * Función hash que mapea un String periodo a un índice válido del array de buckets.
	 * 
	 * Esta función es esencial para el rendimiento del hash table de strings ya que:
	 * - Convierte strings en índices numéricos para acceso directo
	 * - Distribuye los periodos uniformemente entre buckets para minimizar colisiones
	 * - Aprovecha el hashCode() optimizado de Java String
	 * - Garantiza acceso O(1) promedio a los elementos
	 * 
	 * Algoritmo:
	 * 1. Verificación null: Si periodo es null, retorna 0 (bucket por defecto)
	 * 2. periodo.hashCode(): Usa la función hash nativa de Java String (muy eficiente)
	 * 3. Math.abs(): Convierte a positivo para evitar índices negativos
	 * 4. % capacidad: Reduce al rango válido [0, capacidad-1]
	 * 
	 * Ejemplo: Si capacidad=16 y periodo="202401"
	 * - "202401".hashCode() = -1477327617 (calculado por Java)
	 * - Math.abs(-1477327617) = 1477327617
	 * - 1477327617 % 16 = 1
	 * - Resultado: bucket índice 1

	 * @param periodo El string periodo a mapear (formato típico: "YYYYMM")
	 * @return Índice del bucket donde debe almacenarse/buscarse el periodo (0 ≤ resultado < capacidad)
	 */
	private int hash(String periodo) {
		if (periodo == null) return 0;
		return Math.abs(periodo.hashCode()) % capacidad;
	}

	/**
	 * Redimensiona automáticamente el hash table de strings cuando el factor de carga excede el límite.
	 * 
	 * Esta función es esencial para mantener el rendimiento O(1) del diccionario de periodos:
	 * - Evita que las listas de periodos en cada bucket se vuelvan muy largas
	 * - Mantiene el factor de carga bajo 0.75 para minimizar colisiones entre periodos
	 * - Redistribuye periodos con nueva función hash para mejor distribución temporal
	 * 
	 * ¿Cuándo se ejecuta?
	 * - Cuando size >= capacidad * FACTOR_CARGA (0.75)
	 * - Ejemplo: capacidad=16, se redimensiona cuando tenemos ≥12 periodos
	 * 
	 * Proceso de redimensionamiento:
	 * 1. Guardar referencia al array actual (viejosBuckets)
	 * 2. Duplicar la capacidad (16 → 32 → 64...)
	 * 3. Crear nuevo array de buckets con nueva capacidad
	 * 4. Resetear size a 0
	 * 5. Reubicar cada nodo directamente (sin usar agregar() para evitar recursión)
	 * 
	 * ¿Por qué rehash para strings?
	 * - Con nueva capacidad, hash(periodo) % capacidad da diferentes índices
	 * - Ejemplo: "202401", antes hash("202401")%16=1, después hash("202401")%32=17
	 * - Esto redistribuye periodos estacionales que podrían chocar
	 * 
	 * Optimización especial:
	 * - Mueve nodos directamente en lugar de recrearlos (más eficiente)
	 * - Preserva los DiccionarioSimple internos intactos
	 * - Solo recalcula la posición del nodo periodo en el array principal
	 * 
	 * Complejidad: O(n) donde n es el número de periodos
	 * 
	 * Nota crítica: Este método NO llama a agregar() recursivamente, sino que
	 * reubica directamente los nodos para evitar problemas de recursión infinita.
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
					nodo siguiente = actual.siguiente;
					int nuevoIndice = hash(actual.periodo);
					actual.siguiente = buckets[nuevoIndice];
					buckets[nuevoIndice] = actual;
					size++;
					actual = siguiente;
				}
			}
		}
	}

	/**
	 * Agrega una medición de precipitación para un día específico dentro de un periodo.
	 * 
	 * Comportamiento multinivel:
	 * - Si el periodo ya existe: agrega/actualiza la precipitación del día en su diccionario interno
	 * - Si el periodo no existe: crea nuevo periodo con su propio DiccionarioSimple interno
	 * - Cada periodo mantiene un hash table separado para sus días de precipitación
	 * 
	 * Proceso detallado:
	 * 1. Calcular hash(periodo) para encontrar bucket del periodo
	 * 2. Buscar periodo en la lista enlazada del bucket
	 * 3. Si periodo existe: llamar precipitacionesMes.agregar(dia, cantidad)
	 * 4. Si periodo no existe:
	 *    a. Crear nuevo nodo con el periodo
	 *    b. Crear nuevo DiccionarioSimple interno e inicializarlo
	 *    c. Agregar la primera medición (dia, cantidad)
	 *    d. Insertar nodo al principio del bucket
	 * 5. Verificar redimensionamiento si se agregó nuevo periodo
	 * 
	 * Estructura de datos resultante:
	 * - Hash table principal: periodo → DiccionarioSimple
	 * - Hash table interno: dia → cantidad de precipitación
	 * 
	 * Ejemplo:
	 * dic.agregar("202401", 15, 25); // Enero 2024, día 15: 25mm
	 * dic.agregar("202401", 20, 30); // Enero 2024, día 20: 30mm  
	 * dic.agregar("202402", 5, 15);  // Febrero 2024, día 5: 15mm
	 * 
	 * @param periodo String que representa el periodo (típicamente "YYYYMM")
	 * @param dia Día del mes (1-31)
	 * @param cantidad Cantidad de precipitación en mm
	 * 
	 * Complejidad: O(1) promedio para ambos niveles de hash table
	 * Peor caso: O(k1 + k2) donde k1 = tamaño bucket periodo, k2 = tamaño bucket día
	 * Nota: Redimensionamiento amortizado a O(1)
	 */
	@Override
	public void agregar(String periodo, int dia, int cantidad) {
		int indice = hash(periodo);
		nodo actual = buckets[indice];
		
		while(actual != null) {
			if(actual.periodo.equals(periodo)) {
				actual.precipitacionesMes.agregar(dia, cantidad);
				return;
			}
			actual = actual.siguiente;
		}
		
		nodo nuevoNodo = new nodo();
		nuevoNodo.periodo = periodo;
		nuevoNodo.precipitacionesMes = new DiccionarioSimple();
		nuevoNodo.precipitacionesMes.inicializar();
		nuevoNodo.precipitacionesMes.agregar(dia, cantidad);
		nuevoNodo.siguiente = buckets[indice];
		buckets[indice] = nuevoNodo;
		size++;
		
		redimensionar();
	}

	/**
	 * Elimina completamente un periodo y todas sus mediciones de precipitación asociadas.
	 * 
	 * Comportamiento:
	 * - Si el periodo existe: lo elimina junto con todo su DiccionarioSimple interno
	 * - Si el periodo no existe: no hace nada (operación segura)
	 * - Libera la memoria del DiccionarioSimple interno y todos sus días de precipitación
	 * 
	 * Implicaciones importantes:
	 * - Se pierden TODAS las mediciones del periodo eliminado
	 * - Si el periodo tenía 31 días de datos, se eliminan todos
	 * - Es una operación irreversible
	 * 
	 * Proceso:
	 * 1. Calcular hash(periodo) para encontrar bucket correcto
	 * 2. Verificar si el bucket está vacío → terminar
	 * 3. Si el primer nodo tiene el periodo → eliminarlo y ajustar puntero
	 * 4. Si no, recorrer lista enlazada hasta encontrar el periodo
	 * 5. Eliminar nodo y reconectar lista enlazada
	 * 6. Decrementar size del diccionario principal
	 * 
	 * Casos especiales:
	 * - Bucket vacío: return inmediato
	 * - Primer nodo: buckets[indice] = actual.siguiente
	 * - Nodo intermedio: ajustar punteros de nodos adyacentes
	 * 
	 * Ejemplo:
	 * dic.agregar("202401", 15, 25); // Enero con 1 medición
	 * dic.agregar("202401", 20, 30); // Enero con 2 mediciones
	 * dic.eliminar("202401"); // ¡Elimina TODAS las mediciones de enero!
	 * dic.eliminar("202412"); // No hace nada si diciembre no existe
	 * 
	 * @param periodo El periodo a eliminar completamente
	 * 
	 * Complejidad: O(1) promedio, O(k) peor caso donde k es el tamaño del bucket
	 * Nota: La eliminación del DiccionarioSimple interno es automática (garbage collection)
	 * Precondición: El diccionario debe estar inicializado
	 */
	@Override
	public void eliminar(String periodo) {
		int indice = hash(periodo);
		nodo actual = buckets[indice];
		
		if(actual == null) {
			return;
		}
		
		if(actual.periodo.equals(periodo)) {
			buckets[indice] = actual.siguiente;
			size--;
			return;
		}
		
		while(actual.siguiente != null) {
			if(actual.siguiente.periodo.equals(periodo)) {
				actual.siguiente = actual.siguiente.siguiente;
				size--;
				return;
			}
			actual = actual.siguiente;
		}
	}

	/**
	 * Recupera el diccionario de precipitaciones diarias asociado a un periodo específico.
	 * 
	 * Comportamiento:
	 * - Si el periodo existe: devuelve su DiccionarioSimpleTDA interno con los días/precipitaciones
	 * - Si el periodo no existe: devuelve null
	 * 
	 * El DiccionarioSimpleTDA devuelto contiene:
	 * - Claves: días del mes (1-31)
	 * - Valores: cantidad de precipitación en mm para cada día
	 * - Implementación: hash table para acceso O(1) a días específicos
	 * 
	 * Proceso:
	 * 1. Calcular hash(periodo) para encontrar bucket correcto
	 * 2. Recorrer lista enlazada del bucket
	 * 3. Comparar cada nodo hasta encontrar el periodo
	 * 4. Devolver precipitacionesMes si se encuentra, null si no
	 * 
	 * Casos de uso típicos:
	 * - Obtener todas las precipitaciones de un mes específico
	 * - Acceder al diccionario interno para consultas por día
	 * - Verificar si un periodo tiene datos (null = no existe)
	 * 
	 * Ejemplo:
	 * dic.agregar("202401", 15, 25);
	 * dic.agregar("202401", 20, 30);
	 * DiccionarioSimpleTDA enero = dic.recuperar("202401");
	 * if (enero != null) {
	 *     int lluvia15 = enero.recuperar(15); // Retorna 25
	 *     ConjuntoTDA dias = enero.obtenerClaves(); // Contiene {15, 20}
	 * }
	 * DiccionarioSimpleTDA noExiste = dic.recuperar("202412"); // Retorna null
	 * 
	 * @param periodo El periodo cuyo diccionario de precipitaciones se quiere obtener
	 * @return DiccionarioSimpleTDA con las precipitaciones del periodo, o null si no existe
	 * 
	 * Complejidad: O(1) promedio, O(k) peor caso donde k es el tamaño del bucket
	 * Precondición: El diccionario debe estar inicializado
	 * Postcondición: El diccionario permanece sin cambios
	 * Nota: El diccionario devuelto es una referencia, no una copia
	 */
	@Override
	public DiccionarioSimpleTDA recuperar(String periodo) {
		int indice = hash(periodo);
		nodo actual = buckets[indice];
		
		while(actual != null) {
			if(actual.periodo.equals(periodo)) {
				return actual.precipitacionesMes;
			}
			actual = actual.siguiente;
		}
		
		return null;
	}

	/**
	 * Obtiene un conjunto con todos los periodos presentes en el diccionario.
	 * 
	 * Comportamiento:
	 * - Crea un nuevo ConjuntoStringTDA con todos los periodos del diccionario
	 * - El conjunto resultante no tiene duplicados (propiedad del conjunto)
	 * - El orden de los periodos depende del recorrido de los buckets
	 * 
	 * Utilidad:
	 * - Conocer qué periodos tienen datos de precipitación
	 * - Iterar sobre todos los periodos disponibles
	 * - Verificar la cobertura temporal de los datos
	 * 
	 * Proceso:
	 * 1. Crear nuevo conjunto de strings vacío
	 * 2. Recorrer todos los buckets del hash table (0 a capacidad-1)
	 * 3. Para cada bucket, recorrer su lista enlazada
	 * 4. Agregar cada periodo encontrado al conjunto
	 * 5. Devolver conjunto completo
	 * 
	 * Características del resultado:
	 * - Contiene exactamente los periodos que tienen datos
	 * - No contiene duplicados (garantizado por ConjuntoStringTDA)
	 * - Es una copia independiente (modificar conjunto no afecta diccionario)
	 * - Cada periodo en el conjunto tiene al menos una medición de precipitación
	 * 
	 * Ejemplo:
	 * dic.agregar("202401", 15, 25); // Enero 2024
	 * dic.agregar("202402", 10, 20); // Febrero 2024
	 * dic.agregar("202403", 5, 30);  // Marzo 2024
	 * ConjuntoStringTDA periodos = dic.claves(); // {"202401", "202402", "202403"}
	 * 
	 * Casos de uso:
	 * - Generar reportes de cobertura temporal
	 * - Validar rangos de datos disponibles
	 * - Iterar sobre todos los meses con datos
	 * 
	 * @return Un nuevo conjunto con todos los periodos del diccionario
	 * 
	 * Complejidad: O(n) donde n es el número total de periodos en el diccionario
	 * Nota: Debe visitar todos los periodos para construir el conjunto
	 * Precondición: El diccionario debe estar inicializado
	 * Postcondición: El diccionario permanece sin cambios
	 */
	@Override
	public ConjuntoStringTDA claves() {
		ConjuntoStringTDA conjunto = new ConjuntoString();
		conjunto.inicializar();
		
		for (int i = 0; i < capacidad; i++) {
			nodo actual = buckets[i];
			while(actual != null) {
				conjunto.agregar(actual.periodo);
				actual = actual.siguiente;
			}
		}
		
		return conjunto;
	}
}
