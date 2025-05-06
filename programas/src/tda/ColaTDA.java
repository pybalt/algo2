package tda;

public interface ColaTDA {
    /**
     * Este método inicializa la estructura
     * 
     * Costo: Constante
     * 
     */
    public void inicializar();

    /**
     * Método para acolar
     * Debe estar inicializada la cola
     * 
     * Costo: Constante
     * 
     *
     * @param valor
     */
    public void acolar(int valor);

    /**
     * Método para desacolar
     * No tiene que estar vacía
     * 
     * Costo: Lineal
     * 
     */
    public void desacolar();

    /**
     * Método para obtener el primer valor
     * No tiene que estar vacía
     * 
     * Costo: Constante
     * 
     *
     * @return int Valor del primer elemento
     */
    public int primero();

    /**
     * Método para saber si la cola está vacía
     * Tiene que estar inicializada
     * 
     * Costo: Constante
     * 
     *
     * @return boolean True si está vacía, false si no está vacía
     */
    public boolean estaVacia();
}
