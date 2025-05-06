package algoritmos;

import implementacion.ColaPrioridadEstatica;
import tda.ColaPrioridadTDA;



public class AlgoritmosCola {
    public static void main(String[] args) {
        ColaPrioridadTDA colaPrioritaria = new ColaPrioridadEstatica();
        colaPrioritaria.inicializar();

        colaPrioritaria.acolar(4, 2);
        colaPrioritaria.acolar(2, 2);
        colaPrioritaria.acolar(2, 3);
        colaPrioritaria.acolar(8, 10);
        colaPrioritaria.acolar(22, 4);

        ColaPrioridadTDA colaSecundaria = new ColaPrioridadEstatica();
        colaSecundaria.inicializar();

        colaSecundaria.acolar(3, 2);
        colaSecundaria.acolar(3, 2);
        colaSecundaria.acolar(6, 3);
        colaSecundaria.acolar(9, 10);
        colaSecundaria.acolar(99, 4);

        ColaPrioridadTDA colaCombinada = combinarColas(colaPrioritaria, colaSecundaria);
        mostrarCola(colaCombinada);
    }
    public static ColaPrioridadTDA combinarColas(ColaPrioridadTDA colaPrioritaria, ColaPrioridadTDA colaSecundaria){
        ColaPrioridadTDA resultado = new ColaPrioridadEstatica();
        resultado.inicializar();

        ColaPrioridadTDA copiaPrioritaria = new ColaPrioridadEstatica();
        copiaPrioritaria.inicializar();
        ColaPrioridadTDA copiaSecundaria = new ColaPrioridadEstatica();
        copiaSecundaria.inicializar();

        copiarCola(colaPrioritaria, copiaPrioritaria);
        copiarCola(colaSecundaria, copiaSecundaria);


        while(!colaPrioritaria.estaVacia() && !colaSecundaria.estaVacia()) {
            int prioridadP = copiaPrioritaria.prioridad();
            int prioridadS = copiaSecundaria.prioridad();

            if(prioridadP <= prioridadS){
                resultado.acolar(copiaPrioritaria.primero(), prioridadP);
                copiaPrioritaria.desacolar();
            } else {
                resultado.acolar(copiaSecundaria.primero(), prioridadS);
                copiaSecundaria.desacolar();
            }
        }

        // restantes de la cola prioritaria
        while(!copiaPrioritaria.estaVacia()){
            resultado.acolar(copiaPrioritaria.primero(), copiaPrioritaria.prioridad());
            copiaPrioritaria.desacolar();
        }

        // restantes de la cola secundaria
        while(!copiaSecundaria.estaVacia()){
            resultado.acolar(copiaSecundaria.primero(), copiaSecundaria.prioridad());
            copiaSecundaria.desacolar();
        }

        return resultado;
    }
    public static void mostrarCola(ColaPrioridadTDA cola){
        while(!cola.estaVacia()){
            System.out.println(cola.primero());
            cola.desacolar();


        }
    }

    public static void copiarCola(ColaPrioridadTDA cola, ColaPrioridadTDA copia){
        while(!cola.estaVacia()){
            copia.acolar(cola.primero(), cola.prioridad());
            cola.desacolar();
        }
    }
}