package algoritmos;

import implementacion.ConjuntoEstatico;
import implementacion.DiccionarioMultipleEstatico;
import implementacion.DiccionarioSimpleEstatico;
import tda.ConjuntoTDA;
import tda.DiccionarioMultipleTDA;
import tda.DiccionarioSimpleTDA;

public class AlgoritmosDiccionario {

    public static void muestroDiccionario(DiccionarioSimpleTDA diccionarioSimple) {
        ConjuntoTDA claves = diccionarioSimple.obtenerClaves();
        while (!claves.estaVacio()) {
            int clave = claves.elegir();
            claves.sacar(clave);
            System.out.println("Clave: " + clave + ", valor: " + diccionarioSimple.recuperar(clave));
        }
    }

    public static DiccionarioSimpleTDA obtenerValoresDeClave(DiccionarioSimpleTDA diccionarioSimple) {
        DiccionarioSimpleTDA nuevoDiccionario = new DiccionarioSimpleEstatico();
        nuevoDiccionario.inicializar();
        ConjuntoTDA claves = diccionarioSimple.obtenerClaves();
        while (!claves.estaVacio()) {
            int clave = claves.elegir();
            int valorClave = diccionarioSimple.recuperar(clave);
            ConjuntoTDA clavesInternas = diccionarioSimple.obtenerClaves();
            while (!clavesInternas.estaVacio()) {
                int claveInterna = clavesInternas.elegir();
                int valorClaveInterna = diccionarioSimple.recuperar(claveInterna);
                if (valorClave != valorClaveInterna && String.valueOf(valorClaveInterna).contains(String.valueOf(valorClave))) {
                    nuevoDiccionario.agregar(claveInterna, valorClaveInterna);
                    nuevoDiccionario.agregar(clave, valorClave);
                }
                clavesInternas.sacar(claveInterna);
            }
            claves.sacar(clave);
        }
        return nuevoDiccionario;
    }

    public static DiccionarioMultipleTDA devolverInterseccionDiccionarios(DiccionarioMultipleTDA d1, DiccionarioMultipleTDA d2) {
        //Devolver la intersección de claves y valores entre d1 y d2, pueden ser de tamaños diferentes
        DiccionarioMultipleTDA diccionarioResultado = new DiccionarioMultipleEstatico();
        diccionarioResultado.inicializar();
        ConjuntoTDA clavesD1 = d1.obtenerClaves();
        ConjuntoTDA clavesD2 = d2.obtenerClaves();
        ConjuntoTDA clavesInterseccion = new ConjuntoEstatico();
        clavesInterseccion.inicializar();

        while (!clavesD1.estaVacio()) {
            int clave1 = clavesD1.elegir();
            if (clavesD2.pertenece(clave1)) {
                clavesInterseccion.agregar(clave1);
            }
            clavesD1.sacar(clave1);
        }

        if (!clavesInterseccion.estaVacio()) {
            int claveInterseccion = clavesInterseccion.elegir();
            ConjuntoTDA interseccionD1 = d1.recuperar(claveInterseccion);
            ConjuntoTDA interseccionD2 = d2.recuperar(claveInterseccion);

            while (!interseccionD1.estaVacio()) {
                int valord1 = interseccionD1.elegir();
                diccionarioResultado.agregar(claveInterseccion, valord1);
                interseccionD1.sacar(valord1);
            }

            while (!interseccionD2.estaVacio()) {
                int valord2 = interseccionD2.elegir();
                diccionarioResultado.agregar(claveInterseccion, valord2);
                interseccionD2.sacar(valord2);
            }
        }

        return diccionarioResultado;
    }
}