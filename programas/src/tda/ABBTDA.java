package tda;

public interface ABBTDA {
    void inicializar();
    ABBTDA hijoIzquierdo();
    ABBTDA hijoDerecho();
    int raiz();
    void agregar(int valor);
    void eliminar(int valor);
    boolean estaVacio();
}
