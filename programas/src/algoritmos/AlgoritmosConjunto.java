package algoritmos;

import implementacion.ConjuntoEstatico;
import tda.ConjuntoTDA;

public class AlgoritmosConjunto {
    /**
     * Definir un algoritmo que realice la intersección
     * de dos conjuntos, preservandolos
     */

    public ConjuntoTDA interseccionConjuntos(ConjuntoTDA c1, ConjuntoTDA c2) {
        ConjuntoTDA aux = new ConjuntoEstatico();
        aux.inicializar();

        ConjuntoTDA resultado = new ConjuntoEstatico();
        resultado.inicializar();

        while (!c1.estaVacio()) {
            int valor = c1.elegir();

            if (c2.pertenece(valor))
                resultado.agregar(valor);

            aux.agregar(valor);
            c1.sacar(valor);
        }

        while (!aux.estaVacio()) {
            int valor = aux.elegir();
            c1.agregar(valor);
            aux.sacar(valor);
        }

        return resultado;
    }
}
