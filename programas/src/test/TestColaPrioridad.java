package test;

import implementacion.ColaPrioridadEstatica;
import tda.ColaPrioridadTDA;

public class TestColaPrioridad {
    public static void main(String[] args) {
        ColaPrioridadTDA cola = new ColaPrioridadEstatica();
        cola.inicializar();

        cola.acolar(7,8);
        System.out.println(cola.primero());
        cola.acolar(5, 10);
        System.out.println(cola.primero());
        cola.acolar(9,3);
        System.out.println(cola.primero());
        System.out.println("---------------------------------");
        while(!cola.estaVacia()){
            System.out.println(cola.primero());
            cola.desacolar();
        }
    }
}
