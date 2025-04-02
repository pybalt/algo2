package algoritmos;

import implementacion.ColaPrioridadEstatica;
import implementacion.PilaEstatica;
import tda.ColaPrioridadTDA;
import tda.PilaTDA;

public class AlgoritmosPila {
    public static void main(String[] args) {

    }
    public static void ordenoPila(PilaTDA origen) {
        ColaPrioridadTDA aux = new ColaPrioridadEstatica();
        aux.inicializar();

        while(!origen.estaVacia()){
            aux.acolar(origen.tope(), origen.tope());
            origen.desapilar();
        }
        while(!aux.estaVacia()){
            origen.apilar(aux.primero());
            aux.desacolar();
        }

    }
    public static void mostrarPila(PilaTDA origen) {
        PilaTDA aux = new PilaEstatica();
        aux.inicializar();

        while(!origen.estaVacia()){
            System.out.println(origen.tope());
            aux.apilar(origen.tope());
            origen.desapilar();
        }
        while(!aux.estaVacia()){
            origen.apilar(aux.tope());
            aux.desapilar();
        }
    }
}
