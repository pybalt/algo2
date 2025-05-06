package tda;

public interface DiccionarioMultipleTDA {

    /**
     * Costo: Lineal
     */
    public void inicializar();
    /**
     * inicializada
     * 
     * Costo: Lineal
     * */
    public void agregar(int clave, int valor);
    /**
     * inicializada
     * */
    public void eliminar(int clave);
    /**
     * no vacia y existe clave
     * 
     * Costo: Lineal
     * */
    public ConjuntoTDA recuperar(int clave);
    /**
     * inicializada
     * 
     * Costo: POLINOMICO
     * */
    public ConjuntoTDA obtenerClaves();

    /**
     * No vacio clave y valor
     * 
     * Costo: Lineal
     * */
    public void eliminarValor(int clave, int valor);
}
