package implementacion;

import tda.ColaTDA;

/**
 * Primera implementacion
 */
public class ColaEstatica implements ColaTDA {

    private int[] valores;
    private int cantidad;
    @Override
    public void acolar(int valor) {
        valores [cantidad] = valor;
        cantidad ++;
    }

    @Override
    public void desacolar() {

        for (int actual = 0; actual < cantidad; actual ++) {
            valores[actual] = valores[actual + 1];
        }
        cantidad --;

    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    @Override
    public int primero() {
        return valores[0];
    }

    @Override
    public void inicializar() {
        valores = new int[100];
        cantidad = 0;
    }
}
