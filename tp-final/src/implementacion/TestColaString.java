package implementacion;

class TestColaString {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS COLA STRING ===");
        
        testColaVacia();
        testAcolarUno();
        testAcolarVarios(); // ¡Este detectará el BUG!
        testDesacolar();
        testFIFO();
        testCasosBorde();
        
        System.out.println("\n=== TESTS COMPLETADOS ===");
    }
    
    static void testColaVacia() {
        System.out.println("\n--- Test: Cola Vacía ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía";
        System.out.println("✓ Cola vacía funciona");
    }
    
    static void testAcolarUno() {
        System.out.println("\n--- Test: Acolar Un Elemento ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        cola.acolar("Arroz");
        assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
        assert "arroz".equals(cola.primero()) : "ERROR: Primero debería ser 'arroz', pero es: " + cola.primero();
        System.out.println("✓ Acolar un elemento funciona");
    }
    
    static void testAcolarVarios() {
        System.out.println("\n--- Test: Acolar Varios Elementos ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        cola.acolar("Primero");
        cola.acolar("Segundo");
        cola.acolar("Tercero");
        
        // Verificar que el primero sigue siendo el primero (FIFO)
        String primero = cola.primero();
        System.out.println("Primer elemento: " + primero);
        assert "primero".equals(primero) : "ERROR: Primero debería ser 'primero', pero es: " + primero;
        
        // Verificar secuencia completa
        assert "primero".equals(cola.primero()) : "ERROR: 1er elemento incorrecto";
        cola.desacolar();
        assert "segundo".equals(cola.primero()) : "ERROR: 2do elemento incorrecto";  
        cola.desacolar();
        assert "tercero".equals(cola.primero()) : "ERROR: 3er elemento incorrecto";
        
        System.out.println("✓ Acolar varios elementos funciona");
    }
    
    static void testDesacolar() {
        System.out.println("\n--- Test: Desacolar ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        cola.acolar("Único");
        assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
        
        cola.desacolar();
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía después de desacolar";
        
        System.out.println("✓ Desacolar funciona");
    }
    
    static void testFIFO() {
        System.out.println("\n--- Test: Comportamiento FIFO ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        String[] elementos = {"A", "B", "C", "D"};
        
        // Acolar todos
        for (String elem : elementos) {
            cola.acolar(elem);
        }
        
        // Desacolar en orden FIFO
        for (String esperado : elementos) {
            assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
            String actual = cola.primero();
            assert esperado.toLowerCase().equals(actual) : 
                String.format("ERROR: Esperado '%s', pero obtuvo '%s'", esperado.toLowerCase(), actual);
            cola.desacolar();
        }
        
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía al final";
        System.out.println("✓ Comportamiento FIFO correcto");
    }
    
    static void testCasosBorde() {
        System.out.println("\n--- Test: Casos Borde ---");
        ColaString cola = new ColaString();
        cola.inicializarCola();
        
        // Test: Desacolar cola vacía (no debería romper)
        try {
            cola.desacolar();
            System.out.println("⚠ Warning: Desacolar cola vacía no lanzó excepción");
        } catch (Exception e) {
            System.out.println("⚠ Desacolar cola vacía lanzó: " + e.getClass().getSimpleName());
        }
        
        // Test: Primero() en cola vacía
        try {
            String resultado = cola.primero();
            System.out.println("⚠ Warning: primero() en cola vacía devolvió: " + resultado);
        } catch (Exception e) {
            System.out.println("⚠ primero() en cola vacía lanzó: " + e.getClass().getSimpleName());
        }
        
        // Test: Strings con diferentes casos
        cola.inicializarCola();
        cola.acolar("MaYuScUlAs");
        assert "mayusculas".equals(cola.primero()) : "ERROR: No convirtió a minúsculas correctamente";
        
        System.out.println("✓ Casos borde manejados");
    }
    
    // Método helper para debug
    static void imprimirCola(ColaString cola) {
        System.out.print("Cola: [");
        ColaString temp = new ColaString();
        temp.inicializarCola();
        
        boolean primero = true;
        while (!cola.colaVacia()) {
            if (!primero) System.out.print(", ");
            String valor = cola.primero();
            System.out.print("'" + valor + "'");
            temp.acolar(valor);
            cola.desacolar();
            primero = false;
        }
        System.out.println("]");
        
        // Restaurar cola original
        while (!temp.colaVacia()) {
            cola.acolar(temp.primero());
            temp.desacolar();
        }
    }
} 