package implementacion;

import tda.ConjuntoTDA;

public class ConjuntoTA implements ConjuntoTDA {
    int[] elementos;
    int tamaño;
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
        int randomIndex = (int)(Math.random() * tamaño);
        return elementos[randomIndex];
    }
    public boolean pertenece(int x){
        int i = 0;
        while(i < tamaño && elementos[i] != x){
            i++;
        }
        return i < tamaño;
    }
    public boolean conjuntoVacio(){
        return tamaño == 0;
    }
    public void inicializarConjunto(){
        elementos = new int[100];
        tamaño = 0;
    }
}
