package implementacion;

class TestColaPrioridad {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS COLA PRIORIDAD ===");
        
        testColaVacia();
        testInsercionSimple();
        testOrdenPrioridades();
        testPrioridadesIguales();
        testDesacolar();
        testCasosBorde();
        
        System.out.println("\n✅ TODOS LOS TESTS COMPLETADOS");
    }
    
    static void testColaVacia() {
        System.out.println("\n--- Test: Cola Vacía ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía";
        System.out.println("✓ Cola vacía funciona");
    }
    
    static void testInsercionSimple() {
        System.out.println("\n--- Test: Inserción Simple ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        cola.acolarPrioridad(100, 5);
        assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
        assert cola.primero() == 100 : "ERROR: Primer valor debería ser 100";
        assert cola.prioridad() == 5 : "ERROR: Prioridad debería ser 5";
        System.out.println("✓ Inserción simple funciona");
    }
    
    static void testOrdenPrioridades() {
        System.out.println("\n--- Test: Orden de Prioridades ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        // Insertar: menor prioridad = mayor importancia
        cola.acolarPrioridad(300, 3);  // prioridad media
        cola.acolarPrioridad(100, 1);  // prioridad alta (menor número)
        cola.acolarPrioridad(200, 2);  // prioridad media-alta
        cola.acolarPrioridad(400, 4);  // prioridad baja
        
        // Verificar orden: 1, 2, 3, 4
        assert cola.primero() == 100 && cola.prioridad() == 1 : "ERROR: Primero debería ser valor=100, prioridad=1";
        cola.desacolar();
        
        assert cola.primero() == 200 && cola.prioridad() == 2 : "ERROR: Segundo debería ser valor=200, prioridad=2";
        cola.desacolar();
        
        assert cola.primero() == 300 && cola.prioridad() == 3 : "ERROR: Tercero debería ser valor=300, prioridad=3";
        cola.desacolar();
        
        assert cola.primero() == 400 && cola.prioridad() == 4 : "ERROR: Cuarto debería ser valor=400, prioridad=4";
        
        System.out.println("✓ Orden de prioridades correcto");
    }
    
    static void testPrioridadesIguales() {
        System.out.println("\n--- Test: Prioridades Iguales (FIFO) ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        cola.acolarPrioridad(100, 2);
        cola.acolarPrioridad(200, 2);  // misma prioridad
        cola.acolarPrioridad(300, 2);  // misma prioridad
        
        // Debería salir en orden FIFO para misma prioridad
        assert cola.primero() == 100 : "ERROR: Primero debería ser 100";
        cola.desacolar();
        assert cola.primero() == 200 : "ERROR: Segundo debería ser 200";
        cola.desacolar();
        assert cola.primero() == 300 : "ERROR: Tercero debería ser 300";
        
        System.out.println("✓ Prioridades iguales (FIFO) funciona");
    }
    
    static void testDesacolar() {
        System.out.println("\n--- Test: Desacolar ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        cola.acolarPrioridad(100, 1);
        cola.acolarPrioridad(200, 2);
        
        assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
        cola.desacolar();
        assert cola.primero() == 200 : "ERROR: Después de desacolar, primero debería ser 200";
        
        cola.desacolar();
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía después de desacolar todo";
        
        // Desacolar cola vacía no debería romper
        cola.desacolar();
        assert cola.colaVacia() : "ERROR: Cola debería seguir vacía";
        
        System.out.println("✓ Desacolar funciona");
    }
    
    static void testCasosBorde() {
        System.out.println("\n--- Test: Casos Borde ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        // Test: Insertar al principio (nueva prioridad mínima)
        cola.acolarPrioridad(200, 5);
        cola.acolarPrioridad(100, 1);  // debería ir al principio
        assert cola.primero() == 100 : "ERROR: Nueva prioridad mínima debería ir al principio";
        
        // Test: Insertar al final
        cola.acolarPrioridad(300, 10);  // debería ir al final
        cola.desacolar(); // quitar 100
        cola.desacolar(); // quitar 200
        assert cola.primero() == 300 : "ERROR: Último insertado debería estar al final";
        
        System.out.println("✓ Casos borde funcionan");
    }
    
    // Método helper para debug
    static void imprimirCola(ColaPrioridad cola) {
        System.out.print("Cola: ");
        ColaPrioridad temp = new ColaPrioridad();
        temp.inicializarCola();
        
        while (!cola.colaVacia()) {
            int valor = cola.primero();
            int prioridad = cola.prioridad();
            System.out.print("[" + valor + ":" + prioridad + "] ");
            temp.acolarPrioridad(valor, prioridad);
            cola.desacolar();
        }
        
        // Restaurar cola original
        while (!temp.colaVacia()) {
            cola.acolarPrioridad(temp.primero(), temp.prioridad());
            temp.desacolar();
        }
        System.out.println();
    }
} 