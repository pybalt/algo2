package implementacion;
import tda.PilaTDA;

public class PilaEstatica implements PilaTDA {
    private int[] elementos;
    private int indice;

    @Override
    public void inicializar() {
        elementos = new int[100];
        indice = 0;
    }

    @Override
    public void apilar(int valor) {
        elementos[indice] = valor;
        indice++;
    }

    @Override
    public void desapilar() {
        indice--;
    }

    @Override
    public int tope() {
        return elementos[indice - 1];
    }

    @Override
    public boolean estaVacia() {
        return indice == 0;
    }
}
