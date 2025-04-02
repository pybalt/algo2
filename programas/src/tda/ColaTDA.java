package tda;

/**
 * FIFO: First In First Out
 */
public interface ColaTDA {
    /**
     * Agregamos un elemento al final de la estructura
     * @param valor
     */
    void acolar(int valor);

    void desacolar();

    boolean estaVacia();

    int primero();

    void inicializar();
}
