package algoritmos;

import implementacion.PilaEstatica;
import tda.PilaTDA;

public class AlgoritmosPila {
    /**
     * Mostrar Pila
     *
     * @param origen Debe estar inicializado y no vacío para ejecutar
     */
    public void mostrarPila(PilaTDA origen) {
        PilaTDA aux = new PilaEstatica();
        aux.inicializar();

        while (!origen.estaVacia()) {
            System.out.println("Último valor: " + origen.tope());
            aux.apilar(origen.tope());
            origen.desapilar();
        }

        while (!aux.estaVacia()) {
            origen.apilar(aux.tope());
            aux.desapilar();
        }
    }
}
