package tda;

public interface ConjuntoTDA {
    /**
     * Este método inicializa la estructura
     * 
     * Costo: Constante
     */
    public void inicializar();

    /**
     * Método para agregar un elemento al conjunto
     * Debe estar inicializado
     *
     * Costo: Lineal
     * 
     * @param valor
     */
    public void agregar(int valor);

    /**
     * Método para saber si un valor pertenece al conjunto
     * Debe estar inicializado
     *
     * Costo: Lineal
     * 
     * @param valor
     */
    public boolean pertenece(int valor);

    /**
     * Método para eliminar un valor del conjunto
     * Debe estar inicializado y no vacío
     *
     * Costo: Lineal
     * 
     * 
     * @param valor
     */
    public void sacar(int valor);

    /**
     * Elegir un elemento al azar del conjunto
     * Tiene que estar inicializado y no vacío
     *
     * 
     * Costo: Constante
     * @return int Elemento elegido
     */
    public int elegir();

    /**
     * Saber si el conjunto está vacío
     * Tiene que estar inicializado
     *
     * 
     * Costo: Constante
     * @return boolean True si está vacío, false si no está inicializado
     */
    public boolean estaVacio();
}
