package implementacion;

import tda.ConjuntoTDA;

public class ConjuntoTA implements ConjuntoTDA {
    private int[] elementos;
    private int tamaño;
    public void agregar(int x){
        if(!pertenece(x)){
            elementos[tamaño] = x;
            tamaño++;
        }
    }
    public void sacar(int x){
        int i = 0;
        while(i < tamaño && elementos[i] != x){
            i++;
        }
        if(i < tamaño){
            elementos[i] = elementos[tamaño - 1];
            tamaño--;
        }
    }
    public int elegir(){
        return elementos[tamaño - 1];
    }
    public boolean pertenece(int x){
        int i = 0;
        while(i < tamaño && elementos[i] != x){
            i++;
        }
        return i < tamaño;
    }
    public int conjuntoVacio(){
        return tamaño == 0;
    }
    public void inicializarConjunto(){
        elementos = new int[100];
        tamaño = 0;
    }
}
