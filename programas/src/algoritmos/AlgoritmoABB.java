package algoritmos;

import implementacion.ABB;
import tda.ABBTDA;

public class AlgoritmoABB {
    
    public static void visualizar(ABBTDA arbol) {
        if (arbol.estaVacio()) {
            System.out.println("El arbol esta vacio");
            return;
        }
        
        System.out.println("Recorrido Inorden (izquierda-raiz-derecha):");
        inorden(arbol);
        System.out.println();
        
        System.out.println("Recorrido Preorden (raiz-izquierda-derecha):");
        preorden(arbol);
        System.out.println();
        
        System.out.println("Recorrido Postorden (izquierda-derecha-raiz):");
        postorden(arbol);
        System.out.println();
        
        System.out.println("Estructura del arbol:");
        mostrarEstructura(arbol, "", true);
    }
    
    private static void inorden(ABBTDA arbol) {
        if (!arbol.estaVacio()) {
            inorden((ABB) arbol.hijoIzquierdo());
            System.out.print(arbol.raiz() + " ");
            inorden((ABB) arbol.hijoDerecho());
        }
    }
    
    private static void preorden(ABBTDA arbol) {
        if (!arbol.estaVacio()) {
            System.out.print(arbol.raiz() + " ");
            preorden((ABB) arbol.hijoIzquierdo());
            preorden((ABB) arbol.hijoDerecho());
        }
    }
    
    private static void postorden(ABBTDA arbol) {
        if (!arbol.estaVacio()) {
            postorden((ABB) arbol.hijoIzquierdo());
            postorden((ABB) arbol.hijoDerecho());
            System.out.print(arbol.raiz() + " ");
        }
    }
    
    private static void mostrarEstructura(ABBTDA arbol, String prefijo, boolean esUltimo) {
        if (!arbol.estaVacio()) {
            System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + arbol.raiz());
            
            ABB hijoIzq = (ABB) arbol.hijoIzquierdo();
            ABB hijoDer = (ABB) arbol.hijoDerecho();
            
            boolean tieneHijoIzq = !hijoIzq.estaVacio();
            boolean tieneHijoDer = !hijoDer.estaVacio();
            
            if (tieneHijoIzq || tieneHijoDer) {
                if (tieneHijoIzq) {
                    mostrarEstructura(hijoIzq, prefijo + (esUltimo ? "    " : "│   "), !tieneHijoDer);
                }
                if (tieneHijoDer) {
                    mostrarEstructura(hijoDer, prefijo + (esUltimo ? "    " : "│   "), true);
                }
            }
        }
    }

    public int contar(ABBTDA arbol){
        if(arbol.estaVacio()){
            return 0;
        }else{
            return 1 + contar(arbol.hijoDerecho()) + contar(arbol.hijoIzquierdo());
        }
    }
}
