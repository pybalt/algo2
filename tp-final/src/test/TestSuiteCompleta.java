package test;

public class TestSuiteCompleta {
    
    public static void main(String[] args) {
        System.out.println("=== SUITE COMPLETA DE TESTS ===");
        System.out.println("Ejecutando todos los tests de validación y funcionalidad...\n");
        
        boolean allTestsPassed = true;
        
        try {
            // Test 1: Validación de implementación contra bootstrap
            System.out.println("1. VALIDACIÓN CONTRA BOOTSTRAP");
            System.out.println("=====================================");
            TestValidacionCompleta.main(new String[]{});
            System.out.println("✅ Validación contra bootstrap: PASSED\n");
            
        } catch (Exception e) {
            System.err.println("❌ Validación contra bootstrap: FAILED");
            System.err.println("Error: " + e.getMessage());
            allTestsPassed = false;
        }
        
        try {
            // Test 2: Validación de firmas de métodos
            System.out.println("2. VALIDACIÓN DE FIRMAS DE MÉTODOS");
            System.out.println("=====================================");
            TestFirmasMetodos.main(new String[]{});
            System.out.println("✅ Validación de firmas: PASSED\n");
            
        } catch (Exception e) {
            System.err.println("❌ Validación de firmas: FAILED");
            System.err.println("Error: " + e.getMessage());
            allTestsPassed = false;
        }
        
        try {
            // Test 3: Tests funcionales de algoritmos
            System.out.println("3. TESTS FUNCIONALES DE ALGORITMOS");
            System.out.println("=====================================");
            TestAlgoritmos.main(new String[]{});
            System.out.println("✅ Tests funcionales: PASSED\n");
            
        } catch (Exception e) {
            System.err.println("❌ Tests funcionales: FAILED");
            System.err.println("Error: " + e.getMessage());
            allTestsPassed = false;
        }
        
        try {
            // Test 4: Tests de árbol de precipitaciones
            System.out.println("4. TESTS DE ÁRBOL DE PRECIPITACIONES");
            System.out.println("=====================================");
            TestArbolPrecipitaciones.main(new String[]{});
            System.out.println("✅ Tests de árbol: PASSED\n");
            
        } catch (Exception e) {
            System.err.println("❌ Tests de árbol: FAILED");
            System.err.println("Error: " + e.getMessage());
            allTestsPassed = false;
        }
        
        try {
            // Test 5: Tests de estructuras de datos
            System.out.println("5. TESTS DE ESTRUCTURAS DE DATOS");
            System.out.println("=====================================");
            TestConjunto.main(new String[]{});
            TestConjuntoString.main(new String[]{});
            TestColaPrioridad.main(new String[]{});
            TestColaString.main(new String[]{});
            System.out.println("✅ Tests de estructuras: PASSED\n");
            
        } catch (Exception e) {
            System.err.println("❌ Tests de estructuras: FAILED");
            System.err.println("Error: " + e.getMessage());
            allTestsPassed = false;
        }
        
        // Resumen final
        System.out.println("=== RESUMEN FINAL ===");
        if (allTestsPassed) {
            System.out.println("🎉 TODOS LOS TESTS PASARON EXITOSAMENTE");
            System.out.println("\n✅ Implementación validada contra bootstrap");
            System.out.println("✅ Firmas de métodos correctas");
            System.out.println("✅ Funcionalidad implementada correctamente");
            System.out.println("✅ Estructuras de datos funcionando");
            System.out.println("✅ Árbol de precipitaciones operativo");
            System.out.println("\n🚀 LA IMPLEMENTACIÓN ESTÁ LISTA PARA ENTREGA");
        } else {
            System.err.println("❌ ALGUNOS TESTS FALLARON");
            System.err.println("Revisar los errores anteriores antes de la entrega.");
            System.exit(1);
        }
    }
} 