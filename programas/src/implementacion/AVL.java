package implementacion;
import tda.ABBTDA;

public class AVL implements ABBTDA {
    class Nodo {
        int valor;
        int altura;
        ABBTDA izquierdo;
        ABBTDA derecho;
    }
    private Nodo raiz;
    
    /**
     * Complejidad temporal: O(1)
     * Acceso directo al hijo izquierdo almacenado
     */
    @Override
    public ABBTDA hijoIzquierdo() {
        return raiz.izquierdo;
    }

    /**
     * Complejidad temporal: O(1)
     * Acceso directo al hijo derecho almacenado
     */
    @Override
    public ABBTDA hijoDerecho() {
        return raiz.derecho;
    }
    
    /**
     * Complejidad temporal: O(1)
     * Asignacion simple de referencia a null
     */
    @Override
    public void inicializar() {
        raiz = null;
    }

    /**
     * Complejidad temporal: O(1)
     * Verificacion simple de referencia null
     */
    @Override
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Complejidad temporal: O(1)
     * Acceso directo al valor de la raiz
     */
    @Override
    public int raiz() {
        return raiz.valor;
    }

    /**
     * Complejidad temporal: O(log n)
     * - Busqueda de posicion: O(log n) en arbol balanceado
     * - Recalculo de altura en cada nivel: O(1) accediendo altura almacenada en hijos
     * - Rebalanceo: O(1) por rotaciones
     */
    @Override
    public void agregar(int valor) {
        // Fase 1: Insercion BST
        if(raiz == null){
            raiz = new Nodo();
            raiz.valor = valor;
            raiz.altura = 1;
            raiz.izquierdo = new AVL();
            raiz.izquierdo.inicializar();
            raiz.derecho = new AVL();
            raiz.derecho.inicializar();
        }else if(raiz.valor > valor){
            raiz.izquierdo.agregar(valor);
        }else if(raiz.valor < valor){
            raiz.derecho.agregar(valor);
        }
        // Fase 2: Actualiza altura - O(1) accediendo alturas almacenadas
        int alturaIzq = raiz.izquierdo.estaVacio() ? 0 : ((AVL)raiz.izquierdo).raiz.altura;
        int alturaDer = raiz.derecho.estaVacio() ? 0 : ((AVL)raiz.derecho).raiz.altura;
        raiz.altura = 1 + Math.max(alturaIzq, alturaDer);
        // Fase 3: Calcula balance
        int balance = balance(raiz);
        // Fase 4: Rebalancear
        rebalancear(balance);
    }

    /**
     * Complejidad temporal: O(log n)
     * - Busqueda del nodo: O(log n)
     * - Busqueda de sucesor (si necesario): O(log n)
     * - Recalculo de altura en cada nivel: O(1) accediendo altura almacenada
     * - Rebalanceo: O(1) por rotaciones + O(1) por verificaciones optimizadas
     * Total: O(log n)
     */
    @Override
    public void eliminar(int valor) {
        if (estaVacio()) {
            return;
        }
        
        // Fase 1: Eliminación BST estándar
        if (valor < raiz.valor) {
            raiz.izquierdo.eliminar(valor);
        } else if (valor > raiz.valor) {
            raiz.derecho.eliminar(valor);
        } else {
            // Este es el nodo a eliminar
            
            // Caso 1: Nodo hoja
            if (raiz.izquierdo.estaVacio() && raiz.derecho.estaVacio()) {
                raiz = null;
                return;
            }
            
            // Caso 2: Nodo con solo hijo derecho
            else if (raiz.izquierdo.estaVacio()) {
                AVL temp = (AVL) raiz.derecho;
                if (!temp.estaVacio()) {
                    raiz.valor = temp.raiz.valor;
                    raiz.altura = temp.raiz.altura;
                    raiz.izquierdo = temp.raiz.izquierdo;
                    raiz.derecho = temp.raiz.derecho;
                } else {
                    raiz = null;
                }
                return;
            }
            
            // Caso 3: Nodo con solo hijo izquierdo
            else if (raiz.derecho.estaVacio()) {
                AVL temp = (AVL) raiz.izquierdo;
                if (!temp.estaVacio()) {
                    raiz.valor = temp.raiz.valor;
                    raiz.altura = temp.raiz.altura;
                    raiz.izquierdo = temp.raiz.izquierdo;
                    raiz.derecho = temp.raiz.derecho;
                } else {
                    raiz = null;
                }
                return;
            }
            
            // Caso 4: Nodo con dos hijos
            else {
                // Encontrar el sucesor inorden (menor valor en subárbol derecho)
                int sucesor = encontrarMinimo(raiz.derecho);
                raiz.valor = sucesor;
                raiz.derecho.eliminar(sucesor);
            }
        }
        
        // Si llegamos aquí, el árbol no está vacío después de la eliminación
        if (raiz == null) {
            return;
        }
        
        // Fase 2: Actualizar altura - O(1) accediendo alturas almacenadas
        int alturaIzq = raiz.izquierdo.estaVacio() ? 0 : ((AVL)raiz.izquierdo).raiz.altura;
        int alturaDer = raiz.derecho.estaVacio() ? 0 : ((AVL)raiz.derecho).raiz.altura;
        raiz.altura = 1 + Math.max(alturaIzq, alturaDer);
        
        // Fase 3: Calcular balance - O(1) usando alturas almacenadas
        int balance = alturaDer - alturaIzq;
        
        // Fase 4: Rebalancear - O(1) usando balance optimizado
        rebalancearOptimizado(balance);
    }
    
