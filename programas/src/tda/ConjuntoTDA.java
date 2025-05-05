package tda;

public interface ConjuntoTDA {
    /**
     * Este método inicializa la estructura
     */
    public void inicializar();

    /**
     * Método para agregar un elemento al conjunto
     * Debe estar inicializado
     *
     * @param valor
     */
    public void agregar(int valor);

    /**
     * Método para saber si un valor pertenece al conjunto
     * Debe estar inicializado
     *
     * @param valor
     */
    public boolean pertenece(int valor);

    /**
     * Método para eliminar un valor del conjunto
     * Debe estar inicializado y no vacío
     *
     * @param valor
     */
    public void sacar(int valor);

    /**
     * Elegir un elemento al azar del conjunto
     * Tiene que estar inicializado y no vacío
     *
     * @return int Elemento elegido
     */
    public int elegir();

    /**
     * Saber si el conjunto está vacío
     * Tiene que estar inicializado
     *
     * @return boolean True si está vacío, false si no está inicializado
     */
    public boolean estaVacio();
}
