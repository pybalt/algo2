package algoritmos;

import tda.ConjuntoTDA;
import tda.DiccionarioMultipleTDA;
import implementacion.DicMultipleA;
import implementacion.ConjuntoLD;

public class AlgoritmosDiccionarioMultiple {

    /**
     * Generates a new dictionary where keys are values present in multiple keys of the original dictionary,
     * and values are the original keys containing those values.
     * @param originalDict The input dictionary.
     * @return A new dictionary with inverted mapping for repeated values.
     */
    public static DiccionarioMultipleTDA invertirDiccionarioConRepetidos(DiccionarioMultipleTDA originalDict) {
        DiccionarioMultipleTDA tempDict = new DicMultipleA();
        tempDict.inicializarDiccionario();

        DiccionarioMultipleTDA resultado = new DicMultipleA();
        resultado.inicializarDiccionario();

        ConjuntoTDA clavesOriginales = originalDict.claves();

        while (!clavesOriginales.conjuntoVacio()) {
            int claveOriginal = clavesOriginales.elegir();
            clavesOriginales.sacar(claveOriginal);

            ConjuntoTDA valores = originalDict.recuperar(claveOriginal);
            ConjuntoTDA valoresCopia = copiarConjunto(valores);

            while (!valoresCopia.conjuntoVacio()) {
                int valor = valoresCopia.elegir();
                valoresCopia.sacar(valor);
                tempDict.agregar(valor, claveOriginal);
            }
        }

        ConjuntoTDA clavesTemp = tempDict.claves();

        while (!clavesTemp.conjuntoVacio()) {
            int valorKey = clavesTemp.elegir();
            clavesTemp.sacar(valorKey);

            ConjuntoTDA conjuntoClavesOriginales = tempDict.recuperar(valorKey);

            if (conjuntoTieneMasDeUnElemento(conjuntoClavesOriginales)) {
                ConjuntoTDA conjuntoClavesOriginalesCopia = copiarConjunto(conjuntoClavesOriginales);
                 while (!conjuntoClavesOriginalesCopia.conjuntoVacio()) {
                    int claveOriginal = conjuntoClavesOriginalesCopia.elegir();
                    conjuntoClavesOriginalesCopia.sacar(claveOriginal);
                    resultado.agregar(valorKey, claveOriginal);
                }
            }
        }

        return resultado;
    }

    private static ConjuntoTDA copiarConjunto(ConjuntoTDA original) {
        ConjuntoTDA copia = new ConjuntoLD();
        copia.inicializarConjunto();
        ConjuntoTDA aux = new ConjuntoLD();
        aux.inicializarConjunto();

        while (!original.conjuntoVacio()) {
            int x = original.elegir();
            aux.agregar(x);
            original.sacar(x);
        }

        while (!aux.conjuntoVacio()) {
            int x = aux.elegir();
            original.agregar(x);
            copia.agregar(x);
            aux.sacar(x);
        }
        return copia;
    }

    private static boolean conjuntoTieneMasDeUnElemento(ConjuntoTDA conjunto) {
        ConjuntoTDA copia = copiarConjunto(conjunto);
        if (copia.conjuntoVacio()) {
            return false;
        }
        int primerElemento = copia.elegir();
        copia.sacar(primerElemento);
        return !copia.conjuntoVacio();
    }
} 