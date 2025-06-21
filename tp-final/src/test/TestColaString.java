package test;

import implementacion.ColaString;

class TestColaString {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS COLA STRING SEGÚN ENUNCIADO ===");
        
        testColaVacia();
        testAcolarUno();
        testAcolarVarios();
        testDesacolar();
        testFIFO();
        testCasosSegunEnunciado();
        
        System.out.println("\n=== TESTS COMPLETADOS SEGÚN ENUNCIADO ===");
    }
    
    static void testColaVacia() {
        System.out.println("\n--- Test: Cola Vacía ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía";
        System.out.println("✓ Cola vacía funciona según enunciado");
    }
    
    static void testAcolarUno() {
        System.out.println("\n--- Test: Acolar Un Elemento ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        // Según enunciado: períodos son año+mes concatenados
        cola.acolar("202403");
        assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
        assert "202403".equals(cola.primero()) : "ERROR: Primero debería ser '202403', pero es: " + cola.primero();
        System.out.println("✓ Acolar un elemento funciona según enunciado");
    }
    
    static void testAcolarVarios() {
        System.out.println("\n--- Test: Acolar Varios Elementos ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        // Según enunciado: períodos como strings de año+mes
        cola.acolar("202401");  // Enero 2024
        cola.acolar("202402");  // Febrero 2024
        cola.acolar("202403");  // Marzo 2024
        
        // Verificar que el primero sigue siendo el primero (FIFO)
        String primero = cola.primero();
        System.out.println("Primer elemento: " + primero);
        assert "202401".equals(primero) : "ERROR: Primero debería ser '202401', pero es: " + primero;
        
        // Verificar secuencia completa FIFO
        assert "202401".equals(cola.primero()) : "ERROR: 1er elemento incorrecto";
        cola.desacolar();
        assert "202402".equals(cola.primero()) : "ERROR: 2do elemento incorrecto";  
        cola.desacolar();
        assert "202403".equals(cola.primero()) : "ERROR: 3er elemento incorrecto";
        
        System.out.println("✓ Acolar varios elementos funciona según enunciado");
    }
    
    static void testDesacolar() {
        System.out.println("\n--- Test: Desacolar ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        cola.acolar("202404");
        assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
        
        cola.desacolar();
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía después de desacolar";
        
        System.out.println("✓ Desacolar funciona según enunciado");
    }
    
    static void testFIFO() {
        System.out.println("\n--- Test: Comportamiento FIFO según Enunciado ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        // Según enunciado: períodos en orden cronológico
        String[] periodos = {"202401", "202402", "202403", "202404"};
        
        // Acolar todos los períodos
        for (String periodo : periodos) {
            cola.acolar(periodo);
        }
        
        // Desacolar en orden FIFO (orden cronológico)
        for (String esperado : periodos) {
            assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
            String actual = cola.primero();
            assert esperado.equals(actual) : 
                String.format("ERROR: Esperado '%s', pero obtuvo '%s'", esperado, actual);
            cola.desacolar();
        }
        
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía al final";
        System.out.println("✓ Comportamiento FIFO correcto según enunciado");
    }
    
    static void testCasosSegunEnunciado() {
        System.out.println("\n--- Test: Casos según Enunciado ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        // Test robustez: desacolar cola vacía
        cola.desacolar();  // No debe romper según enunciado
        assert cola.colaVacia() : "ERROR: Cola debería seguir vacía";
        
        // Test: Nombres de campos como strings
        cola.acolar("Campo Norte");
        cola.acolar("Campo Sur");
        assert "Campo Norte".equals(cola.primero()) : "ERROR: Debería mantener nombres de campos";
        cola.desacolar();
        assert "Campo Sur".equals(cola.primero()) : "ERROR: Segundo campo incorrecto";
        
        // Test: Períodos con formato específico del enunciado
        cola.inicializarCola();
        cola.acolar("202412");  // Diciembre 2024
        cola.acolar("202501");  // Enero 2025
        cola.acolar("202502");  // Febrero 2025
        
        // Verificar formato de períodos (6 dígitos)
        while (!cola.colaVacia()) {
            String periodo = cola.primero();
            assert periodo.length() == 6 : "ERROR: Período debe tener 6 caracteres (AAAAMM)";
            assert periodo.matches("\\d{6}") : "ERROR: Período debe ser numérico";
            cola.desacolar();
        }
        
        // Test: Strings vacíos (caso límite)
        cola.acolar("");  // String vacío debe ser aceptado
        assert "".equals(cola.primero()) : "ERROR: String vacío debería ser aceptado";
        
        System.out.println("✓ Casos según enunciado funcionan correctamente");
    }
} 