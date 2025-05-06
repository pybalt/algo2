package test;

import tda.DiccionarioSimpleTDA;
import tda.ConjuntoTDA;
import implementacion.DiccionarioSimpleEstatico;
import implementacion.ConjuntoEstatico;
import algoritmos.AlgoritmosDiccionarioSimple;

public class TestAlgoritmosDiccionarioSimple {

    public static void main(String[] args) {
        DiccionarioSimpleTDA dicEntrada = new DiccionarioSimpleEstatico();
        dicEntrada.inicializar();

        // Populate input dictionary with example data
        dicEntrada.agregar(1, 23);
        dicEntrada.agregar(3, 56);
        dicEntrada.agregar(4, 564);

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
        ConjuntoTDA claves = dic.obtenerClaves();
        ConjuntoTDA clavesCopia = new ConjuntoEstatico();
        clavesCopia.inicializar();

        // Copy keys
        ConjuntoTDA aux = new ConjuntoEstatico();
        aux.inicializar();
        while (!claves.estaVacio()) {
            int k = claves.elegir();
            aux.agregar(k);
            claves.sacar(k);
        }
        while (!aux.estaVacio()) {
            int k = aux.elegir();
            claves.agregar(k);
            clavesCopia.agregar(k);
            aux.sacar(k);
        }

        System.out.print("{");
        boolean primero = true;
        while (!clavesCopia.estaVacio()) {
            if (!primero) {
                System.out.print(", ");
            }
            int clave = clavesCopia.elegir();
            System.out.print(clave + ": " + dic.recuperar(clave));
            clavesCopia.sacar(clave);
            primero = false;
        }
        System.out.println("}");
    }
} 