package tda;

public interface ListaEspecialTDA {
    /**
     * Método para inicializar la lista
     */
    public void inicializar();

    /**
     * Método para agregar un elemento a la lista
     * No tiene que estar vacío
     *
     * @param value
     */
    public void agregar(int value);

    /**
     * Método para eliminar un elemento de la lista
     * No tiene que estar vacío
     *
     * @param value
     */
    public void eliminar(int value);

    /**
     * Método para obtener el primer valor
     * No tiene que estar vacío
     *
     * @return int Valor del primer elemento
     */
    public int primero();

    /**
     * Método para saber si la lista está vacía
     * Tiene que estar inicializada
     *
     * @return boolean True si está vacía, false si no está vacía
     */
    public boolean estaVacia();
}
