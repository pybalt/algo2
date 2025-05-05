package implementacion;

import tda.ConjuntoTDA;

import java.util.Random;

public class ConjuntoEstatico implements ConjuntoTDA {
    private int[] elementos;
    private int cantidad;

    @Override
    public void inicializar() {
        elementos = new int[100];
        cantidad = 0;
    }

    @Override
    public void agregar(int valor) {
        if (!pertenece(valor)) {
            elementos[cantidad] = valor;
            cantidad++;
        }
    }

    @Override
    public boolean pertenece(int valor) {
        boolean encontrado = false;

        for (int i = 0; i < cantidad && !encontrado; i++)
            if (elementos[i] == valor)
                encontrado = true;

        return encontrado;
    }

    @Override
    public void sacar(int valor) {
        boolean encontrado = false;

        for (int i = 0; i < cantidad && !encontrado; i++) {
            if (elementos[i] == valor) {
                encontrado = true;
                elementos[i] = elementos[cantidad - 1];
                cantidad--;
            }
        }
    }

    @Override
    public boolean estaVacio() {
        return cantidad == 0;
    }

    @Override
    public int elegir() {
        Random r = new Random();
        int pos = r.nextInt(cantidad);

        return elementos[pos];
    }
}
