package implementacion;

import tda.ABBTDA;

public class ABB implements ABBTDA {
    class Nodo {
        int valor;
        ABBTDA hijoIzquierdo;
        ABBTDA hijoDerecho;
    }
    private Nodo raiz;

    @Override
    public void inicializar() {
        raiz = null;
    }

    @Override
    public int raiz() {
        return raiz.valor;
    }

    @Override
    public boolean estaVacio() {
        return raiz == null;
    }

    @Override
    public ABBTDA hijoDerecho() {
        return raiz.hijoDerecho;
    }

    @Override
    public ABBTDA hijoIzquierdo() {
        return raiz.hijoIzquierdo;
    }
    private int menor(ABBTDA arbol){
        if(arbol.hijoIzquierdo().estaVacio()){
            return arbol.raiz();
        }else{
            return menor(arbol.hijoIzquierdo());
        }
    }
    private int mayor(ABBTDA arbol){
        if(arbol.hijoDerecho().estaVacio()){
            return arbol.raiz();
        }else{
            return mayor(arbol.hijoDerecho());
        }
    }

    @Override
    public void agregar(int valor) {
        if(raiz == null){
            raiz = new Nodo();
            raiz.valor = valor;
            raiz.hijoIzquierdo = new ABB();
            raiz.hijoIzquierdo.inicializar();
            raiz.hijoDerecho = new ABB();
            raiz.hijoDerecho.inicializar();
        }else if (raiz.valor > valor){
            raiz.hijoIzquierdo.agregar(valor);
        }else if (raiz.valor < valor){
            raiz.hijoDerecho.agregar(valor);
        }
    }

    @Override
    public void eliminar(int valor) {
        // Caso 1: El nodo a eliminar es la raiz y es una hoja
        if( raiz != null
            && raiz.hijoIzquierdo.estaVacio()
            && raiz.hijoDerecho.estaVacio()
            && raiz.valor == valor
        ){
            raiz = null;
        }
        // Caso 2: El nodo a eliminar es la raíz y tiene solo hijo izquierdo
        else if (
                raiz != null
                && raiz.hijoDerecho.estaVacio()
                && !raiz.hijoIzquierdo.estaVacio()
                && raiz.valor == valor
        ) {
            raiz.valor = mayor(raiz.hijoIzquierdo);
            raiz.hijoIzquierdo.eliminar(raiz.valor);
        }
        // Caso 3: El nodo a eliminar es la raíz y tiene solo hijo derecho
        else if (
            raiz != null
            && !raiz.hijoDerecho.estaVacio()
            && raiz.hijoIzquierdo.estaVacio()
            && raiz.valor == valor
        ) {
            raiz.valor = menor(raiz.hijoDerecho);
            raiz.hijoDerecho.eliminar(raiz.valor);
        }
        // Caso 4: El nodo a eliminar es la raíz y tiene ambos hijos
        /**
         * hay varios métodos de reemplazo para eliminar nodos con dos hijos en un BST:
         * 1. Predecesor Inorden
         *
         * El mayor elemento del subárbol izquierdo
         * Es el nodo más a la derecha del subárbol izquierdo
         * Garantiza que sea menor que todos los elementos del subárbol derecho
         *
         * 2. Sucesor Inorden
         *
         * El menor elemento del subárbol derecho
         * Es el nodo más a la izquierda del subárbol derecho
         * Garantiza que sea mayor que todos los elementos del subárbol izquierdo
         *
         * 3. Estrategias de Balanceo
         *
         * Alternancia: Alternar entre predecesor y sucesor para mantener mejor balance
         * Por altura: Elegir del subárbol más alto para reducir la altura total
         * Por tamaño: Elegir del subárbol con más nodos
         * Comparación de estrategias:
         * Predecesor Inorden:
         *
         * ✅ Simple de implementar
         * ✅ Siempre funciona
         * ❌ Puede crear desequilibrio hacia la derecha
         *
         * Sucesor Inorden:
         *
         * ✅ Simple de implementar
         * ✅ Siempre funciona
         * ❌ Puede crear desequilibrio hacia la izquierda
         *
         * Alternancia:
         *
         * ✅ Mejor balance general
         * ✅ Fácil de implementar
         * ❌ No considera la estructura actual del árbol
         *
         * Por Altura:
         *
         * ✅ Mantiene mejor balance
         * ✅ Reduce la altura total
         * ❌ Requiere calcular alturas (más costoso)
         *
         * Por Tamaño:
         *
         * ✅ Distribución más uniforme
         * ❌ Requiere calcular tamaños (más costoso)
         *
         * Híbrida:
         *
         * ✅ Combina ventajas de varias estrategias
         * ✅ Se adapta a la situación del árbol
         * ❌ Más compleja de implementar
         *
         * La elección depende de tus prioridades:
         * simplicidad (predecesor/sucesor),
         * balance (alternancia/altura),
         * o rendimiento (híbrida para casos específicos).
         */
        else if (
                raiz != null
                && raiz.valor == valor
                && !raiz.hijoDerecho.estaVacio()
                && !raiz.hijoDerecho.estaVacio()
        ) {
            // Aca hay varios approach
            // El mas adecuado creo que es usar el predecesor inorden
            // Que es el mayor del hijo izquierdo
            raiz.valor = mayor(raiz.hijoIzquierdo);
            raiz.hijoIzquierdo.eliminar(raiz.valor);
        }
        // Caso 5: Buscar en el subárbol derecho
        else if(
                raiz != null
                && raiz.valor < valor
        ){
            raiz.hijoDerecho.eliminar(valor);
        }
        // Caso 6: Buscar en el subarbol izquierdo
        else if (
                raiz != null
                && raiz.valor > valor
        ) {
            raiz.hijoIzquierdo.eliminar(valor);
        }
        // Caso implicito: raiz es null o el valor no existe (no se hace nada)
    }
}
