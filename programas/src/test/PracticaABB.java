/*
 * Obtener el valor del menor elemento de un ABB. ✅
 * Calcular la cantidad de elementos que contiene un ABB. ✅
 * Calcular la suma de los elementos que contiene un ABB. ✅
 * Calcular el cantidad de hojas de un ABB ✅
 * Calcular la altura de un ABB. ✅
 * Comprobar si dos ABBs tienen la misma forma.
 * Comprobar si dos ABBs son iguales.
 * Contar la cantidad de elementos que están en un cierto nivel N.
 * Dado un valor k, arme un conjunto con todos los elementos del ABB que son mayores que k.
 * Dado un elemento de valor v (que está presente en el ABB), obtener el elemento del árbol que es inmediatamente anterior (en valor).
 * Dado un ABB obtener el conjunto de los valores que son nodos intermedios.
 */
package test;
import tda.ABBTDA;

public class PracticaABB {
    public static void main(String[] args) {
        implementacion.ABB arbol = new implementacion.ABB();
        arbol.inicializar();
        arbol.agregar(10);
        arbol.agregar(7);
        arbol.agregar(15);
        arbol.agregar(6);
        arbol.agregar(8);
        arbol.agregar(11);
        arbol.agregar(16);
        arbol.agregar(5);
        arbol.agregar(7);
        arbol.agregar(9);
        arbol.agregar(4);
        int cantidadElementos = contarElementos(arbol);
        int sumaElementos = sumaElementos(arbol);
        int qtyHojas = cantidadHojas(arbol);
        int alturaABB = calculaAltura(arbol);
        System.out.println(cantidadElementos);
        System.out.println(sumaElementos);
        System.out.println(qtyHojas);
        System.out.println(alturaABB);
    }
    ABBTDA menorElemento(ABBTDA arbol){
        if(arbol.hijoIzquierdo().estaVacio()){
            return arbol;
        }else{
            return menorElemento(arbol.hijoIzquierdo());
        }
    }
    static int contarElementos(ABBTDA arbol){
        if(arbol.estaVacio()){
            return 0;
        }else{
            return 1 + contarElementos(arbol.hijoIzquierdo()) + contarElementos(arbol.hijoDerecho());
        }
    }
    static int sumaElementos(ABBTDA arbol){
        if(arbol.estaVacio()){
            return 0;
        }else{
            return arbol.raiz() + sumaElementos(arbol.hijoDerecho()) + sumaElementos(arbol.hijoIzquierdo());
        }
    }
    static int cantidadHojas(ABBTDA arbol){
        if(arbol.estaVacio()){
            return 0;
        }
        else if(arbol.hijoDerecho().estaVacio() && arbol.hijoIzquierdo().estaVacio()){
            return 1;
        }else{
            return cantidadHojas(arbol.hijoIzquierdo()) + cantidadHojas(arbol.hijoDerecho());
        }
    }
    static int calculaAltura(ABBTDA arbol){
        if(arbol.estaVacio()){
            return -1;
        }else if (
            arbol.hijoDerecho().estaVacio() 
            && arbol.hijoIzquierdo().estaVacio()
            ){
            return 1;
        }else{
            return 1 + max(
                calculaAltura(arbol.hijoDerecho()),
                calculaAltura(arbol.hijoIzquierdo())
                );
        }
    }
    static int max(int a, int b){
        if(a > b){
            return a;
        }else{
            return b;
        }
    }
}
