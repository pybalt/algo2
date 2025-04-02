package implementacion;

import tda.ColaPrioridadTDA;

public class ColaPrioridadEstatica implements ColaPrioridadTDA {
    private int[] prioridades;
    private int[] valores;
    private int cantidad;

    /**
     * Inicializa la cola de prioridad
     */
    @Override
    public void inicializar() {
        prioridades = new int[100];
        valores = new int[100];
        cantidad = 0;
    }

    /**
     * Agrega un elemento a la cola con su prioridad
     * Precondición: La cola debe estar inicializada
     * Logica: Debemos usar la prioridad como parametro
     *          para decidir la posicion del elemento.
     *          ------------------------------------
     *          En otras palabras, la cola debe estar siempre
     *          ordenada por prioridad.
     *          ====================================
     * @param valor     Valor a acolar
     * @param prioridad Prioridad del elemento
     */
    @Override
    public void acolar(int valor, int prioridad) {
        int nuevoValor = cantidad;
        for(int i = 0; i<cantidad; i++) {
            if(prioridad > prioridades[i]) {
                nuevoValor = i;
                i = cantidad; // termina el bucle
            }
        }
        for(int i = cantidad - 1; i >= nuevoValor; i--) {
            valores[i+1] = valores[i];
            prioridades[i+1] = prioridades[i];
        }
        valores[nuevoValor] = valor;
        prioridades[nuevoValor] = prioridad;
        cantidad++;
    }

    /**
     * Elimina el elemento de mayor prioridad
     * Precondición: La cola debe estar inicializada y no vacía
     */
    @Override
    public void desacolar() {
        for (int i = 0; i < cantidad - 1; i++) {
            valores[i] = valores[i+1];
            prioridades[i] = prioridades[i+1];
        }
        cantidad--;
    }

    /**
     * Verifica si la cola está vacía
     * Precondición: La cola debe estar inicializada
     *
     * @return true si está vacía, false en caso contrario
     */
    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /**
     * Devuelve el valor del elemento de mayor prioridad
     * Precondición: La cola debe estar inicializada y no vacía
     *
     * @return El valor del primer elemento
     */
    @Override
    public int primero() {
        return valores[0];
    }

    /**
     * Devuelve la prioridad del elemento de mayor prioridad
     * Precondición: La cola debe estar inicializada y no vacía
     *
     * @return La prioridad del primer elemento
     */
    @Override
    public int prioridad() {
        return prioridades[0];
    }
}
