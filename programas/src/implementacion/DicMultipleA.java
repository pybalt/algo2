package implementacion;

import tda.ConjuntoTDA;
import tda.DiccionarioMultipleTDA;

public class DicMultipleA implements DiccionarioMultipleTDA{
    class Elemento {
        int clave;
        int [] valores;
        int cantValores;
    }

    Elemento[] elementos;
    int cantClaves;

    public void inicializarDiccionario(){
        elementos = new Elemento[100];
        cantClaves = 0;
    }

    @Override
    public void agregar(int clave, int valor) {
        int posC = clave2Indice(clave);
        if (posC == -1){
            posC = cantClaves;
            elementos[posC] = new Elemento();
            elementos[posC].clave = clave;
            elementos[posC].cantValores = 0;
            elementos[posC].valores = new int[100];
            cantClaves++;
        }
        Elemento e = elementos[posC];
        int posV = valor2Indice(e, valor);
        if (posV == -1){
            e.valores[e.cantValores] = valor;
            e.cantValores ++;
        }
    }

    @Override
    public void eliminar(int clave) {
        int pos = clave2Indice(clave);
        if (pos != -1){
            elementos[pos] = elementos[cantClaves - 1];
            cantClaves --;
        }
    }

    @Override
    public void eliminarValor(int clave, int valor) {
        int posC = clave2Indice(clave);
        if (posC != -1){
            Elemento e = elementos[posC];
            int posV = valor2Indice(e, valor);
            if (posV!= -1){
                e.valores[posV] = e.valores[e.cantValores-1];
                e.cantValores--;
                if(e.cantValores==0){
                    eliminar(clave);
                }
            }
        }
    }

    @Override
    public ConjuntoTDA recuperar(int clave) {
        ConjuntoTDA c = new ConjuntoLD();
        c.inicializarConjunto();

        int pos = clave2Indice(clave);
        if (pos != -1){
            Elemento e = elementos[pos];
            for(int i=0; i<e.cantValores; i++){
                c.agregar(e.valores[i]);
            }
        }
        return c;
    }

    @Override
    public ConjuntoTDA claves() {
        ConjuntoTDA c = new ConjuntoLD();
        c.inicializarConjunto();

        for(int i=0; i<cantClaves; i++){
            c.agregar(elementos[i].clave);
        }

        return c;
    }

    private int clave2Indice(int clave){
        int i = cantClaves - 1;
        while( i>= 0 && elementos[i].clave != clave){
            i--;
        }
        return i;
    }
    private int valor2Indice(Elemento e, int valor){
        int i = e.cantValores - 1;
        while (i>= 0 && e.valores[i] != valor){
            i--;
        }
        return i;
    }
}