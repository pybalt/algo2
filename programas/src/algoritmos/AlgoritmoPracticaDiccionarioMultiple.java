package algoritmos;

import implementacion.DiccionarioMultipleEstatico;
import tda.ConjuntoTDA;
import tda.DiccionarioMultipleTDA;

public class AlgoritmoPracticaDiccionarioMultiple {
    public DiccionarioMultipleTDA mostrarDiccionario(DiccionarioMultipleTDA diccionario) {
        DiccionarioMultipleTDA resultado = new DiccionarioMultipleEstatico();
        resultado.inicializar();

        ConjuntoTDA claves = diccionario.obtenerClaves();

        while (!claves.estaVacio()) {
            int clave = claves.elegir();
            claves.sacar(clave);

            ConjuntoTDA valores = diccionario.recuperar(clave);

            while (!valores.estaVacio()) {
                int valor = valores.elegir();

                // Este valor está almacenado en otra key?
                ConjuntoTDA claves2 = diccionario.obtenerClaves();

                while (!claves2.estaVacio()) {
                    int clave2 = claves2.elegir();
                    claves2.sacar(clave2);

                    if (clave != clave2) {
                        ConjuntoTDA valores2 = diccionario.recuperar(clave2);
                        if (valores2.pertenece(valor)) {
                            resultado.agregar(valor, clave);
                            resultado.agregar(valor, clave2);
                        }
                    }
                }

                valores.sacar(valor);
            }
        }

        return resultado;
    }
}
