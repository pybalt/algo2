package tda;

public interface DiccionarioSimpleTDA {
    
    /**
     * Inicializa el diccionario
     */
    void InicializarDiccionario();
    /**
     * Requiere inicializar
     * @param clave
     * @param valor
     */
    void Agregar(int clave, int valor);
    /**
     * Requiere inicializar
     * @param int
     */
    void Eliminar(int clave);
    /**
     * Requiere inicializar y que la clave exista
     * @param clave
     * @return
     */
    int Recuperar(int clave);
    /**
     * Requiere inicializar
     * @return
     */
    ConjuntoTDA Claves();

}
