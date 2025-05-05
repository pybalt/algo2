package implementacion;

import tda.ColaPrioridadTDA;

public class ColaPrioridadEstatica implements ColaPrioridadTDA {
    private int[] elementos;
    private int[] prioridades;
    private int cantidad;

    public void inicializar() {
        elementos = new int[100];
        prioridades = new int[100];
        cantidad = 0;
    }

    @Override
    public void acolar(int valor, int prioridad) {
        int nuevaPosicion = cantidad;

        for (int i = 0; i < cantidad; i++) {
            if (prioridad > prioridades[i]) {
                nuevaPosicion = i;
                i = cantidad; // Finalizo el ciclo
            }
        }

        /**
         * Recorro a la inversa
         * para no pisar los valores
         */
        for (int i  = cantidad - 1; i >= nuevaPosicion; i--) {
            elementos[i + 1] = elementos[i];
            prioridades[i + 1] = prioridades[i];
        }

        elementos[nuevaPosicion] = valor;
        prioridades[nuevaPosicion] = prioridad;
        cantidad++;
    }

    @Override
    public void desacolar() {
        for (int i = 0; i < cantidad; i++) {
            elementos[i] = elementos[i + 1];
            prioridades[i] = prioridades[i + 1];
        }

        cantidad--;
    }

    @Override
    public int primero() {
        return elementos[0];
    }

    @Override
    public int prioridad() {
        return prioridades[0];
    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }
}