    /**
     * Complejidad temporal: O(log n)
     * Busqueda del minimo siguiendo el camino izquierdo
     */
    private int encontrarMinimo(ABBTDA arbol) {
        while (!arbol.hijoIzquierdo().estaVacio()) {
            arbol = arbol.hijoIzquierdo();
        }
        
        return arbol.raiz();
    }

    /**
     * Complejidad temporal: O(1)
     * Reorganizacion de referencias sin recorrido de nodos + actualizacion de alturas
     */
    private void rotarIzquierda(){
        if (estaVacio() || hijoDerecho().estaVacio()) {
            return;
        }
        
        // Guardamos los valores y subárboles actuales
        int valorZ = raiz();
        int valorY = hijoDerecho().raiz();
        ABBTDA T1 = hijoIzquierdo();
        ABBTDA T2 = hijoDerecho().hijoIzquierdo();
        ABBTDA x = hijoDerecho().hijoDerecho();
        
        // Reorganizamos: y se convierte en la nueva raíz
        raiz.valor = valorY;
        
        // Creamos nuevo subárbol izquierdo (z con T1 y T2)
        AVL nuevoZ = new AVL();
        nuevoZ.inicializar();
        nuevoZ.crearNodoConHijos(valorZ, T1, T2);
        
        // Actualizamos altura del nuevo nodo Z
        int alturaT1 = T1.estaVacio() ? 0 : ((AVL)T1).raiz.altura;
        int alturaT2 = T2.estaVacio() ? 0 : ((AVL)T2).raiz.altura;
        nuevoZ.raiz.altura = 1 + Math.max(alturaT1, alturaT2);
        
        // Asignamos los nuevos hijos
        raiz.izquierdo = nuevoZ;
        raiz.derecho = x;
        
        // Actualizamos altura de la nueva raíz
        int alturaX = x.estaVacio() ? 0 : ((AVL)x).raiz.altura;
        raiz.altura = 1 + Math.max(nuevoZ.raiz.altura, alturaX);
    }

    /**
     * Complejidad temporal: O(1)
     * Reorganizacion de referencias sin recorrido de nodos + actualizacion de alturas
     */
    private void rotarDerecha(){
        if (estaVacio() || hijoIzquierdo().estaVacio()) {
            return;
        }
        // Guardamos los valores y subÁrboles actuales
        int valorZ = raiz();
        int valorY = hijoIzquierdo().raiz();

        ABBTDA x = hijoIzquierdo().hijoIzquierdo();
        ABBTDA T3 = hijoIzquierdo().hijoDerecho();
        ABBTDA T4 = hijoDerecho();

        // Reorganizamos: y se convierte en la nueva raíz
        raiz.valor = valorY;
        
        // Creamos nuevo subárbol derecho (z con T3 y T4)
        AVL nuevoZ = new AVL();
        nuevoZ.inicializar();
        nuevoZ.crearNodoConHijos(valorZ, T3, T4);
        
        // Actualizamos altura del nuevo nodo Z
        int alturaT3 = T3.estaVacio() ? 0 : ((AVL)T3).raiz.altura;
        int alturaT4 = T4.estaVacio() ? 0 : ((AVL)T4).raiz.altura;
        nuevoZ.raiz.altura = 1 + Math.max(alturaT3, alturaT4);
        
        // Asignamos nuevos hijos
        raiz.izquierdo = x;
        raiz.derecho = nuevoZ;
        
        // Actualizamos altura de la nueva raíz
        int alturaX = x.estaVacio() ? 0 : ((AVL)x).raiz.altura;
        raiz.altura = 1 + Math.max(alturaX, nuevoZ.raiz.altura);
    }
    
