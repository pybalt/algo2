package implementacion;

import tda.PilaTDA;

public class PilaEstatica implements PilaTDA {
    private int[] valores;
    private int cantidad;

    @Override
    public void apilar(int valor) {
        valores[cantidad] = valor;
        cantidad++;
    };

    @Override
    public void desapilar() {
        cantidad--;
    };

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    };

    @Override
    public void inicializar(){
        valores = new int[100];
        cantidad = 0;
    };

    @Override
    public int tope(){
        return valores[cantidad - 1];
    };
}