package implementacion;

import tda.ColaTDA;

// FIFO: first in, first out
// LILO: last in, last out

public class ColaEstatica implements ColaTDA {
    private int[] elementos;
    private int cantidad;

    public void inicializar() {
        elementos = new int[100];
        cantidad = 0;
    }

    public void acolar(int valor) {
        elementos[cantidad] = valor;
        cantidad++;
    }

    public void desacolar() {
        for (int i = 0; i < cantidad; i++) {
            elementos[i] = elementos[i + 1];
        }

        cantidad--;
    }

    public int primero() {
        return elementos[0];
    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }
}
