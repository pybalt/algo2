package algoritmos;

import implementacion.DiccionarioSimpleEstatico;
import tda.ConjuntoTDA;
import tda.DiccionarioSimpleTDA;

public class AlgoritmoPracticaDiccionario {
    public DiccionarioSimpleTDA obtenerSubnumeros(DiccionarioSimpleTDA diccionario) {
        DiccionarioSimpleTDA resultado = new DiccionarioSimpleEstatico();
        resultado.inicializar();

        ConjuntoTDA keys = diccionario.obtenerClaves();

        while (!keys.estaVacio()) {
            int k = keys.elegir();
            keys.sacar(k);
            int valor = diccionario.recuperar(k);

            // Obtengo el diccionario nuevamente
            ConjuntoTDA keys2 = diccionario.obtenerClaves();
            keys2.sacar(k); // Para evitar evaluar consigo mismo

            while (!keys2.estaVacio()) {
                int key2 = keys2.elegir();
                keys2.sacar(key2);

                // Verifico si k es subnúmero de valor
                int operacion = diccionario.recuperar(key2);
                while (operacion > valor) {
                    operacion = operacion / 10;

                    if (operacion == valor) {
                        resultado.agregar(k, valor);
                        resultado.agregar(key2, diccionario.recuperar(key2));
                    }
                }
            }
        }

        return resultado;
    }
}
