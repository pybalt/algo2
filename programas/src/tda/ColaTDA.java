package tda;

public interface ColaTDA {
    /**
     * Este método inicializa la estructura
     */
    public void inicializar();

    /**
     * Método para acolar
     * Debe estar inicializada la cola
     *
     * @param valor
     */
    public void acolar(int valor);

    /**
     * Método para desacolar
     * No tiene que estar vacía
     */
    public void desacolar();

    /**
     * Método para obtener el primer valor
     * No tiene que estar vacía
     *
     * @return int Valor del primer elemento
     */
    public int primero();

    /**
     * Método para saber si la cola está vacía
     * Tiene que estar inicializada
     *
     * @return boolean True si está vacía, false si no está vacía
     */
    public boolean estaVacia();
}
