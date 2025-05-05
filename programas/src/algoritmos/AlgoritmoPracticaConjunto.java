package algoritmos;

import implementacion.ConjuntoEstatico;
import tda.ConjuntoTDA;

public class AlgoritmoPracticaConjunto {
    public ConjuntoTDA iterarConjunto(ConjuntoTDA conjunto) {
        // Conjunto para almacenar los valores para restaurar el conjunto original
        ConjuntoTDA aux = new ConjuntoEstatico();
        aux.inicializar();

        /**
         * Necesito una copia del conjunto original para iterarlo en cada elemento
         * que seleccione del conjunto para sumarlo.
         *
         * Lamentablemente, no encontré otra forma más eficiente de hacerlo
         * que recorrer el conjunto original, guardar los elementos y luego en la iteración de aux ir restaurando el conjunto original.
         *
         * Profe: si está leyendo esto le agradezco una devolución acerca de la estrategia utilizada.
         */
        ConjuntoTDA aux1 = new ConjuntoEstatico();
        aux1.inicializar();

        /**
         * Como mencione anteriormente, voy a recorrer el conjunto original, crear una copia en aux y volver a restaurarlo.
         */
        while (!conjunto.estaVacio()) {
            int valor = conjunto.elegir();
            aux.agregar(valor);
            aux1.agregar(valor);
            conjunto.sacar(valor);
        }

        // Conjunto para almacenar las sumas de cada iteración
        ConjuntoTDA resultado = new ConjuntoEstatico();
        resultado.inicializar();

        /**
         * Estrategia: debido a que ya tengo una copia del conjunto original en aux lo que voy a hacer es recorrer aux.
         * Cada elemento de aux me va a servir para hacer las iteraciones para comenzar la suma, y además me va a servir para restaurar el conjunto original.
         *
         * Con la copia llamada aux1 lo que voy a hacer es usarlo para comparar cada elemento con el elemento seleccionado del conjunto
         * y con eso haré la respectiva suma.
         *
         * La copia aux2 es para poder restaurar aux1 una vez haya finalizado todas las iteraciones.
         */
        while (!aux.estaVacio()) {
            int valor = aux.elegir();
            //System.out.println("APUNTO A " + valor);
            int suma = valor;

            // Inicializo un conjunto auxiliar para almacenar los valores para tenerlos disponibles en cada iteración
            ConjuntoTDA aux2 = new ConjuntoEstatico();
            aux2.inicializar();

            while (!aux1.estaVacio()) {
                // Primero sacamos el valor que tenemos seleccionado en la iteración padre
                aux2.agregar(valor);
                aux1.sacar(valor);

                // Tomamos un valor del conjunto
                int valor2 = aux1.elegir();
                //System.out.println("---- COMPARO A " + valor2 + " contra " + suma);

                // Vemos si es mayor a la suma
                if (valor2 > suma)
                    suma += valor2;

                // Sacamos este valor del conjunto
                aux1.sacar(valor2);
                aux2.agregar(valor2);
            }

            // Restauramos aux1 para poder utilizarlo en la próxima iteración
            while (!aux2.estaVacio()) {
                int valor2 = aux2.elegir();
                aux1.agregar(valor2);
                aux2.sacar(valor2);
            }

            //System.out.println("-------- Suma: " + suma);
            resultado.agregar(suma);
            conjunto.agregar(valor);
            aux.sacar(valor);
        }

        return resultado;
    }
}
