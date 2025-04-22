package tda;

public interface DiccionarioMultipleTDA {
    /**
     * Initialize the dictionary
     * @pre None
     * @post Dictionary is initialized and empty
     */
    void inicializarDiccionario();

    /**
     * Add a key-value pair to the dictionary
     * @param clave key to add
     * @param valor value to add
     * @pre Dictionary must be initialized
     * @post The value is added to the set associated with the key
     */
    void agregar(int clave, int valor);

    /**
     * Remove a key and all its associated values
     * @param clave key to remove
     * @pre Dictionary must be initialized and key must exist
     * @post Key and all its values are removed
     */
    void eliminar(int clave);

    /**
     * Remove a specific value associated with a key
     * @param clave key to remove value from
     * @param valor value to remove
     * @pre Dictionary must be initialized, key must exist and value must exist for that key
     * @post The value is removed from the set associated with the key
     */
    void eliminarValor(int clave, int valor);

    /**
     * Get all values associated with a key
     * @param clave key to retrieve values for
     * @return Set of values associated with the key
     * @pre Dictionary must be initialized and key must exist
     */
    ConjuntoTDA recuperar(int clave);

    /**
     * Get all keys in the dictionary
     * @return Set of all keys
     * @pre Dictionary must be initialized
     */
    ConjuntoTDA claves();
}
