package implementacion;

class TestConjuntoString {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS CONJUNTO STRING ===");
        
        testConjuntoVacio();
        testAgregarUno();
        testAgregarDuplicados();
        testPertenece();
        testSacar();
        testElegir(); // ¡Puede detectar bugs!
        testCasosBorde();
        testCaseSensitive();
        
        System.out.println("\n=== TESTS COMPLETADOS ===");
    }
    
    static void testConjuntoVacio() {
        System.out.println("\n--- Test: Conjunto Vacío ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        assert conjunto.estaVacio() : "ERROR: Conjunto debería estar vacío";
        assert !conjunto.pertenece("test") : "ERROR: 'test' no debería pertenecer a conjunto vacío";
        System.out.println("✓ Conjunto vacío funciona");
    }
    
    static void testAgregarUno() {
        System.out.println("\n--- Test: Agregar Un Elemento ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        conjunto.agregar("Arroz");
        assert !conjunto.estaVacio() : "ERROR: Conjunto no debería estar vacío";
        assert conjunto.pertenece("Arroz") : "ERROR: 'Arroz' debería pertenecer";
        assert !conjunto.pertenece("Maíz") : "ERROR: 'Maíz' no debería pertenecer";
        System.out.println("✓ Agregar un elemento funciona");
    }
    
    static void testAgregarDuplicados() {
        System.out.println("\n--- Test: Agregar Duplicados ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        conjunto.agregar("Trigo");
        conjunto.agregar("Trigo");  // duplicado
        conjunto.agregar("Trigo");  // duplicado
        
        assert conjunto.pertenece("Trigo") : "ERROR: 'Trigo' debería estar";
        
        // Verificar que solo hay un elemento (usando elegir)
        for (int i = 0; i < 10; i++) {
            String elegido = conjunto.elegir();
            assert "Trigo".equals(elegido) : "ERROR: Elegir debería devolver siempre 'Trigo', pero devolvió: " + elegido;
        }
        
        System.out.println("✓ Duplicados manejados correctamente");
    }
    
    static void testPertenece() {
        System.out.println("\n--- Test: Pertenece ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        String[] campos = {"Arroz", "Maíz", "Trigo", "Avena", "Cebada"};
        
        // Agregar elementos
        for (String campo : campos) {
            conjunto.agregar(campo);
        }
        
        // Verificar que todos pertenecen
        for (String campo : campos) {
            assert conjunto.pertenece(campo) : "ERROR: '" + campo + "' debería pertenecer";
        }
        
        // Verificar que otros no pertenecen
        String[] noPertenecen = {"Soja", "Girasol", "Quinoa", "Centeno"};
        for (String campo : noPertenecen) {
            assert !conjunto.pertenece(campo) : "ERROR: '" + campo + "' NO debería pertenecer";
        }
        
        System.out.println("✓ Pertenece funciona correctamente");
    }
    
    static void testSacar() {
        System.out.println("\n--- Test: Sacar ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        conjunto.agregar("Campo1");
        conjunto.agregar("Campo2");
        conjunto.agregar("Campo3");
        
        // Sacar elemento del medio
        conjunto.sacar("Campo2");
        assert !conjunto.pertenece("Campo2") : "ERROR: 'Campo2' debería haber sido eliminado";
        assert conjunto.pertenece("Campo1") : "ERROR: 'Campo1' debería seguir";
        assert conjunto.pertenece("Campo3") : "ERROR: 'Campo3' debería seguir";
        
        // Sacar primer elemento (último agregado)
        conjunto.sacar("Campo3");
        assert !conjunto.pertenece("Campo3") : "ERROR: 'Campo3' debería haber sido eliminado";
        assert conjunto.pertenece("Campo1") : "ERROR: 'Campo1' debería seguir";
        
        // Sacar último elemento
        conjunto.sacar("Campo1");
        assert conjunto.estaVacio() : "ERROR: Conjunto debería estar vacío";
        
        System.out.println("✓ Sacar funciona correctamente");
    }
    
    static void testElegir() {
        System.out.println("\n--- Test: Elegir ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        // Test con un elemento
        conjunto.agregar("Único");
        String elegido = conjunto.elegir();
        assert "Único".equals(elegido) : "ERROR: Con un elemento, elegir debería devolver 'Único', pero devolvió: " + elegido;
        
        // Test con múltiples elementos
        conjunto.agregar("Alpha");
        conjunto.agregar("Beta");
        conjunto.agregar("Gamma");
        // Ahora tiene: [Gamma, Beta, Alpha, Único] (orden reverso de inserción)
        
        // Verificar que elegir devuelve elementos válidos
        boolean[] encontrados = new boolean[4]; // para Único, Alpha, Beta, Gamma
        for (int i = 0; i < 20; i++) {  // múltiples intentos
            String valor = conjunto.elegir();
            assert conjunto.pertenece(valor) : "ERROR: elegir() devolvió '" + valor + "' que no pertenece al conjunto";
            
            // Marcar elementos encontrados
            if ("Único".equals(valor)) encontrados[0] = true;
            if ("Alpha".equals(valor)) encontrados[1] = true;
            if ("Beta".equals(valor)) encontrados[2] = true; 
            if ("Gamma".equals(valor)) encontrados[3] = true;
        }
        
        System.out.println("✓ Elegir funciona (elementos válidos devueltos)");
    }
    
    static void testCasosBorde() {
        System.out.println("\n--- Test: Casos Borde ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        // Sacar de conjunto vacío
        conjunto.sacar("Inexistente");  // no debería romper
        assert conjunto.estaVacio() : "ERROR: Conjunto debería seguir vacío";
        
        // Elegir de conjunto vacío
        try {
            conjunto.elegir();
            System.out.println("⚠ Warning: elegir() en conjunto vacío no lanzó excepción");
        } catch (Exception e) {
            System.out.println("⚠ elegir() en conjunto vacío lanzó: " + e.getClass().getSimpleName());
        }
        
        // Strings vacíos y especiales
        conjunto.agregar("");  // string vacío
        assert conjunto.pertenece("") : "ERROR: String vacío debería poder agregarse";
        
        conjunto.agregar(null);  // null (puede causar problemas)
        // Note: esto puede fallar dependiendo de la implementación
        
        System.out.println("✓ Casos borde manejados");
    }
    
    static void testCaseSensitive() {
        System.out.println("\n--- Test: Case Sensitive ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        conjunto.agregar("Arroz");
        conjunto.agregar("ARROZ");
        conjunto.agregar("arroz");
        
        // Deberían ser elementos diferentes (case sensitive)
        assert conjunto.pertenece("Arroz") : "ERROR: 'Arroz' debería pertenecer";
        assert conjunto.pertenece("ARROZ") : "ERROR: 'ARROZ' debería pertenecer";
        assert conjunto.pertenece("arroz") : "ERROR: 'arroz' debería pertenecer";
        assert !conjunto.pertenece("ArRoZ") : "ERROR: 'ArRoZ' no debería pertenecer";
        
        System.out.println("✓ Case sensitivity funciona correctamente");
    }
    
    // Helper para debug
    static void imprimirConjunto(ConjuntoString conjunto) {
        System.out.print("Conjunto: {");
        if (!conjunto.estaVacio()) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.print("'" + conjunto.elegir() + "'");
                    if (i < 4) System.out.print(", ");
                } catch (Exception e) {
                    break;
                }
            }
        }
        System.out.println("}");
    }
} 