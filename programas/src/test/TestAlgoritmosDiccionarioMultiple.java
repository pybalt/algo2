package test;

import algoritmos.AlgoritmosDiccionarioMultiple;
import implementacion.ConjuntoLD;
import implementacion.DicMultipleA;
import tda.ConjuntoTDA;
import tda.DiccionarioMultipleTDA;

public class TestAlgoritmosDiccionarioMultiple {

    public static void main(String[] args) {
        testInvertirDiccionario();
    }

    public static void testInvertirDiccionario() {
        System.out.println("Testing invertirDiccionarioConRepetidos...");

        DiccionarioMultipleTDA originalDict = new DicMultipleA();
        originalDict.inicializarDiccionario();

        // Populate original dictionary based on the example
        // {2} ---> {3, 5, 7, 9, 10}
        originalDict.agregar(2, 3);
        originalDict.agregar(2, 5);
        originalDict.agregar(2, 7);
        originalDict.agregar(2, 9);
        originalDict.agregar(2, 10);

        // {3} ---> {2, 5, 6, 19, 1}
        originalDict.agregar(3, 2);
        originalDict.agregar(3, 5);
        originalDict.agregar(3, 6);
        originalDict.agregar(3, 19);
        originalDict.agregar(3, 1);

        // {4} ---> {19, 5, 4, 9, 11}
        originalDict.agregar(4, 19);
        originalDict.agregar(4, 5);
        originalDict.agregar(4, 4);
        originalDict.agregar(4, 9);
        originalDict.agregar(4, 11);

        // Call the algorithm
        DiccionarioMultipleTDA resultadoDict = AlgoritmosDiccionarioMultiple.invertirDiccionarioConRepetidos(originalDict);

        // --- Verification ---
        boolean passed = true;

        // Expected keys: 5, 9, 19
        ConjuntoTDA clavesResultado = resultadoDict.claves();
        ConjuntoTDA clavesEsperadas = new ConjuntoLD();
        clavesEsperadas.inicializarConjunto();
        clavesEsperadas.agregar(5);
        clavesEsperadas.agregar(9);
        clavesEsperadas.agregar(19);

        if (!conjuntosIguales(clavesResultado, clavesEsperadas)) {
            System.out.println("  FAILED: Result keys mismatch.");
            System.out.print("    Expected: "); imprimirConjunto(clavesEsperadas);
            System.out.print("    Got: "); imprimirConjunto(resultadoDict.claves()); // Print fresh copy
            passed = false;
        }

        // Expected values for key 5: {2, 3, 4}
        ConjuntoTDA valores5Esperado = new ConjuntoLD();
        valores5Esperado.inicializarConjunto();
        valores5Esperado.agregar(2);
        valores5Esperado.agregar(3);
        valores5Esperado.agregar(4);
        if (!conjuntosIguales(resultadoDict.recuperar(5), valores5Esperado)) {
            System.out.println("  FAILED: Values for key 5 mismatch.");
            System.out.print("    Expected: "); imprimirConjunto(valores5Esperado);
            System.out.print("    Got: "); imprimirConjunto(resultadoDict.recuperar(5));
            passed = false;
        }

        // Expected values for key 9: {2, 4}
        ConjuntoTDA valores9Esperado = new ConjuntoLD();
        valores9Esperado.inicializarConjunto();
        valores9Esperado.agregar(2);
        valores9Esperado.agregar(4);
         if (!conjuntosIguales(resultadoDict.recuperar(9), valores9Esperado)) {
            System.out.println("  FAILED: Values for key 9 mismatch.");
            System.out.print("    Expected: "); imprimirConjunto(valores9Esperado);
            System.out.print("    Got: "); imprimirConjunto(resultadoDict.recuperar(9));
             passed = false;
        }

        // Expected values for key 19: {3, 4}
        ConjuntoTDA valores19Esperado = new ConjuntoLD();
        valores19Esperado.inicializarConjunto();
        valores19Esperado.agregar(3);
        valores19Esperado.agregar(4);
         if (!conjuntosIguales(resultadoDict.recuperar(19), valores19Esperado)) {
            System.out.println("  FAILED: Values for key 19 mismatch.");
            System.out.print("    Expected: "); imprimirConjunto(valores19Esperado);
            System.out.print("    Got: "); imprimirConjunto(resultadoDict.recuperar(19));
             passed = false;
        }

        // --- Check non-included keys --- 
        // Value 7 appears only once (key 2), should not be a key in result
        if (clavesResultado.pertenece(7)) {
             System.out.println("  FAILED: Key 7 should not be present in result.");
             passed = false;
        }
        // Value 2 appears only once (key 3), should not be a key in result
         if (clavesResultado.pertenece(2)) {
             System.out.println("  FAILED: Key 2 should not be present in result.");
             passed = false;
        }

        if (passed) {
            System.out.println("  PASSED!");
        }
         System.out.println("-----------------------------------------");
    }

