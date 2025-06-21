package test;

import implementacion.Conjunto;

class TestConjunto {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS CONJUNTO SEGÚN ENUNCIADO ===");
        
        testConjuntoVacio();
        testAgregarUno();
        testAgregarDuplicados();
        testPertenece();
        testSacar();
        testElegir();
        testCasosSegunEnunciado();
        
        System.out.println("\n=== TESTS COMPLETADOS SEGÚN ENUNCIADO ===");
    }
    
    static void testConjuntoVacio() {
        System.out.println("\n--- Test: Conjunto Vacío ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        assert conjunto.estaVacio() : "ERROR: Conjunto debería estar vacío";
        assert !conjunto.pertenece(15) : "ERROR: Día 15 no debería pertenecer a conjunto vacío";
        System.out.println("✓ Conjunto vacío funciona según enunciado");
    }
    
    static void testAgregarUno() {
        System.out.println("\n--- Test: Agregar Un Elemento ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        // Según enunciado: días van de 1 a 31
        conjunto.agregar(15);
        assert !conjunto.estaVacio() : "ERROR: Conjunto no debería estar vacío";
        assert conjunto.pertenece(15) : "ERROR: Día 15 debería pertenecer";
        assert !conjunto.pertenece(20) : "ERROR: Día 20 no debería pertenecer";
        System.out.println("✓ Agregar un elemento funciona según enunciado");
    }
    
    static void testAgregarDuplicados() {
        System.out.println("\n--- Test: Agregar Duplicados según Enunciado ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        // Según enunciado: mismo día múltiples veces debe sobreescribir (no duplicar)
        conjunto.agregar(10);
        conjunto.agregar(10);  // duplicado - debe ser ignorado
        conjunto.agregar(10);  // duplicado - debe ser ignorado
        
        assert conjunto.pertenece(10) : "ERROR: Día 10 debería estar";
        
        // Verificar que solo hay un elemento
        int elegido = conjunto.elegir();
        assert elegido == 10 : "ERROR: Elegir debería devolver 10, pero devolvió: " + elegido;
        
        System.out.println("✓ Duplicados manejados correctamente según enunciado");
    }
    
    static void testPertenece() {
        System.out.println("\n--- Test: Pertenece según Enunciado ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        // Según enunciado: días válidos para precipitaciones
        int[] diasValidos = {1, 15, 28, 30, 31};  // Días típicos de medición
        
        // Agregar elementos
        for (int dia : diasValidos) {
            conjunto.agregar(dia);
        }
        
        // Verificar que todos pertenecen
        for (int dia : diasValidos) {
            assert conjunto.pertenece(dia) : "ERROR: Día " + dia + " debería pertenecer";
        }
        
        // Verificar que otros no pertenecen
        int[] diasNoAgregados = {5, 12, 22, 25};
        for (int dia : diasNoAgregados) {
            assert !conjunto.pertenece(dia) : "ERROR: Día " + dia + " NO debería pertenecer";
        }
        
        System.out.println("✓ Pertenece funciona correctamente según enunciado");
    }
    
    static void testSacar() {
        System.out.println("\n--- Test: Sacar según Enunciado ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        conjunto.agregar(10);
        conjunto.agregar(20);
        conjunto.agregar(30);
        
        // Sacar elemento
        conjunto.sacar(20);
        assert !conjunto.pertenece(20) : "ERROR: Día 20 debería haber sido eliminado";
        assert conjunto.pertenece(10) : "ERROR: Día 10 debería seguir";
        assert conjunto.pertenece(30) : "ERROR: Día 30 debería seguir";
        
        // Sacar elemento inexistente - debe ser ignorado según enunciado
        conjunto.sacar(99);  // Día inexistente - no debe romper
        assert conjunto.pertenece(10) : "ERROR: Día 10 debería seguir después de sacar inexistente";
        assert conjunto.pertenece(30) : "ERROR: Día 30 debería seguir después de sacar inexistente";
        
        System.out.println("✓ Sacar funciona correctamente según enunciado");
    }
    
    static void testElegir() {
        System.out.println("\n--- Test: Elegir según Enunciado ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        // Test con un elemento
        conjunto.agregar(15);
        int elegido = conjunto.elegir();
        assert elegido == 15 : "ERROR: Con un elemento, elegir debería devolver 15, pero devolvió: " + elegido;
        
        // Test con múltiples elementos (días de precipitación)
        conjunto.agregar(1);   // Primer día del mes
        conjunto.agregar(15);  // Día medio
        conjunto.agregar(31);  // Último día del mes
        
        // Verificar que elegir devuelve elementos válidos del conjunto
        for (int i = 0; i < 10; i++) {
            int valor = conjunto.elegir();
            assert conjunto.pertenece(valor) : "ERROR: elegir() devolvió " + valor + " que no pertenece al conjunto";
            // Verificar que está en rango válido de días
            assert valor >= 1 && valor <= 31 : "ERROR: Día devuelto fuera de rango válido: " + valor;
        }
        
        System.out.println("✓ Elegir funciona según enunciado");
    }
    
    static void testCasosSegunEnunciado() {
        System.out.println("\n--- Test: Casos según Enunciado ---");
        Conjunto conjunto = new Conjunto();
        conjunto.inicializar();
        
        // Test robustez: operaciones en conjunto vacío
        conjunto.sacar(999);  // No debe romper
        assert conjunto.estaVacio() : "ERROR: Conjunto debería seguir vacío";
        
        // Test: Días límite según enunciado (1-31)
        conjunto.agregar(1);   // Día mínimo válido
        conjunto.agregar(31);  // Día máximo válido
        assert conjunto.pertenece(1) : "ERROR: Día 1 debería ser válido";
        assert conjunto.pertenece(31) : "ERROR: Día 31 debería ser válido";
        
        // Test: Días fuera de rango típico (pero permitidos como enteros)
        conjunto.agregar(0);    // Día 0 (técnicamente inválido pero el conjunto debe aceptarlo)
        conjunto.agregar(32);   // Día 32 (técnicamente inválido pero el conjunto debe aceptarlo)
        assert conjunto.pertenece(0) : "ERROR: Conjunto debe aceptar cualquier entero";
        assert conjunto.pertenece(32) : "ERROR: Conjunto debe aceptar cualquier entero";
        
        // Test: Agregar-sacar-agregar mismo día
        conjunto.agregar(15);
        conjunto.sacar(15);
        conjunto.agregar(15);
        assert conjunto.pertenece(15) : "ERROR: Día 15 debería estar después de agregar-sacar-agregar";
        
        // Test: Precipitaciones como valores (aunque van en diccionario separado)
        conjunto.agregar(100);  // 100mm de precipitación
        conjunto.agregar(250);  // 250mm de precipitación
        assert conjunto.pertenece(100) : "ERROR: Valor 100 debería estar";
        assert conjunto.pertenece(250) : "ERROR: Valor 250 debería estar";
        
        System.out.println("✓ Casos según enunciado funcionan correctamente");
    }
} 