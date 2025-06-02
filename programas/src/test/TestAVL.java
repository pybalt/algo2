package test;
import implementacion.AVL;
import tda.ABBTDA;

public class TestAVL {
    
    public static void main(String[] args) {
        System.out.println("=== AVL TDD Tests ===");
        
        testBasicOperations();
        testSingleRotations();
        testDoubleRotations();
        testComplexInsertions();
        testDeletions();
        testBalanceProperties();
        testEdgeCases();
        
        // Nuevos tests agregados
        testHeightAccuracy();
        testPerformanceAndStress();
        testSpecificRotationScenarios();
        testBoundaryConditions();
        testSequentialOperations();
        testRandomOperations();
        testLargeDatasets();
        
        System.out.println("All tests completed!");
    }
    
    // Test 1: Basic Operations
    public static void testBasicOperations() {
        System.out.println("\n--- Test 1: Basic Operations ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Test empty tree
        assert avl.estaVacio() : "New tree should be empty";
        System.out.println("✓ Empty tree test passed");
        
        // Test single insertion
        avl.agregar(10);
        assert !avl.estaVacio() : "Tree with one element should not be empty";
        assert avl.raiz() == 10 : "Root should be 10";
        System.out.println("✓ Single insertion test passed");
        
        // Test children are empty
        assert avl.hijoIzquierdo().estaVacio() : "Left child should be empty";
        assert avl.hijoDerecho().estaVacio() : "Right child should be empty";
        System.out.println("✓ Children empty test passed");
    }
    
    // Test 2: Right rotation (LL case)
    public static void testSingleRotations() {
        System.out.println("\n--- Test 2: Single Rotations ---");
        
        // LL case: insert 10, 5, 1 (should trigger right rotation)
        ABBTDA avl = new AVL();
        avl.inicializar();
        avl.agregar(10);
        avl.agregar(5);
        avl.agregar(1);
        
        // After rotation: root should be 5, left=1, right=10
        assert avl.raiz() == 5 : "Root should be 5 after LL rotation";
        assert avl.hijoIzquierdo().raiz() == 1 : "Left child should be 1";
        assert avl.hijoDerecho().raiz() == 10 : "Right child should be 10";
        System.out.println("✓ LL rotation test passed");
        
        // RR case: insert 1, 5, 10 (should trigger left rotation)
        avl = new AVL();
        avl.inicializar();
        avl.agregar(1);
        avl.agregar(5);
        avl.agregar(10);
        
        // After rotation: root should be 5, left=1, right=10
        assert avl.raiz() == 5 : "Root should be 5 after RR rotation";
        assert avl.hijoIzquierdo().raiz() == 1 : "Left child should be 1";
        assert avl.hijoDerecho().raiz() == 10 : "Right child should be 10";
        System.out.println("✓ RR rotation test passed");
    }
    
    // Test 3: Double rotations
    public static void testDoubleRotations() {
        System.out.println("\n--- Test 3: Double Rotations ---");
        
        // LR case: insert 10, 1, 5 (should trigger left-right rotation)
        ABBTDA avl = new AVL();
        avl.inicializar();
        avl.agregar(10);
        avl.agregar(1);
        avl.agregar(5);
        
        // After LR rotation: root should be 5, left=1, right=10
        assert avl.raiz() == 5 : "Root should be 5 after LR rotation";
        assert avl.hijoIzquierdo().raiz() == 1 : "Left child should be 1";
        assert avl.hijoDerecho().raiz() == 10 : "Right child should be 10";
        System.out.println("✓ LR rotation test passed");
        
        // RL case: insert 1, 10, 5 (should trigger right-left rotation)
        avl = new AVL();
        avl.inicializar();
        avl.agregar(1);
        avl.agregar(10);
        avl.agregar(5);
        
        // After RL rotation: root should be 5, left=1, right=10
        assert avl.raiz() == 5 : "Root should be 5 after RL rotation";
        assert avl.hijoIzquierdo().raiz() == 1 : "Left child should be 1";
        assert avl.hijoDerecho().raiz() == 10 : "Right child should be 10";
        System.out.println("✓ RL rotation test passed");
    }
    
    // Test 4: Complex insertion sequences
    public static void testComplexInsertions() {
        System.out.println("\n--- Test 4: Complex Insertions ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Insert sequence that forces multiple rotations
        int[] sequence = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 35};
        
        for (int val : sequence) {
            avl.agregar(val);
            assert isBalanced(avl) : "Tree should remain balanced after inserting " + val;
            assert isBST(avl) : "Tree should maintain BST property after inserting " + val;
        }
        
        System.out.println("✓ Complex insertion sequence test passed");
        
        // Test ascending sequence (worst case for regular BST)
        avl = new AVL();
        avl.inicializar();
        
        for (int i = 1; i <= 7; i++) {
            avl.agregar(i);
            assert isBalanced(avl) : "Tree should remain balanced during ascending insertion";
            assert getHeight(avl) <= Math.log(i) / Math.log(2) + 2 : "Height should be logarithmic";
        }
        
        System.out.println("✓ Ascending sequence test passed");
    }
    
    // Test 5: Deletions
    public static void testDeletions() {
        System.out.println("\n--- Test 5: Deletions ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Build a tree
        int[] values = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 35};
        for (int val : values) {
            avl.agregar(val);
        }
        
        // Test leaf deletion
        avl.eliminar(5);
        assert isBalanced(avl) : "Tree should remain balanced after leaf deletion";
        assert isBST(avl) : "Tree should maintain BST property after leaf deletion";
        System.out.println("✓ Leaf deletion test passed");
        
        // Test node with one child deletion
        avl.eliminar(10);
        assert isBalanced(avl) : "Tree should remain balanced after single child deletion";
        assert isBST(avl) : "Tree should maintain BST property after single child deletion";
        System.out.println("✓ Single child deletion test passed");
        
        // Test node with two children deletion
        avl.eliminar(25);
        assert isBalanced(avl) : "Tree should remain balanced after two children deletion";
        assert isBST(avl) : "Tree should maintain BST property after two children deletion";
        System.out.println("✓ Two children deletion test passed");
        
        // Test root deletion
        int oldRoot = avl.raiz();
        avl.eliminar(oldRoot);
        assert isBalanced(avl) : "Tree should remain balanced after root deletion";
        assert isBST(avl) : "Tree should maintain BST property after root deletion";
        System.out.println("✓ Root deletion test passed");
    }
    
    // Test 6: Balance properties
    public static void testBalanceProperties() {
        System.out.println("\n--- Test 6: Balance Properties ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Test that balance factor never exceeds 1
        for (int i = 1; i <= 15; i++) {
            avl.agregar(i);
            assert Math.abs(getBalanceFactor(avl)) <= 1 : "Balance factor should never exceed 1";
            assertAllNodesBalanced(avl);
        }
        
        System.out.println("✓ Balance factor test passed");
        
        // Test height property
        int nodeCount = countNodes(avl);
        int height = getHeight(avl);
        double minHeight = Math.log(nodeCount + 1) / Math.log(2) - 1;
        double maxHeight = 1.44 * Math.log(nodeCount + 2) / Math.log(2) - 0.328;
        
        assert height >= minHeight : "Height should be at least log(n+1) - 1";
        assert height <= maxHeight : "Height should be at most 1.44*log(n+2) - 0.328";
        
        System.out.println("✓ Height property test passed");
    }
    
    // Test 7: Edge cases
    public static void testEdgeCases() {
        System.out.println("\n--- Test 7: Edge Cases ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Test duplicate insertion
        avl.agregar(5);
        avl.agregar(5);
        assert avl.raiz() == 5 : "Duplicate insertion should not break tree";
        System.out.println("✓ Duplicate insertion test passed");
        
        // Test deleting non-existent element
        avl.eliminar(999);
        assert !avl.estaVacio() : "Deleting non-existent element should not break tree";
        System.out.println("✓ Non-existent deletion test passed");
        
        // Test deleting from empty tree
        avl = new AVL();
        avl.inicializar();
        avl.eliminar(10);
        assert avl.estaVacio() : "Deleting from empty tree should keep it empty";
        System.out.println("✓ Empty tree deletion test passed");
        
        // Test single element tree
        avl.agregar(42);
        assert avl.raiz() == 42 : "Single element tree should work";
        avl.eliminar(42);
        assert avl.estaVacio() : "Deleting only element should make tree empty";
        System.out.println("✓ Single element test passed");
    }
    
    // Nuevo Test: Verificar precision de alturas
    public static void testHeightAccuracy() {
        System.out.println("\n--- Test 8: Height Accuracy ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Test altura en arbol balanceado perfecto
        avl.agregar(4);
        avl.agregar(2);
        avl.agregar(6);
        avl.agregar(1);
        avl.agregar(3);
        avl.agregar(5);
        avl.agregar(7);
        
        assert getHeight(avl) == 2 : "Perfect balanced tree height should be 2";
        System.out.println("✓ Perfect balanced tree height test passed");
        
        // Test altura despues de rotaciones
        avl = new AVL();
        avl.inicializar();
        for (int i = 1; i <= 8; i++) {
            avl.agregar(i);
            int expectedMaxHeight = (int) Math.ceil(Math.log(i + 1) / Math.log(2)) + 1;
            assert getHeight(avl) <= expectedMaxHeight : "Height should be logarithmic after insertion " + i;
        }
        
        System.out.println("✓ Height after rotations test passed");
        
        // Test altura consistente despues de eliminaciones
        for (int i = 8; i >= 1; i--) {
            avl.eliminar(i);
            if (!avl.estaVacio()) {
                assert isBalanced(avl) : "Tree should remain balanced after deletion " + i;
                int nodeCount = countNodes(avl);
                int height = getHeight(avl);
                if (nodeCount > 0) {
                    assert height <= 1.44 * Math.log(nodeCount + 2) / Math.log(2) : "Height should be logarithmic";
                }
            }
        }
        
        System.out.println("✓ Consistent height after deletions test passed");
    }
    
    // Nuevo Test: Rendimiento y stress
    public static void testPerformanceAndStress() {
        System.out.println("\n--- Test 9: Performance and Stress ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Test insercion de muchos elementos
        long startTime = System.nanoTime();
        for (int i = 1; i <= 1000; i++) {
            avl.agregar(i);
            if (i % 100 == 0) {
                assert isBalanced(avl) : "Tree should remain balanced at " + i + " elements";
                assert getHeight(avl) <= 1.44 * Math.log(i + 2) / Math.log(2) + 1 : "Height should be logarithmic at " + i + " elements";
            }
        }
        long endTime = System.nanoTime();
        
        System.out.println("✓ Inserted 1000 elements in " + (endTime - startTime) / 1000000 + " ms");
        assert countNodes(avl) == 1000 : "Should have 1000 nodes";
        
        // Test eliminacion masiva
        startTime = System.nanoTime();
        for (int i = 500; i <= 600; i++) {
            avl.eliminar(i);
            assert isBalanced(avl) : "Tree should remain balanced after deletion " + i;
        }
        endTime = System.nanoTime();
        
        System.out.println("✓ Deleted 101 elements in " + (endTime - startTime) / 1000000 + " ms");
        assert countNodes(avl) == 899 : "Should have 899 nodes after deletions";
        
        // Test insercion intercalada
        for (int i = 1500; i <= 1600; i++) {
            avl.agregar(i);
            avl.eliminar(i - 500);
        }
        
        assert isBalanced(avl) : "Tree should remain balanced after intercalated operations";
        System.out.println("✓ Intercalated insertions and deletions test passed");
    }
    
    // Nuevo Test: Escenarios especificos de rotacion
    public static void testSpecificRotationScenarios() {
        System.out.println("\n--- Test 10: Specific Rotation Scenarios ---");
        
        // Test rotacion LL con subarboles
        ABBTDA avl = new AVL();
        avl.inicializar();
        avl.agregar(10);
        avl.agregar(5);
        avl.agregar(15);
        avl.agregar(3);
        avl.agregar(7);
        avl.agregar(1); // Deberia trigger LL rotation en nodo 5
        
        assert isBalanced(avl) : "Tree should be balanced after LL with subtrees";
        assert isBST(avl) : "Tree should maintain BST property after LL with subtrees";
        System.out.println("✓ LL rotation with subtrees test passed");
        
        // Test rotacion RR con subarboles
        avl = new AVL();
        avl.inicializar();
        avl.agregar(10);
        avl.agregar(5);
        avl.agregar(15);
        avl.agregar(12);
        avl.agregar(18);
        avl.agregar(20); // Deberia trigger RR rotation en nodo 15
        
        assert isBalanced(avl) : "Tree should be balanced after RR with subtrees";
        assert isBST(avl) : "Tree should maintain BST property after RR with subtrees";
        System.out.println("✓ RR rotation with subtrees test passed");
        
        // Test rotacion LR compleja
        avl = new AVL();
        avl.inicializar();
        avl.agregar(10);
        avl.agregar(5);
        avl.agregar(15);
        avl.agregar(3);
        avl.agregar(8);
        avl.agregar(6); // Deberia trigger LR rotation
        
        assert isBalanced(avl) : "Tree should be balanced after complex LR";
        assert isBST(avl) : "Tree should maintain BST property after complex LR";
        System.out.println("✓ Complex LR rotation test passed");
        
        // Test rotacion RL compleja
        avl = new AVL();
        avl.inicializar();
        avl.agregar(10);
        avl.agregar(5);
        avl.agregar(15);
        avl.agregar(12);
        avl.agregar(18);
        avl.agregar(14); // Deberia trigger RL rotation
        
        assert isBalanced(avl) : "Tree should be balanced after complex RL";
        assert isBST(avl) : "Tree should maintain BST property after complex RL";
        System.out.println("✓ Complex RL rotation test passed");
    }
    
    // Nuevo Test: Condiciones limite
    public static void testBoundaryConditions() {
        System.out.println("\n--- Test 11: Boundary Conditions ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Test valores extremos
        avl.agregar(Integer.MAX_VALUE);
        avl.agregar(Integer.MIN_VALUE);
        avl.agregar(0);
        
        assert isBST(avl) : "Tree should handle extreme integer values";
        assert isBalanced(avl) : "Tree should be balanced with extreme values";
        System.out.println("✓ Extreme integer values test passed");
        
        // Test eliminacion en arbol de un solo nodo
        avl = new AVL();
        avl.inicializar();
        avl.agregar(42);
        avl.eliminar(42);
        
        assert avl.estaVacio() : "Tree should be empty after deleting only node";
        System.out.println("✓ Single node deletion test passed");
        
        // Test multiples eliminaciones del mismo valor
        avl = new AVL();
        avl.inicializar();
        avl.agregar(10);
        avl.agregar(5);
        avl.agregar(15);
        
        avl.eliminar(10);
        avl.eliminar(10); // Segunda eliminacion del mismo valor
        
        assert countNodes(avl) == 2 : "Should have 2 nodes after multiple deletions of same value";
        assert isBST(avl) : "Tree should maintain BST property";
        System.out.println("✓ Multiple deletions of same value test passed");
        
        // Test arbol con altura maxima teorica
        avl = new AVL();
        avl.inicializar();
        
        // Secuencia Fibonacci para maximizar altura en AVL
        int[] fibSequence = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int val : fibSequence) {
            avl.agregar(val);
        }
        
        int height = getHeight(avl);
        int nodeCount = countNodes(avl);
        double maxTheoreticalHeight = 1.44 * Math.log(nodeCount + 2) / Math.log(2) - 0.328;
        
        assert height <= maxTheoreticalHeight + 1 : "Height should not exceed theoretical maximum";
        System.out.println("✓ Maximum theoretical height test passed");
    }
    
    // Nuevo Test: Operaciones secuenciales
    public static void testSequentialOperations() {
        System.out.println("\n--- Test 12: Sequential Operations ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Test insercion secuencial ascendente
        for (int i = 1; i <= 20; i++) {
            avl.agregar(i);
        }
        
        assert isBalanced(avl) : "Tree should be balanced after ascending insertions";
        assert isBST(avl) : "Tree should maintain BST property";
        assert getHeight(avl) <= 6 : "Height should be logarithmic for 20 nodes";
        System.out.println("✓ Ascending sequential insertion test passed");
        
        // Test eliminacion secuencial desde el medio
        for (int i = 10; i <= 15; i++) {
            avl.eliminar(i);
            assert isBalanced(avl) : "Tree should remain balanced during middle deletions";
        }
        
        System.out.println("✓ Middle range deletion test passed");
        
        // Test insercion secuencial descendente en nuevo arbol
        avl = new AVL();
        avl.inicializar();
        
        for (int i = 20; i >= 1; i--) {
            avl.agregar(i);
            assert isBalanced(avl) : "Tree should be balanced during descending insertion " + i;
        }
        
        assert getHeight(avl) <= 6 : "Height should be logarithmic for descending insertions";
        System.out.println("✓ Descending sequential insertion test passed");
    }
    
    // Nuevo Test: Operaciones aleatorias
    public static void testRandomOperations() {
        System.out.println("\n--- Test 13: Random Operations ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Seed fijo para reproducibilidad
        java.util.Random rand = new java.util.Random(12345);
        
        // Test inserciones aleatorias
        for (int i = 0; i < 100; i++) {
            int value = rand.nextInt(1000);
            avl.agregar(value);
            
            if (i % 10 == 0) {
                assert isBalanced(avl) : "Tree should be balanced after random insertion " + i;
                assert isBST(avl) : "Tree should maintain BST property after random insertion " + i;
            }
        }
        
        System.out.println("✓ Random insertions test passed");
        
        // Test eliminaciones aleatorias
        for (int i = 0; i < 50; i++) {
            int value = rand.nextInt(1000);
            avl.eliminar(value);
            
            if (!avl.estaVacio() && i % 5 == 0) {
                assert isBalanced(avl) : "Tree should be balanced after random deletion " + i;
                assert isBST(avl) : "Tree should maintain BST property after random deletion " + i;
            }
        }
        
        System.out.println("✓ Random deletions test passed");
        
        // Test operaciones mixtas aleatorias
        for (int i = 0; i < 200; i++) {
            if (rand.nextBoolean()) {
                avl.agregar(rand.nextInt(500));
            } else {
                avl.eliminar(rand.nextInt(500));
            }
            
            if (!avl.estaVacio() && i % 20 == 0) {
                assert isBalanced(avl) : "Tree should be balanced after mixed operation " + i;
            }
        }
        
        System.out.println("✓ Mixed random operations test passed");
    }
    
    // Nuevo Test: Datasets grandes
    public static void testLargeDatasets() {
        System.out.println("\n--- Test 14: Large Datasets ---");
        
        ABBTDA avl = new AVL();
        avl.inicializar();
        
        // Test con 5000 elementos
        long startTime = System.nanoTime();
        
        for (int i = 1; i <= 5000; i++) {
            avl.agregar(i);
            
            if (i % 1000 == 0) {
                assert isBalanced(avl) : "Tree should be balanced at " + i + " elements";
                int height = getHeight(avl);
                double maxExpectedHeight = 1.44 * Math.log(i + 2) / Math.log(2) + 2;
                assert height <= maxExpectedHeight : "Height should be logarithmic at " + i + " elements";
                
                System.out.println("  ✓ " + i + " elements: height=" + height + ", balanced=" + isBalanced(avl));
            }
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        
        assert countNodes(avl) == 5000 : "Should have exactly 5000 nodes";
        System.out.println("✓ Large dataset insertion completed in " + duration + " ms");
        
        // Test busqueda en dataset grande
        startTime = System.nanoTime();
        boolean found2500 = false;
        ABBTDA current = avl;
        
        // Busqueda manual del valor 2500
        while (!current.estaVacio()) {
            if (current.raiz() == 2500) {
                found2500 = true;
                break;
            } else if (2500 < current.raiz()) {
                current = current.hijoIzquierdo();
            } else {
                current = current.hijoDerecho();
            }
        }
        
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000;
        
        assert found2500 : "Should find element 2500 in large dataset";
        System.out.println("✓ Found element 2500 in " + duration + " microseconds");
        
        // Test eliminacion parcial de dataset grande
        startTime = System.nanoTime();
        
        for (int i = 2000; i <= 3000; i++) {
            avl.eliminar(i);
        }
        
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        
        assert isBalanced(avl) : "Tree should remain balanced after large deletion range";
        assert countNodes(avl) == 4000 : "Should have 4000 nodes after range deletion";
        System.out.println("✓ Range deletion (1000 elements) completed in " + duration + " ms");
    }
    
    // Helper methods for testing
    private static boolean isBalanced(ABBTDA tree) {
        if (tree.estaVacio()) return true;
        
        int leftHeight = tree.hijoIzquierdo().estaVacio() ? 0 : getHeight(tree.hijoIzquierdo()) + 1;
        int rightHeight = tree.hijoDerecho().estaVacio() ? 0 : getHeight(tree.hijoDerecho()) + 1;
        
        return Math.abs(leftHeight - rightHeight) <= 1 &&
               isBalanced(tree.hijoIzquierdo()) &&
               isBalanced(tree.hijoDerecho());
    }
    
    private static boolean isBST(ABBTDA tree) {
        return isBSTHelper(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private static boolean isBSTHelper(ABBTDA tree, int min, int max) {
        if (tree.estaVacio()) return true;
        
        int value = tree.raiz();
        return value > min && value < max &&
               isBSTHelper(tree.hijoIzquierdo(), min, value) &&
               isBSTHelper(tree.hijoDerecho(), value, max);
    }
    
    private static int getHeight(ABBTDA tree) {
        if (tree.estaVacio()) return -1;
        return 1 + Math.max(getHeight(tree.hijoIzquierdo()), getHeight(tree.hijoDerecho()));
    }
    
    private static int getBalanceFactor(ABBTDA tree) {
        if (tree.estaVacio()) return 0;
        return getHeight(tree.hijoIzquierdo()) - getHeight(tree.hijoDerecho());
    }
    
    private static void assertAllNodesBalanced(ABBTDA tree) {
        if (tree.estaVacio()) return;
        
        assert Math.abs(getBalanceFactor(tree)) <= 1 : "Node balance factor exceeded 1";
        assertAllNodesBalanced(tree.hijoIzquierdo());
        assertAllNodesBalanced(tree.hijoDerecho());
    }
    
    private static int countNodes(ABBTDA tree) {
        if (tree.estaVacio()) return 0;
        return 1 + countNodes(tree.hijoIzquierdo()) + countNodes(tree.hijoDerecho());
    }
} 