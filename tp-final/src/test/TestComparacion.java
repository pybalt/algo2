package test;

class TestComparacion {
    
    public static void main(String[] args) {
        System.out.println("=== TEST COMPARACIÓN SEGÚN ENUNCIADO ===");
        
        // Test comparación de nombres de campos según enunciado
        testComparacionCampos();
        testComparacionPeriodos();
        testMetodosCorrectos();
        
        System.out.println("\n=== TESTS COMPLETADOS SEGÚN ENUNCIADO ===");
    }
    
    static void testComparacionCampos() {
        System.out.println("\n--- Test: Comparación de Nombres de Campos ---");
        
        // Según enunciado: campos de cultivo se comparan lexicográficamente
        String[] campos = {
            "Campo Norte", "Campo Sur", "Campo Este", "Campo Oeste", "Campo Central"
        };
        
        for (int i = 0; i < campos.length; i++) {
            for (int j = i + 1; j < campos.length; j++) {
                String campo1 = campos[i];
                String campo2 = campos[j];
                
                int resultado = campo1.compareToIgnoreCase(campo2);
                boolean esMenorCorrecto = (resultado < 0);
                boolean esMayorCorrecto = (resultado > 0);
                
                System.out.printf("'%s' vs '%s': resultado=%d, esMenor=%s, esMayor=%s%n",
                    campo1, campo2, resultado, esMenorCorrecto, esMayorCorrecto);
            }
        }
        
        System.out.println("✓ Comparación de campos según enunciado");
    }
    
    static void testComparacionPeriodos() {
        System.out.println("\n--- Test: Comparación de Períodos ---");
        
        // Según enunciado: períodos son año+mes concatenados como String
        String[] periodos = {"202401", "202402", "202403", "202404", "202405"};
        
        for (int i = 0; i < periodos.length - 1; i++) {
            String periodo1 = periodos[i];
            String periodo2 = periodos[i + 1];
            
            int resultado = periodo1.compareToIgnoreCase(periodo2);
            boolean cronologico = (resultado < 0);  // período anterior < período posterior
            
            System.out.printf("Período '%s' vs '%s': resultado=%d, cronológico=%s%n",
                periodo1, periodo2, resultado, cronologico);
                
            assert cronologico : "ERROR: Los períodos deberían estar en orden cronológico";
        }
        
        System.out.println("✓ Comparación de períodos según enunciado");
    }
    
    static void testMetodosCorrectos() {
        System.out.println("\n--- Test: Métodos de Comparación Correctos ---");
        
        // Demostrar los métodos correctos vs incorrectos
        String[] ejemplos = {
            "Campo A", "Campo B", "Campo C", "Campo Norte", "Campo Sur"
        };
        
        System.out.println("Comparaciones correctas (< 0 y > 0):");
        for (int i = 0; i < ejemplos.length - 1; i++) {
            String campo1 = ejemplos[i];
            String campo2 = ejemplos[i + 1];
            
            int resultado = campo1.compareToIgnoreCase(campo2);
            boolean esMenorCorrecto = (resultado < 0);  // ✅ CORRECTO
            boolean esMayorCorrecto = (resultado > 0);  // ✅ CORRECTO
            boolean esMenorIncorrecto = (resultado == -3);  // ❌ INCORRECTO
            boolean esMayorIncorrecto = (resultado == 3);   // ❌ INCORRECTO
            
            System.out.printf("  '%s' vs '%s': resultado=%d%n", campo1, campo2, resultado);
            System.out.printf("    Método correcto   - esMenor: %s, esMayor: %s%n", 
                esMenorCorrecto, esMayorCorrecto);
            System.out.printf("    Método incorrecto - esMenor: %s, esMayor: %s%n", 
                esMenorIncorrecto, esMayorIncorrecto);
            System.out.println();
        }
        
        System.out.println("✓ Métodos de comparación correctos demostrados");
    }
} 