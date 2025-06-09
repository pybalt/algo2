package implementacion;

class TestConjunto {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS CONJUNTO ===");
        
        testConjuntoVacio();
        testAgregarUno();
        testAgregarDuplicados();
        testPertenece();
        testSacar();
        testElegir(); // ¡Este detectará BUGS críticos!
        testCasosBorde();
        
        System.out.println("\n=== TESTS COMPLETADOS ===");
    }
    
    static void testConjuntoVacio() {
        System.out.println("\n--- Test: Conjunto Vacío ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        assert conjunto.estaVacio() : "ERROR: Conjunto debería estar vacío";
        assert !conjunto.pertenece(5) : "ERROR: Elemento 5 no debería pertenecer a conjunto vacío";
        System.out.println("✓ Conjunto vacío funciona");
    }
    
    static void testAgregarUno() {
        System.out.println("\n--- Test: Agregar Un Elemento ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        conjunto.agregar(10);
        assert !conjunto.estaVacio() : "ERROR: Conjunto no debería estar vacío";
        assert conjunto.pertenece(10) : "ERROR: Elemento 10 debería pertenecer";
        assert !conjunto.pertenece(20) : "ERROR: Elemento 20 no debería pertenecer";
        System.out.println("✓ Agregar un elemento funciona");
    }
    
    static void testAgregarDuplicados() {
        System.out.println("\n--- Test: Agregar Duplicados ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        conjunto.agregar(5);
        conjunto.agregar(5);  // duplicado
        conjunto.agregar(5);  // duplicado
        
        assert conjunto.pertenece(5) : "ERROR: Elemento 5 debería estar";
        
        // Verificar que solo hay un elemento (contando con elegir)
        for (int i = 0; i < 10; i++) {
            int elegido = conjunto.elegir();
            assert elegido == 5 : "ERROR: Elegir debería devolver siempre 5, pero devolvió: " + elegido;
        }
        
        System.out.println("✓ Duplicados manejados correctamente");
    }
    
    static void testPertenece() {
        System.out.println("\n--- Test: Pertenece ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        int[] elementos = {10, 20, 30, 40, 50};
        
        // Agregar elementos
        for (int elem : elementos) {
            conjunto.agregar(elem);
        }
        
        // Verificar que todos pertenecen
        for (int elem : elementos) {
            assert conjunto.pertenece(elem) : "ERROR: Elemento " + elem + " debería pertenecer";
        }
        
        // Verificar que otros no pertenecen
        int[] noPertenecen = {5, 15, 25, 35, 45, 55};
        for (int elem : noPertenecen) {
            assert !conjunto.pertenece(elem) : "ERROR: Elemento " + elem + " NO debería pertenecer";
        }
        
        System.out.println("✓ Pertenece funciona correctamente");
    }
    
    static void testSacar() {
        System.out.println("\n--- Test: Sacar ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        conjunto.agregar(10);
        conjunto.agregar(20);
        conjunto.agregar(30);
        
        // Sacar elemento del medio
        conjunto.sacar(20);
        assert !conjunto.pertenece(20) : "ERROR: Elemento 20 debería haber sido eliminado";
        assert conjunto.pertenece(10) : "ERROR: Elemento 10 debería seguir";
        assert conjunto.pertenece(30) : "ERROR: Elemento 30 debería seguir";
        
        // Sacar primer elemento
        conjunto.sacar(30);  // último agregado = primero
        assert !conjunto.pertenece(30) : "ERROR: Elemento 30 debería haber sido eliminado";
        assert conjunto.pertenece(10) : "ERROR: Elemento 10 debería seguir";
        
        // Sacar último elemento
        conjunto.sacar(10);
        assert conjunto.estaVacio() : "ERROR: Conjunto debería estar vacío";
        
        System.out.println("✓ Sacar funciona correctamente");
    }
    
    static void testElegir() {
        System.out.println("\n--- Test: Elegir (¡CRÍTICO!) ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        // Test con un elemento
        conjunto.agregar(42);
        int elegido = conjunto.elegir();
        assert elegido == 42 : "ERROR: Con un elemento, elegir debería devolver 42, pero devolvió: " + elegido;
        
        // Test con múltiples elementos
        conjunto.agregar(10);
        conjunto.agregar(20);
        conjunto.agregar(30);
        // Ahora tiene: [30, 20, 10, 42] (orden de inserción reverso)
        
        // Verificar que elegir devuelve elementos válidos
        boolean[] encontrados = new boolean[4]; // para 10, 20, 30, 42
        for (int i = 0; i < 20; i++) {  // múltiples intentos
            int valor = conjunto.elegir();
            assert conjunto.pertenece(valor) : "ERROR: elegir() devolvió " + valor + " que no pertenece al conjunto";
            
            // Marcar elementos encontrados
            if (valor == 10) encontrados[0] = true;
            if (valor == 20) encontrados[1] = true; 
            if (valor == 30) encontrados[2] = true;
            if (valor == 42) encontrados[3] = true;
        }
        
        System.out.println("✓ Elegir funciona (elementos válidos devueltos)");
    }
    
    static void testCasosBorde() {
        System.out.println("\n--- Test: Casos Borde ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        // Sacar de conjunto vacío
        conjunto.sacar(999);  // no debería romper
        assert conjunto.estaVacio() : "ERROR: Conjunto debería seguir vacío";
        
        // Elegir de conjunto vacío
        try {
            conjunto.elegir();
            System.out.println("⚠ Warning: elegir() en conjunto vacío no lanzó excepción");
        } catch (Exception e) {
            System.out.println("⚠ elegir() en conjunto vacío lanzó: " + e.getClass().getSimpleName());
        }
        
        // Agregar, sacar, agregar mismo elemento
        conjunto.agregar(100);
        conjunto.sacar(100);
        conjunto.agregar(100);
        assert conjunto.pertenece(100) : "ERROR: Elemento 100 debería estar después de agregar-sacar-agregar";
        
        System.out.println("✓ Casos borde manejados");
    }
    
    // Helper para debug
    static void imprimirConjunto(Conjunto conjunto) {
        System.out.print("Conjunto: {");
        // No podemos iterar fácilmente, pero podemos usar elegir() múltiples veces
        if (!conjunto.estaVacio()) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.print(conjunto.elegir());
                    if (i < 4) System.out.print(", ");
                } catch (Exception e) {
                    break;
                }
            }
        }
        System.out.println("}");
    }
} 