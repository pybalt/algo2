package algoritmos;

import tda.ConjuntoTDA;
import tda.DiccionarioSimpleTDA;
import implementacion.ConjuntoEstatico; // Using linked list set implementation
import implementacion.DiccionarioSimpleEstatico; // Using array simple dictionary implementation

public class AlgoritmosDiccionarioSimple {

    /**
     * Checks if the string representation of sub is a substring of the string representation of num.
     * This matches the sub-number definition implied by the example (e.g., 56 in 564).
     */
    private static boolean esSubnumero(int sub, int num) {
        String subStr = String.valueOf(sub);
        String numStr = String.valueOf(num);
        return numStr.contains(subStr);
    }

    /**
     * Helper function to copy a ConjuntoTDA.
     * Required because standard iteration methods (elegir/recuperar) modify the set.
     * @param origen The source set.
     * @param destino The destination set (must be initialized).
     */
    private static void copiarConjunto(ConjuntoTDA origen, ConjuntoTDA destino) {
        ConjuntoTDA aux = new ConjuntoEstatico();
        aux.inicializar();

        // Transfer elements from origen to aux
        while (!origen.estaVacio()) {
            int x = origen.elegir();
            aux.agregar(x);
            origen.sacar(x);
        }

        // Transfer elements back to origen and also to destino
        while (!aux.estaVacio()) {
            int x = aux.elegir();
            origen.agregar(x);
            destino.agregar(x);
            aux.sacar(x);
        }
    }

    /**
     * Develop an algorithm that receives a simple dictionary and returns a new dictionary
     * containing only those keys and values where the value of one key appears within
     * the value of another key as a subnumber.
     *
     * For example:
     * input={1: 23, 3: 56, 4: 564}
     * output={3: 56, 4: 564}
     *
     * @param dicIn The input dictionary.
     * @return A new dictionary containing only the elements that satisfy the sub-number condition.
     */
    public static DiccionarioSimpleTDA filtrarPorSubnumero(DiccionarioSimpleTDA dicIn) {
        DiccionarioSimpleTDA dicOut = new DiccionarioSimpleEstatico();
        dicOut.inicializar();

        ConjuntoTDA claves = dicIn.obtenerClaves();
        ConjuntoTDA clavesResultantes = new ConjuntoEstatico(); // Set to store keys to include in the output
        clavesResultantes.inicializar();

        ConjuntoTDA clavesCopia1 = new ConjuntoEstatico(); // Create a copy for the outer loop iteration
        clavesCopia1.inicializar();
        copiarConjunto(claves, clavesCopia1);

        ConjuntoTDA clavesOriginal = new ConjuntoEstatico(); // Keep a stable copy of all keys
        clavesOriginal.inicializar();
        copiarConjunto(clavesCopia1, clavesOriginal); // clavesCopia1 now holds the keys again


        // Iterate through each key (key1) and its value (value1)
        while (!clavesCopia1.estaVacio()) {
            int key1 = clavesCopia1.elegir();
            clavesCopia1.sacar(key1);
            int value1 = dicIn.recuperar(key1);

            ConjuntoTDA clavesCopia2 = new ConjuntoEstatico(); // Create a copy for the inner loop iteration
            clavesCopia2.inicializar();
            copiarConjunto(clavesOriginal, clavesCopia2); // Use the stable copy

            // Compare value1 with every other value (value2) in the dictionary
            while (!clavesCopia2.estaVacio()) {
                int key2 = clavesCopia2.elegir();
                clavesCopia2.sacar(key2);

                if (key1 == key2) { // Don't compare an element with itself
                    continue;
                }

                int value2 = dicIn.recuperar(key2);

                // Check if value1 is a subnumber of value2 OR value2 is a subnumber of value1
                if (esSubnumero(value1, value2) || esSubnumero(value2, value1)) {
                    // If the condition is met, add both related keys to the result set
                    if (!clavesResultantes.pertenece(key1)) {
                        clavesResultantes.agregar(key1);
                    }
                    if (!clavesResultantes.pertenece(key2)) {
                        clavesResultantes.agregar(key2);
                    }
                    // No need to break, continue checking key1 against other keys if needed,
                    // although adding to the set handles duplicates.
                }
            }
        }

        // Populate the output dictionary using the keys identified
        while (!clavesResultantes.estaVacio()) {
            int clave = clavesResultantes.elegir();
            clavesResultantes.sacar(clave);
            dicOut.agregar(clave, dicIn.recuperar(clave)); // Retrieve value from original dictionary
        }

        return dicOut;
    }
} 