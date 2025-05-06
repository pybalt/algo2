package tda;

public interface DiccionarioSimpleTDA {
    /**
     * Este método inicializa la estructura
     * 
     * Costo: Constante
     */
    public void inicializar();

    /**
     * Método para agregar un elemento al diccionario
     * Debe estar inicializado
     *
     * Costo: Lineal
     * 
     * @param key
     * @param value
     */
    public void agregar(int key, int value);

    /**
     * Método para eliminar un elemento del diccionario
     * Debe estar inicializado
     *
     * Costo: Lineal
     * @param key
     */
    public void eliminar(int key);

    /**
     * Método para recuperar un elemento del diccionario
     * El diccionario no debe estar vacío y debe existir la clave
     *
     * Costo: Lineal
     * @param key
     */
    public int recuperar(int key);

    /**
     * Método para obtener todas las claves del diccionario
     * Debe estar inicializado
     * 
     * Costo: Lineal
     */
    public ConjuntoTDA obtenerClaves();
}
