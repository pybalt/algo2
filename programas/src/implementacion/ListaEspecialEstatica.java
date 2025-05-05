package implementacion;

import tda.ListaEspecialTDA;

public class ListaEspecialEstatica implements ListaEspecialTDA {
    private int[] lista;
    private int acumulador;
    private int cantidad;

    @Override
    public void inicializar() {
        lista = new int[100];
        acumulador = 0;
        cantidad = 0;
    }

    @Override
    public void agregar(int value) {
        // Primer elemento?
        if (cantidad == 0)
            lista[0] = value;
        else {
            // Promedio
            int promedio = acumulador / cantidad;
            if (value > promedio) {
                // Si el nuevo valor es mayor al promedio, lo agrego al final
                lista[cantidad] = value;
            } else {
                // Si el nuevo valor es menor o igual al promedio, lo agrego al principio
                for (int i = cantidad; i > 0; i--) {
                    lista[i] = lista[i - 1];
                }
                lista[0] = value;
            }
        }

        acumulador += value;
        cantidad++;
    }

    @Override
    public void eliminar(int value) {
        for (int i = 0; i < cantidad; i++) {
            lista[i] = lista[i + 1];
        }

        acumulador -= value;
        cantidad--;
    }

    @Override
    public int primero() {
        return lista[0];
    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }
}