    // Helper method to check if two sets are equal
    private static boolean conjuntosIguales(ConjuntoTDA c1, ConjuntoTDA c2) {
        ConjuntoTDA c1Copia = copiarConjunto(c1);
        ConjuntoTDA c2Copia = copiarConjunto(c2);

        // Check size first (quick check)
        int size1 = 0;
        int size2 = 0;
        ConjuntoTDA temp1 = copiarConjunto(c1Copia);
        ConjuntoTDA temp2 = copiarConjunto(c2Copia);
        while(!temp1.conjuntoVacio()) { size1++; temp1.sacar(temp1.elegir()); }
        while(!temp2.conjuntoVacio()) { size2++; temp2.sacar(temp2.elegir()); }
        if (size1 != size2) {
            return false;
        }

        // Check elements
        while (!c1Copia.conjuntoVacio()) {
            int elemento = c1Copia.elegir();
            if (!c2Copia.pertenece(elemento)) {
                return false;
            }
            c1Copia.sacar(elemento);
            c2Copia.sacar(elemento); // Remove matching element from c2
        }

        // If we finished and c2 is also empty, they had the same elements
        return c2Copia.conjuntoVacio();
    }

    // Helper method to copy a ConjuntoTDA (same as in Algoritmos class, could be moved to a util class)
    private static ConjuntoTDA copiarConjunto(ConjuntoTDA original) {
        ConjuntoTDA copia = new ConjuntoLD();
        copia.inicializarConjunto();
        ConjuntoTDA aux = new ConjuntoLD();
        aux.inicializarConjunto();

        // Need a mutable copy of the original to iterate
        ConjuntoTDA originalMutable = new ConjuntoLD();
        originalMutable.inicializarConjunto();
        ConjuntoTDA temp = new ConjuntoLD(); // To restore original
        temp.inicializarConjunto();
        while(!original.conjuntoVacio()) {
             int x = original.elegir();
             temp.agregar(x);
             originalMutable.agregar(x);
             original.sacar(x);
        }
         while(!temp.conjuntoVacio()) {
             int x = temp.elegir();
             original.agregar(x); // Restore original
             temp.sacar(x);
        }


        while (!originalMutable.conjuntoVacio()) {
            int x = originalMutable.elegir();
            aux.agregar(x);
            originalMutable.sacar(x);
        }

        while (!aux.conjuntoVacio()) {
            int x = aux.elegir();
           // originalMutable.agregar(x); // Restore original copy (not needed here)
            copia.agregar(x);    // Add to the final copy
            aux.sacar(x);
        }
        return copia;
    }
    
    // Helper to print set contents for debugging
    public static void imprimirConjunto(ConjuntoTDA c) {
        ConjuntoTDA copia = copiarConjunto(c);
        System.out.print("{ ");
        boolean first = true;
        while (!copia.conjuntoVacio()) {
            if (!first) {
                System.out.print(", ");
            }
            int x = copia.elegir();
            System.out.print(x);
            copia.sacar(x);
            first = false;
        }
        System.out.println(" }");
    }
} 