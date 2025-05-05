package implementacion;

import tda.PilaTDA;

public class PilaDinamica implements PilaTDA {
    class Nodo {
        int valor;
        Nodo siguiente;
    }

    private Nodo tope;

    @Override
    public void inicializar() {
        tope = null;
    }

    @Override
    public void apilar(int valor) {
        Nodo aux = new Nodo();
        aux.valor = valor;
        aux.siguiente = tope;
        tope = aux;
    }

    @Override
    public void desapilar() {
        tope = tope.siguiente;
    }

    @Override
    public int tope() {
        return tope.valor;
    }

    @Override
    public boolean estaVacia() {
        return tope == null;
    }
}
