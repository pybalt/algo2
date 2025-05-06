package tda;

public interface ColaPrioridadTDA {
    /**
     * Costo: Constante
     */
    public void inicializar();

    /**
     * Método par acolar
     * Debe estar inicializada
     *
     * Costo: Lineal
     * 
     * @param valor
     * @param prioridad
     */
    public void acolar(int valor, int prioridad);

    /**
     * Método para desacolar
     * No tiene que estar vacía
     * 
     * Costo: Lineal
     */
    public void desacolar();

    /**
     * Método para saber si la cola está vacía
     * Tiene que estar inicializada
     * 
     * Costo: Constante
     *
     * @return boolean True si está vacía, false si no está vacía
     */
    public boolean estaVacia();

    /**
     * Método para obtener el primer valor
     * No tiene que estar vacía
     *
     * Costo: Constante
     * @return int Valor del primer elemento
     */
    public int primero();

    /**
     * Método para obtener la prioridad del primer valor
     * No tiene que estar vacía
     *
     * Costo: Constante
     * @return int Prioridad del primer elemento
     */
    public int prioridad();
}
