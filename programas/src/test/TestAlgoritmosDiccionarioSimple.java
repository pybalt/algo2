package test;

import tda.DiccionarioSimpleTDA;
import tda.ConjuntoTDA;
import implementacion.DicSimpleA;
import implementacion.ConjuntoLD;
import algoritmos.AlgoritmosDiccionarioSimple;

public class TestAlgoritmosDiccionarioSimple {

    public static void main(String[] args) {
        DiccionarioSimpleTDA dicEntrada = new DicSimpleA();
        dicEntrada.InicializarDiccionario();

        // Populate input dictionary with example data
        dicEntrada.Agregar(1, 23);
        dicEntrada.Agregar(3, 56);
        dicEntrada.Agregar(4, 564);

        System.out.println("Diccionario de entrada:");
        mostrarDiccionario(dicEntrada);

        // Call the algorithm
        DiccionarioSimpleTDA dicSalida = AlgoritmosDiccionarioSimple.filtrarPorSubnumero(dicEntrada);

        System.out.println("\nDiccionario de salida (filtrado por subnúmero):");
        mostrarDiccionario(dicSalida);
    }

    /**
     * Helper function to print the contents of a dictionary.
     * @param dic The dictionary to print.
     */
    public static void mostrarDiccionario(DiccionarioSimpleTDA dic) {
        ConjuntoTDA claves = dic.Claves();
        ConjuntoTDA clavesCopia = new ConjuntoLD(); // Use a copy to avoid modifying the original keys set
        clavesCopia.inicializarConjunto();

        // Copy keys
        ConjuntoTDA aux = new ConjuntoLD();
        aux.inicializarConjunto();
        while (!claves.conjuntoVacio()) {
            int k = claves.elegir();
            aux.agregar(k);
            claves.sacar(k);
        }
        while (!aux.conjuntoVacio()) {
            int k = aux.elegir();
            claves.agregar(k); // Restore original set
            clavesCopia.agregar(k); // Populate copy
            aux.sacar(k);
        }

        System.out.print("{");
        boolean primero = true;
        while (!clavesCopia.conjuntoVacio()) {
            if (!primero) {
                System.out.print(", ");
            }
            int clave = clavesCopia.elegir();
            System.out.print(clave + ": " + dic.Recuperar(clave));
            clavesCopia.sacar(clave);
            primero = false;
        }
        System.out.println("}");
    }
} 