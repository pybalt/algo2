package algoritmos;

import tda.ConjuntoTDA;
import tda.DiccionarioSimpleTDA;

public class AlgoritmosDiccionarios {
    public void muestroDiccionario(DiccionarioSimpleTDA diccionario) {
        ConjuntoTDA keys = diccionario.obtenerClaves();

        while (!keys.estaVacio()) {
            int k = keys.elegir();
            keys.sacar(k);

            System.out.println("Clave: " + k + " - Valor: " + diccionario.recuperar(k));
        }
    }
}
