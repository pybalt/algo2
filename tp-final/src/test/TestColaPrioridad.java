package test;

import implementacion.ColaPrioridad;

class TestColaPrioridad {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS COLA PRIORIDAD SEGÚN ENUNCIADO ===");
        
        testColaVacia();
        testInsercionSimple();
        testOrdenPrioridades();
        testPrioridadesIguales();
        testDesacolar();
        testCasosSegunEnunciado();
        
        System.out.println("\n✅ TODOS LOS TESTS COMPLETADOS SEGÚN ENUNCIADO");
    }
    
    static void testColaVacia() {
        System.out.println("\n--- Test: Cola Vacía ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía";
        System.out.println("✓ Cola vacía funciona según enunciado");
    }
    
    static void testInsercionSimple() {
        System.out.println("\n--- Test: Inserción Simple ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        // Según enunciado: precipitación (valor) con día (prioridad)
        cola.acolarPrioridad(25, 15);  // 25mm el día 15
        assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
        assert cola.primero() == 25 : "ERROR: Primer valor debería ser 25mm";
        assert cola.prioridad() == 15 : "ERROR: Prioridad debería ser día 15";
        System.out.println("✓ Inserción simple funciona según enunciado");
    }
    
    static void testOrdenPrioridades() {
        System.out.println("\n--- Test: Orden de Prioridades según Enunciado ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        // Según enunciado: días ordenados cronológicamente (menor día = mayor prioridad)
        cola.acolarPrioridad(30, 15);  // 30mm el día 15
        cola.acolarPrioridad(50, 5);   // 50mm el día 5 (mayor prioridad)
        cola.acolarPrioridad(20, 10);  // 20mm el día 10
        cola.acolarPrioridad(40, 25);  // 40mm el día 25 (menor prioridad)
        
        // Verificar orden cronológico: día 5, 10, 15, 25
        assert cola.primero() == 50 && cola.prioridad() == 5 : "ERROR: Primero debería ser 50mm del día 5";
        cola.desacolar();
        
        assert cola.primero() == 20 && cola.prioridad() == 10 : "ERROR: Segundo debería ser 20mm del día 10";
        cola.desacolar();
        
        assert cola.primero() == 30 && cola.prioridad() == 15 : "ERROR: Tercero debería ser 30mm del día 15";
        cola.desacolar();
        
        assert cola.primero() == 40 && cola.prioridad() == 25 : "ERROR: Cuarto debería ser 40mm del día 25";
        
        System.out.println("✓ Orden de prioridades correcto según enunciado");
    }
    
    static void testPrioridadesIguales() {
        System.out.println("\n--- Test: Prioridades Iguales (mismo día) ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        // Según enunciado: múltiples mediciones el mismo día (sobreescribir)
        // Pero en cola de prioridad, podemos tener múltiples valores mismo día
        cola.acolarPrioridad(100, 15);  // Primera medición día 15
        cola.acolarPrioridad(200, 15);  // Segunda medición día 15 (mismo día)
        cola.acolarPrioridad(300, 15);  // Tercera medición día 15 (mismo día)
        
        // Debería salir en orden FIFO para misma prioridad
        assert cola.primero() == 100 : "ERROR: Primero debería ser 100mm";
        cola.desacolar();
        assert cola.primero() == 200 : "ERROR: Segundo debería ser 200mm";
        cola.desacolar();
        assert cola.primero() == 300 : "ERROR: Tercero debería ser 300mm";
        
        System.out.println("✓ Prioridades iguales (mismo día) funciona según enunciado");
    }
    
    static void testDesacolar() {
        System.out.println("\n--- Test: Desacolar ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        cola.acolarPrioridad(100, 5);   // 100mm día 5
        cola.acolarPrioridad(200, 10);  // 200mm día 10
        
        assert !cola.colaVacia() : "ERROR: Cola no debería estar vacía";
        cola.desacolar();
        assert cola.primero() == 200 : "ERROR: Después de desacolar, primero debería ser 200mm";
        
        cola.desacolar();
        assert cola.colaVacia() : "ERROR: Cola debería estar vacía después de desacolar todo";
        
        // Desacolar cola vacía no debería romper según enunciado
        cola.desacolar();
        assert cola.colaVacia() : "ERROR: Cola debería seguir vacía";
        
        System.out.println("✓ Desacolar funciona según enunciado");
    }
    
    static void testCasosSegunEnunciado() {
        System.out.println("\n--- Test: Casos según Enunciado ---");
        ColaPrioridad cola = new ColaPrioridad();
        cola.inicializarCola();
        
        // Test: Días límite del mes (1-31)
        cola.acolarPrioridad(25, 1);   // Primer día del mes
        cola.acolarPrioridad(30, 31);  // Último día del mes
        cola.acolarPrioridad(20, 15);  // Día medio
        
        // Verificar orden cronológico
        assert cola.prioridad() == 1 : "ERROR: Primer día debería tener mayor prioridad";
        cola.desacolar();
        assert cola.prioridad() == 15 : "ERROR: Día 15 debería ser segundo";
        cola.desacolar();
        assert cola.prioridad() == 31 : "ERROR: Último día debería ser último";
        
        // Test: Precipitaciones extremas según enunciado
        cola.inicializarCola();
        cola.acolarPrioridad(0, 10);     // Sin precipitación
        cola.acolarPrioridad(500, 20);   // Precipitación muy alta
        cola.acolarPrioridad(25, 5);     // Precipitación normal
        
        // Verificar que acepta todos los valores
        assert cola.primero() == 25 && cola.prioridad() == 5 : "ERROR: Día 5 debería ser primero";
        cola.desacolar();
        assert cola.primero() == 0 && cola.prioridad() == 10 : "ERROR: Día 10 con 0mm debería ser segundo";
        cola.desacolar();
        assert cola.primero() == 500 && cola.prioridad() == 20 : "ERROR: Día 20 con 500mm debería ser tercero";
        
        // Test: Insertar en orden no cronológico
        cola.inicializarCola();
        cola.acolarPrioridad(100, 25);  // Día 25
        cola.acolarPrioridad(200, 5);   // Día 5 (debería ir al principio)
        cola.acolarPrioridad(150, 15);  // Día 15 (debería ir en el medio)
        
        assert cola.prioridad() == 5 : "ERROR: Día 5 debería estar al principio";
        cola.desacolar();
        assert cola.prioridad() == 15 : "ERROR: Día 15 debería estar en el medio";
        cola.desacolar();
        assert cola.prioridad() == 25 : "ERROR: Día 25 debería estar al final";
        
        System.out.println("✓ Casos según enunciado funcionan correctamente");
    }
} 