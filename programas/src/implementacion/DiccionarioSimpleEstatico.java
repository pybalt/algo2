package implementacion;

import tda.ConjuntoTDA;
import tda.DiccionarioSimpleTDA;

public class DiccionarioSimpleEstatico implements DiccionarioSimpleTDA {
    class nodo {
        int key;
        int value;
    }

    private nodo[] elementos;
    private int cantidad;

    @Override
    public void inicializar() {
        elementos = new nodo[100];
        cantidad = 0;
    }

    @Override
    public void agregar(int key, int value) {
        int pos = recuperarIndice(key);

        if (pos == -1) {
            // Creo un nuevo nodo
            nodo aux = new nodo();
            aux.key = key;
            aux.value = value;

            // Lo agrego al final de elementos
            elementos[cantidad] = aux;
            cantidad++;
        } else {
            // Si ya existe la clave, actualizo el valor
            elementos[pos].value = value;
        }
    }

    @Override
    public void eliminar(int key) {
        int pos = recuperarIndice(key);

        // Si no es -1 entonces existe la clave
        if (pos != -1) {
            elementos[pos] = elementos[cantidad - 1];
            cantidad--;
        }
    }

    @Override
    public int recuperar(int key) {
        int pos = recuperarIndice(key);

        if (pos != -1)
            return elementos[pos].value;

        return 0; // Solo se ejecuta si nunca devolvemos un elemento arriba, tuki!!
    }

    public ConjuntoTDA obtenerClaves() {
        ConjuntoTDA resultado = new ConjuntoEstatico();
        resultado.inicializar();

        for (int i = 0; i < cantidad; i++) {
            resultado.agregar(elementos[i].key);
        }

        return resultado;
    }

    /**
     * Método de Servicio para obtener el índice de una clave
     */
    private int recuperarIndice(int key) {
        int indice = -1;

        for (int i = 0; i < cantidad; i++)
            if (elementos[i].key == key) {
                // Tuki, la encontré
                indice = i;

                // Salimos a lo bestia del for, una demenciaaaa...
                i = cantidad;
            }

        return indice;
    }
}
