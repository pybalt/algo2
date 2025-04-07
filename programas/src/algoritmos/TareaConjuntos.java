/**
Dado un conjunto no vacio devolver un conjunto nuevo que tenga la siguiente particularidad:

Seleccione valores aleatorios dentro del conjunto original, haga la suma de esos elementos hasta que encuentre un valor menor a esos elementos seleccionados. 

Por ejemplo:
Supongase que tiene el siguiente conjunto C: {13, 4, 6, 17, 25 }, 
En la primera iteracion el elegir devuelve el valor 6, por lo tanto la suma va a ser 6, en la siguiente iteracion el elegir devuelve 13, como el 13 es mayor a 6 lo suma y queda en 19, en la siguiente iteracion el elegir me devuelve 4, como 4 es menor al ultimo elemento elegido, se corta la sumatoria.
El valor de la sumatoria (19)  pasa a ser elemento nuevo en el conjunto Resultado. 
Repetir este proceso tantas veces como elementos tenga el conjunto original (en este ejemplo 5 veces)
Mantener el conjunto original al finalizar el proceso. 
*/

package algoritmos;

import tda.ConjuntoTDA;
import implementacion.ConjuntoTA;

public class TareaConjuntos{

    public static void main(String[] args){
        ConjuntoTDA conjunto = new ConjuntoTA();
        conjunto.inicializarConjunto();
        conjunto.agregar(13);
        conjunto.agregar(4);
        conjunto.agregar(6);
        conjunto.agregar(17);
        conjunto.agregar(25);

        ConjuntoTDA temp = new ConjuntoTA();
        temp.inicializarConjunto();
        int size = 0;
        while(!conjunto.conjuntoVacio()) {
            int elem = conjunto.elegir();
            temp.agregar(elem);
            conjunto.sacar(elem);
            size++;
        }

        while(!temp.conjuntoVacio()) {
            int elem = temp.elegir();
            conjunto.agregar(elem);
            temp.sacar(elem);
        }

        ConjuntoTDA resultado = new ConjuntoTA();
        resultado.inicializarConjunto();

        for(int i = 0; i < size; i++) {
            int suma = 0;
            int elementoAnterior = 0;
            boolean continuarSumando = true;
            
            int numeroElegido = conjunto.elegir();
            suma = numeroElegido;
            elementoAnterior = numeroElegido;
            
            while(continuarSumando) {
                numeroElegido = conjunto.elegir();
                if(numeroElegido < elementoAnterior) {
                    continuarSumando = false;
                } else {
                    suma += numeroElegido;
                    elementoAnterior = numeroElegido;
                }
            }

            resultado.agregar(suma);
        }

        System.out.println("Conjunto resultado:");
        imprimeConjunto(resultado);
        
        System.out.println("Conjunto original:");
        imprimeConjunto(conjunto);
    }
    public static void imprimeConjunto(ConjuntoTDA set) {
        ConjuntoTDA temp = new ConjuntoTA();
        temp.inicializarConjunto();
        System.out.print("{");
        while (!set.conjuntoVacio()) {
            int element = set.elegir();
            System.out.print(element);
            temp.agregar(element);
            set.sacar(element);
            if (!set.conjuntoVacio()) {
                System.out.print(", ");
            }
        }
        while (!temp.conjuntoVacio()) {
            int element = temp.elegir();
            set.agregar(element);
            temp.sacar(element);
        }
        System.out.println("}");
    }
}