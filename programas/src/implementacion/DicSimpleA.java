package implementacion;

import tda.DiccionarioSimpleTDA;
import tda.ConjuntoTDA;

public class DicSimpleA implements DiccionarioSimpleTDA{
    class NodoClave{
        int clave;
        int valor;
        NodoClave sigClave;
    }
    class Elemento { 
        int clave;
        int valor;
    }

    Elemento[] elementos;
    NodoClave origen;
    int cant;

    public void InicializarDiccionario(){
        cant = 0;
        elementos = new Elemento[100];
    }
    
    public void Agregar(int clave, int valor){
        int pos = Clave2Indice(clave);
        if(pos==-1){
            pos = cant;
            elementos[pos] = new Elemento();
            elementos[pos].clave = clave;
            cant++;
        }
        elementos[pos].valor = valor;
    }
    /**
     * Devuelve -1 si no encuentra la clave
     * @param clave
     * @return
     */
    private int Clave2Indice(int clave){
        int i = cant - 1;
        while(i >= 0 && elementos[i].clave != clave){
            i--;
        }
        return i;
    }

    public void Eliminar(int clave){
        int pos = Clave2Indice(clave);
        if(pos != -1){
            elementos[pos] = elementos[cant - 1];
            cant--;
        }
    }

    public int Recuperar(int clave){
        int pos = Clave2Indice(clave);
        return elementos[pos].valor;
    }

    public ConjuntoTDA Claves(){
        ConjuntoTDA c = new ConjuntoLD();
        c.inicializarConjunto();

        for(int i=0; i<cant; i++){
            c.agregar(elementos[i].clave);
        }
        return c;
    }
}
