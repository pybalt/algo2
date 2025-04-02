package test;

import implementacion.PilaEstatica;
import tda.PilaTDA;

import static algoritmos.AlgoritmosPila.mostrarPila;
import static algoritmos.AlgoritmosPila.ordenoPila;

public class TestAlgoritmoPilaCola {
    public static void main(String[] args) {
        PilaTDA origen = new PilaEstatica();
        origen.inicializar();
        origen.apilar(5);
        origen.apilar(10);
        origen.apilar(5);
        origen.apilar(2);
        origen.apilar(200);

        ordenoPila(origen);
        mostrarPila(origen);
    }
}