    /**
     * Complejidad temporal: O(n)
     * Llama a alturaTDA dos veces, cada una recorre subarboles completos
     */
    private int balance(Nodo nodo){
        if (nodo == null) {
            return 0;
        }
        return alturaTDA(nodo.derecho) - alturaTDA(nodo.izquierdo);
    }

    /**
     * Complejidad temporal: O(m) donde m es el numero de nodos del arbol
     * Recorre recursivamente todos los nodos del subarbol
     */
    private int alturaTDA(ABBTDA arbol){
        if (arbol == null || arbol.estaVacio()) {
            return 0;
        }
        return 1 + Math.max(alturaTDA(arbol.hijoIzquierdo()), alturaTDA(arbol.hijoDerecho()));
    }
    
    /**
     * Complejidad temporal: O(1)
     * Asignacion de referencias
     */
    private void crearNodoConHijos(int valor, ABBTDA hijoIzq, ABBTDA hijoDer) {
        if (raiz == null) {
            raiz = new Nodo();
        }
        raiz.valor = valor;
        raiz.izquierdo = hijoIzq;
        raiz.derecho = hijoDer;
    }

    /**
     * Complejidad temporal: O(n)
     * Llama a alturaTDA dos veces sobre subarboles
     */
    private int balanceTDA(ABBTDA arbol){
        if (arbol == null || arbol.estaVacio()) {
            return 0;
        }
        return alturaTDA(arbol.hijoDerecho()) - alturaTDA(arbol.hijoIzquierdo());
    }
    
    /**
     * Complejidad temporal: O(1) para las rotaciones + O(n) para calculos de balance
     * - Verificacion de condiciones: O(n) por llamadas a balanceTDA
     * - Rotaciones simples o dobles: O(1) cada una
     * Total: O(n) dominado por calculos de balance
     */
    private void rebalancear(int balance) {
        // Caso Left Left (LL) - Rotacion simple derecha
        if (balance < -1 && balanceTDA(raiz.izquierdo) <= 0) {
            rotarDerecha();
        }
        // Caso Right Right (RR) - Rotacion simple izquierda  
        else if (balance > 1 && balanceTDA(raiz.derecho) >= 0) {
            rotarIzquierda();
        }
        // Caso Left Right (LR) - Rotacion doble derecha
        else if (balance < -1 && balanceTDA(raiz.izquierdo) > 0) {
            ((AVL)raiz.izquierdo).rotarIzquierda();
            rotarDerecha();
        }
        // Caso Right Left (RL) - Rotacion doble izquierda
        else if (balance > 1 && balanceTDA(raiz.derecho) < 0) {
            ((AVL)raiz.derecho).rotarDerecha();
            rotarIzquierda();
        }
    }

    /**
     * Complejidad temporal: O(1)
     * Version optimizada que usa alturas almacenadas en lugar de recalcular
     */
    private void rebalancearOptimizado(int balance) {
        // Caso Left Left (LL) - Rotacion simple derecha
        if (balance < -1 && balanceOptimizado(raiz.izquierdo) <= 0) {
            rotarDerecha();
        }
        // Caso Right Right (RR) - Rotacion simple izquierda  
        else if (balance > 1 && balanceOptimizado(raiz.derecho) >= 0) {
            rotarIzquierda();
        }
        // Caso Left Right (LR) - Rotacion doble derecha
        else if (balance < -1 && balanceOptimizado(raiz.izquierdo) > 0) {
            ((AVL)raiz.izquierdo).rotarIzquierda();
            rotarDerecha();
        }
        // Caso Right Left (RL) - Rotacion doble izquierda
        else if (balance > 1 && balanceOptimizado(raiz.derecho) < 0) {
            ((AVL)raiz.derecho).rotarDerecha();
            rotarIzquierda();
        }
    }
    
    /**
     * Complejidad temporal: O(1)
     * Calcula balance usando alturas ya almacenadas
     */
    private int balanceOptimizado(ABBTDA arbol) {
        if (arbol == null || arbol.estaVacio()) {
            return 0;
        }
        
        AVL avlNodo = (AVL) arbol;
        int alturaIzq = avlNodo.raiz.izquierdo.estaVacio() ? 0 : ((AVL)avlNodo.raiz.izquierdo).raiz.altura;
        int alturaDer = avlNodo.raiz.derecho.estaVacio() ? 0 : ((AVL)avlNodo.raiz.derecho).raiz.altura;
        
        return alturaDer - alturaIzq;
    }
}
