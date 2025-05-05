package implementacion;

import tda.ConjuntoTDA;

public class ConjuntoDinamico implements ConjuntoTDA {
    private class Nodo {
        int valor;
        Nodo siguiente;
    }

    private Nodo inicio;

    @Override
    public void inicializar() {
        inicio = null;
    }

    @Override
    public void agregar(int x) {
        if (!pertenece(x)) {
            Nodo nuevo = new Nodo();
            nuevo.valor = x;
            nuevo.siguiente = inicio;
            inicio = nuevo;
        }
    }

    @Override
    public void sacar(int x) {
        if (inicio != null) {
            if (inicio.valor == x) {
                inicio = inicio.siguiente;
            } else {
                Nodo actual = inicio;
                while (actual.siguiente != null && actual.siguiente.valor != x) {
                    actual = actual.siguiente;
                }
                if (actual.siguiente != null) {
                    actual.siguiente = actual.siguiente.siguiente;
                }
            }
        }
    }

    @Override
    public boolean pertenece(int x) {
        Nodo actual = inicio;
        while (actual != null) {
            if (actual.valor == x) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    @Override
    public boolean estaVacio() {
        return inicio == null;
    }

    @Override
    public int elegir() {
        return inicio.valor;
    }
}
