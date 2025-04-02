package test;

import implementacion.PilaEstatica;
import tda.PilaTDA;

public class TestPila {
    public static void main(String[] args) {
        PilaTDA aux = new PilaEstatica();

        aux.inicializar();
        aux.apilar(3);
        aux.apilar(4);
        aux.apilar(5);
        aux.apilar(4);
        aux.apilar(0);

        System.out.println("Ultimo elemento: " + aux.tope());

        System.out.println("Totalidad de elementos");

        while(!aux.estaVacia()){
            System.out.println(aux.tope());
            aux.desapilar();
        }
    }
}
