package implementacion;

import tda.ConjuntoTDA;
import tda.DiccionarioMultipleTDA;

public class DiccionarioMultipleEstatico implements DiccionarioMultipleTDA {
    class nodo {
        int clave;
        int[] valores;
        int cantidadValores;
    }

    private nodo[] elementos;
    private int cantidadClaves;


    @Override
    public void inicializar() {
        elementos = new nodo[100];
        cantidadClaves = 0;

    }

    @Override
    public void agregar(int clave, int valor) {
        int pos = clave2Pos(clave);
        if (pos==-1) {
            elementos[cantidadClaves] = new nodo();
            elementos[cantidadClaves].clave = clave;
            elementos[cantidadClaves].valores = new int[100];
            elementos[cantidadClaves].valores[0] = valor;
            elementos[cantidadClaves].cantidadValores=1;
            cantidadClaves++;
        }
        else {
            nodo elem = elementos[pos];
            int posValores = valorIndice(elem, valor);
            if (posValores == -1) {
                elem.valores[elem.cantidadValores] = valor;
                elem.cantidadValores++;
            }
        }

    }

    @Override
    public void eliminar(int clave) {
        int pos = clave2Pos(clave);
        if(pos != -1) {
            elementos[pos] = elementos[cantidadClaves - 1];
            cantidadClaves--;
        }

    }

    @Override
    public ConjuntoTDA recuperar(int clave) {
        ConjuntoTDA valores = new ConjuntoEstatico();
        valores.inicializar();
        int pos = clave2Pos(clave);
        if (pos!=-1) {
            nodo elem = elementos[pos];
            for (int i = 0;i<elem.cantidadValores;i++) {
                valores.agregar(elem.valores[i]);
            }
        }

        return valores;

    }

    @Override
    public ConjuntoTDA obtenerClaves() {
        ConjuntoTDA resultado = new ConjuntoEstatico();
        resultado.inicializar();
        for(int i=0; i<cantidadClaves; i++)
            resultado.agregar(elementos[i].clave);
        return resultado;
    }

    @Override
    public void eliminarValor(int clave, int valor) {
        int pos = clave2Pos(clave);
        if (pos!= -1) {
            nodo elem = elementos[pos];
            int posValor = valorIndice(elem, valor);
            if (posValor != -1) {
                int ultimo = elem.cantidadValores;
                elem.valores[posValor]= elem.valores[ultimo-1];
                elem.cantidadValores--;
                if(elem.cantidadValores == 0) {
                    eliminar(clave);
                }
            }
        }

    }

    @Override
    public boolean estaVacio() {
        return cantidadClaves == 0;
    }

    private int clave2Pos(int clave) {
        int resultado = -1;
        boolean encontrado = false;
        for(int i=0; i<cantidadClaves && !encontrado; i++)
            if(elementos[i].clave == clave) {
                resultado = i;
                encontrado = true;
            }
        return resultado;
    }

    private int valorIndice(nodo elem, int valor) {
        int resultado = -1;
        boolean encontrado = false;
        for(int i=0;i<elem.cantidadValores && !encontrado;i++) {
            if (elem.valores[i] == valor) {
                resultado = i;
                encontrado = true;
            }
        }
        return resultado;
    }
}
