package tda;

public interface DiccionarioMultipleTDA {
    public void inicializar();
    /**
     * inicializada
     * */
    public void agregar(int clave, int valor);
    /**
     * inicializada
     * */
    public void eliminar(int clave);
    /**
     * no vacia y existe clave
     * */
    public ConjuntoTDA recuperar(int clave);
    /**
     * inicializada
     * */
    public ConjuntoTDA obtenerClaves();

    /**
     * No vacio clave y valor
     * */
    public void eliminarValor(int clave, int valor);

    /**
     *
     * inicializada
     */
    public boolean estaVacio();
}
