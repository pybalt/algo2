package tda;

public interface PilaTDA {
    /**
     * Este método inicializa la estructura
     */
    public void inicializar();

    /**
     * Método para apilar
     * Debe estar inicializada
     *
     * @param valor
     */
    public void apilar(int valor);

    /**
     * Método para desapilar
     * No tiene que estar vacía
     */
    public void desapilar();

    /**
     * Método para obtener el valor del tope
     * No tiene que estar vacía
     * @return int Valor del tope
     */
    public int tope();

    /**
     * Método para saber si la pila está vacía
     * Tiene que estar inicializada
     * @return boolean True si está vacía, false si no está vacía
     */
    public boolean estaVacia();
}