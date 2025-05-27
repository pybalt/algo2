package implementacion;
import tda.ABBTDA;

public class AVL implements ABBTDA {
    class Nodo {
        int valor;
        int altura;
        ABBTDA hijoIzquierdo;
        ABBTDA hijoDerecho;
    }
    private Nodo raiz;
    @Override
    public ABBTDA hijoIzquierdo() {
        return raiz.hijoIzquierdo;
    }

    @Override
    public ABBTDA hijoDerecho() {
        return raiz.hijoDerecho;
    }
    private void setHijoDerecho(ABBTDA nodo){
        raiz.hijoDerecho = nodo;
    }
    
    private void setHijoIzquierdo(ABBTDA nodo){
        raiz.hijoIzquierdo = nodo;
    }
    @Override
    public void inicializar() {
        raiz = null;
    }

    @Override
    public boolean estaVacio() {
        return raiz == null;
    }

    @Override
    public int raiz() {
        return raiz.valor;
    }

    @Override
    public void agregar(int valor) {
        // Implementación pendiente
    }

    @Override
    public void eliminar(int valor) {
        // Implementación pendiente
    }

    /**
     * Rotación a la izquierda:
     *       z               y
     *      / \             / \
     *    T1   y     =>    z   x
     *        / \         / \
     *       T2  x       T1  T2
     * 
     * Como no podemos reasignar punteros, creamos nuevos árboles
     * y copiamos los valores reorganizando la estructura
     */
    private void rotarIzquierda(){
        if (estaVacio() || hijoDerecho().estaVacio()) {
            return;
        }
        
        // Guardamos los valores y subárboles actuales
        int valorZ = raiz();
        int valorY = hijoDerecho().raiz();
        ABBTDA T1 = hijoIzquierdo();
        ABBTDA T2 = hijoDerecho().hijoIzquierdo();
        ABBTDA x = hijoDerecho().hijoDerecho();
        
        // Reorganizamos: y se convierte en la nueva raíz
        raiz.valor = valorY;
        
        // Creamos nuevo subárbol izquierdo (z con T1 y T2)
        AVL nuevoZ = new AVL();
        nuevoZ.inicializar();
        nuevoZ.raiz = new Nodo();
        nuevoZ.raiz.valor = valorZ;
        nuevoZ.raiz.hijoIzquierdo = T1;
        nuevoZ.raiz.hijoDerecho = T2;
        
        // Asignamos los nuevos hijos
        raiz.hijoIzquierdo = nuevoZ;
        raiz.hijoDerecho = x;
    }

    /**
     *    Rotación a la derecha:
     *        z              y
     *       / \            / \
     *      y   T4   =>    x   z
     *     / \                / \
     *    x   T3            T3  T4
     * 
     * Como no podemos reasignar punteros, creamos nuevos árboles
     * y copiamos los valores reorganizando la estructura
     */
    private void rotarDerecha(){
        if (estaVacio() || hijoIzquierdo().estaVacio()) {
            return;
        }
        // Guardamos los valores y subÁrboles actuales
        int valorZ = raiz();
        int valorY = hijoIzquierdo().raiz();

        ABBTDA x = hijoIzquierdo().hijoIzquierdo();
        ABBTDA T3 = hijoIzquierdo().hijoDerecho();
        ABBTDA T4 = hijoDerecho();

        // Creamos z con T3 y T4
        raiz.valor = valorY;
        AVL nuevoZ = new AVL();
        nuevoZ.inicializar();
        nuevoZ.raiz.valor = valorZ;
        nuevoZ.raiz.hijoDerecho = T4;
        nuevoZ.raiz.hijoIzquierdo = T3;
        
        // Asignamos nuevos hijos
        raiz.hijoIzquierdo = x;
        raiz.hijoDerecho = nuevoZ;
    }
    private int balance(Nodo nodo){
        return 0;
    }
    private int altura(Nodo nodo){
        return 0;
    }
}
