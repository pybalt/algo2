package implementacion;

import tda.ColaTDA;

public class ColaDinamica implements ColaTDA {
    class Nodo {
        int valor;
        Nodo siguiente;
    }

    private Nodo primero;
    private Nodo ultimo; // Fondo de olla

    @Override
    public void inicializar() {
        primero = null;
        ultimo = null;
    }

    @Override
    public void acolar(int valor) {
        Nodo nuevo = new Nodo();
        nuevo.valor = valor;

        if (primero == null) {
            // No hay elementos
            primero = nuevo;
            ultimo = nuevo;
            nuevo.siguiente = null;
        } else {
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
    }

    @Override
    public void desacolar() {
        primero = primero.siguiente;

        if (primero == null)
            ultimo = null; // Si la cola queda vacía, actualizo el último
    }

    @Override
    public int primero() {
        return primero.valor;
    }

    @Override
    public boolean estaVacia() {
        return primero == null;
    }
}
