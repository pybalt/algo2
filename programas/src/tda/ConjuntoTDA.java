package tda;

public interface ConjuntoTDA {
    /**
     * Agrega un elemento al conjunto
     * Debe estar inicializado
     */
    public void agregar(int x);
    /**
     * Saca un elemento del conjunto
     * Debe estar inicializado
     * No debe estar vacio
     */
    public void sacar(int x);
    /**
     * Elige un elemento del conjunto
     * Debe estar inicializado
     * No debe estar vacio
     */
    public int elegir();
    /**
     * Verifica si un elemento pertenece al conjunto
     * Debe estar inicializado
     * No debe estar vacio
     */
    public boolean pertenece(int x);
    /**
     * Verifica si el conjunto esta vacio
     * Debe estar inicializado
     */
    public boolean conjuntoVacio();
    /**
     * Inicializa el conjunto
     */
    public void inicializarConjunto();
}