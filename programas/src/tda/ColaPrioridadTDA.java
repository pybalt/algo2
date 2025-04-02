package tda;
public interface ColaPrioridadTDA {
    /**
     * Inicializa la cola de prioridad
     */
    void inicializar();
    
    /**
     * Agrega un elemento a la cola con su prioridad
     * Precondición: La cola debe estar inicializada
     * @param valor Valor a acolar
     * @param prioridad Prioridad del elemento
     */
    void acolar(int valor, int prioridad);
    
    /**
     * Elimina el elemento de mayor prioridad
     * Precondición: La cola debe estar inicializada y no vacía
     */
    void desacolar();
    
    /**
     * Verifica si la cola está vacía
     * Precondición: La cola debe estar inicializada
     * @return true si está vacía, false en caso contrario
     */
    boolean estaVacia();
    
    /**
     * Devuelve el valor del elemento de mayor prioridad
     * Precondición: La cola debe estar inicializada y no vacía
     * @return El valor del primer elemento
     */
    int primero();
    
    /**
     * Devuelve la prioridad del elemento de mayor prioridad
     * Precondición: La cola debe estar inicializada y no vacía
     * @return La prioridad del primer elemento
     */
    int prioridad();
}