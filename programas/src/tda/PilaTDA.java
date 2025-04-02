package tda;

/**
 * Interfaz para una estructura de datos tipo Pila (Stack)
 * Sigue el principio LIFO (Last In, First Out)
 */
public interface PilaTDA {
    
    /**
     * Agrega un elemento en el tope de la pila
     * @param valor Elemento a agregar
     * Debe estar inicializada la pila
     */
    void apilar(int valor);
    
    /**
     * Elimina el elemento del tope de la pila
     * Debe estar inicializada la pila
     * Debe haber elementos en la pila
     */
    void desapilar();
    
    /**
     * Verifica si la pila está vacía
     * @return true si la pila está vacía, false en caso contrario
     * Debe estar inicializada la pila
     */
    boolean estaVacia();
    
    /**
     * Devuelve el elemento del tope de la pila sin eliminarlo
     * @return Elemento del tope de la pila
     * Debe estar inicializada la pila
     * Debe haber elementos en la pila
     */
    int tope();
    
    /**
     * Inicializa la pila, dejándola vacía
     */
    void inicializar();
} 