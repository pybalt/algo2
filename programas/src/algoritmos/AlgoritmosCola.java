package algoritmos;

import implementacion.ColaPrioridadEstatica;
import tda.ColaPrioridadTDA;
import tda.ColaTDA;
import tda.PilaTDA;

public class AlgoritmosCola {
    public void ordenoPila(PilaTDA origen) {
        ColaPrioridadTDA aux = new ColaPrioridadEstatica();
        aux.inicializar();

        while(!origen.estaVacia()) {
            aux.acolar(origen.tope(), origen.tope());
            origen.desapilar();
        }

        while(!aux.estaVacia()) {
            origen.apilar(aux.primero());
            aux.desacolar();
        }
    }

    // ColaRepeticionesTDA?
    /*public void muestroColaRepeticiones(ColaRepeticionesTDA origen) {
        while (!origen.estaVacio()) {
            System.out.println("Valor: " + origen.primero() + " Repeticiones: " + origen.repeticiones());
            origen.desacolar();
        }
    }*/

    public void mostrarCola(ColaTDA origen) {
        while (!origen.estaVacia()) {
            System.out.println("Elemento desacolado: " + origen.primero());
            origen.desacolar();
        }
    }
}
