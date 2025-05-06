package tda;

public interface PilaTDA {
    /**
     * Este método inicializa la estructura
     * Costo: Constante
     */
    public void inicializar();

    /**
     * Método para apilar
     * Debe estar inicializada
     * Costo: Constante
     *
     * @param valor
     */
    public void apilar(int valor);

    /**
     * Método para desapilar
     * No tiene que estar vacía
     * Costo: Constante
     */
    public void desapilar();

    /**
     * Método para obtener el valor del tope
     * No tiene que estar vacía
     * Costo: Constante
     * 
     * @return int Valor del tope
     */
    public int tope();

    /**
     * Método para saber si la pila está vacía
     * Tiene que estar inicializada
     * Costo: Constante
     * 
     * @return boolean True si está vacía, false si no está vacía
     */
    public boolean estaVacia();
}