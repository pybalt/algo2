package implementacion;

class TestComparacion {
    
    public static void main(String[] args) {
        System.out.println("=== TEST COMPARETO ===");
        
        // Casos que devuelven exactamente -3
        System.out.println("Casos que devuelven -3:");
        testComparacion("A", "D");
        testComparacion("B", "E"); 
        testComparacion("a", "d");
        testComparacion("Casa", "Fosa");  // C vs F
        
        // Casos con campos reales
        System.out.println("\nCasos con campos reales:");
        testComparacion("Arroz", "Maíz");
        testComparacion("Trigo", "Avena");
        testComparacion("Cebada", "Maíz");
        
        // Test de los métodos actuales (incorrectos)
        System.out.println("\nTest métodos actuales:");
        testMetodosActuales();
    }
    
    static void testComparacion(String s1, String s2) {
        int resultado = s1.compareToIgnoreCase(s2);
        System.out.printf("'%s' vs '%s' = %d%s\n", 
            s1, s2, resultado, 
            (resultado == -3) ? " ✅ (sería true)" : " ❌ (sería false)");
    }
    
    static void testMetodosActuales() {
        // Simular los métodos incorrectos
        String[] campos = {"Arroz", "Maíz", "Trigo", "Avena", "Casa", "Fosa"};
        
        for (int i = 0; i < campos.length; i++) {
            for (int j = i + 1; j < campos.length; j++) {
                String v1 = campos[i];
                String v2 = campos[j];
                
                int resultado = v1.compareToIgnoreCase(v2);
                boolean esMenorActual = (resultado == -3);
                boolean esMayorActual = (resultado == 3);
                boolean esMenorCorrecto = (resultado < 0);
                boolean esMayorCorrecto = (resultado > 0);
                
                System.out.printf("'%s' vs '%s': actual=%d, esMenor=%s (debería=%s), esMayor=%s (debería=%s)\n",
                    v1, v2, resultado, 
                    esMenorActual, esMenorCorrecto,
                    esMayorActual, esMayorCorrecto);
            }
        }
    }
} 