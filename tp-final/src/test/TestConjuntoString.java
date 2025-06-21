package test;

import implementacion.ConjuntoString;

class TestConjuntoString {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS CONJUNTO STRING SEGÚN ENUNCIADO ===");
        
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
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        assert conjunto.estaVacio() : "ERROR: Conjunto debería estar vacío";
        assert !conjunto.pertenece("test") : "ERROR: 'test' no debería pertenecer a conjunto vacío";
        System.out.println("✓ Conjunto vacío funciona según enunciado");
    }
    
    static void testAgregarUno() {
        System.out.println("\n--- Test: Agregar Un Elemento ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        conjunto.agregar("Campo Norte");
        assert !conjunto.estaVacio() : "ERROR: Conjunto no debería estar vacío";
        assert conjunto.pertenece("Campo Norte") : "ERROR: 'Campo Norte' debería pertenecer";
        assert !conjunto.pertenece("Campo Sur") : "ERROR: 'Campo Sur' no debería pertenecer";
        System.out.println("✓ Agregar un elemento funciona según enunciado");
    }
    
    static void testAgregarDuplicados() {
        System.out.println("\n--- Test: Agregar Duplicados según Enunciado ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        conjunto.agregar("Campo Central");
        conjunto.agregar("Campo Central");  // duplicado - debe ser ignorado
        conjunto.agregar("Campo Central");  // duplicado - debe ser ignorado
        
        assert conjunto.pertenece("Campo Central") : "ERROR: 'Campo Central' debería estar";
        
        // Verificar que solo hay un elemento
        String elegido = conjunto.elegir();
        assert "Campo Central".equals(elegido) : "ERROR: Elegir debería devolver 'Campo Central', pero devolvió: " + elegido;
        
        System.out.println("✓ Duplicados manejados correctamente según enunciado");
    }
    
    static void testPertenece() {
        System.out.println("\n--- Test: Pertenece según Enunciado ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        // Usar nombres de campos como especifica el enunciado
        String[] campos = {"Campo Norte", "Campo Sur", "Campo Este", "Campo Oeste", "Campo Central"};
        
        // Agregar elementos
        for (String campo : campos) {
            conjunto.agregar(campo);
        }
        
        // Verificar que todos pertenecen
        for (String campo : campos) {
            assert conjunto.pertenece(campo) : "ERROR: '" + campo + "' debería pertenecer";
        }
        
        // Verificar que otros no pertenecen
        String[] noPertenecen = {"Campo Inexistente", "Otro Campo", "Campo Vacío"};
        for (String campo : noPertenecen) {
            assert !conjunto.pertenece(campo) : "ERROR: '" + campo + "' NO debería pertenecer";
        }
        
        System.out.println("✓ Pertenece funciona correctamente según enunciado");
    }
    
    static void testSacar() {
        System.out.println("\n--- Test: Sacar según Enunciado ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        conjunto.agregar("Campo A");
        conjunto.agregar("Campo B");
        conjunto.agregar("Campo C");
        
        // Sacar elemento
        conjunto.sacar("Campo B");
        assert !conjunto.pertenece("Campo B") : "ERROR: 'Campo B' debería haber sido eliminado";
        assert conjunto.pertenece("Campo A") : "ERROR: 'Campo A' debería seguir";
        assert conjunto.pertenece("Campo C") : "ERROR: 'Campo C' debería seguir";
        
        // Sacar elemento inexistente - debe ser ignorado según enunciado
        conjunto.sacar("Campo Inexistente");  // No debe romper
        assert conjunto.pertenece("Campo A") : "ERROR: 'Campo A' debería seguir después de sacar inexistente";
        assert conjunto.pertenece("Campo C") : "ERROR: 'Campo C' debería seguir después de sacar inexistente";
        
        System.out.println("✓ Sacar funciona correctamente según enunciado");
    }
    
    static void testElegir() {
        System.out.println("\n--- Test: Elegir según Enunciado ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        // Test con un elemento
        conjunto.agregar("Campo Único");
        String elegido = conjunto.elegir();
        assert "Campo Único".equals(elegido) : "ERROR: Con un elemento, elegir debería devolver 'Campo Único', pero devolvió: " + elegido;
        
        // Test con múltiples elementos
        conjunto.agregar("Campo Alpha");
        conjunto.agregar("Campo Beta");
        conjunto.agregar("Campo Gamma");
        
        // Verificar que elegir devuelve elementos válidos del conjunto
        for (int i = 0; i < 10; i++) {
            String valor = conjunto.elegir();
            assert conjunto.pertenece(valor) : "ERROR: elegir() devolvió '" + valor + "' que no pertenece al conjunto";
        }
        
        System.out.println("✓ Elegir funciona según enunciado");
    }
    
    static void testCasosSegunEnunciado() {
        System.out.println("\n--- Test: Casos según Enunciado ---");
        ConjuntoString conjunto = new ConjuntoString();
        conjunto.inicializar();
        
        // Test robustez: operaciones en conjunto vacío
        conjunto.sacar("Campo Inexistente");  // No debe romper
        assert conjunto.estaVacio() : "ERROR: Conjunto debería seguir vacío";
        
        // Test: Nombres de campos con espacios (como especifica el enunciado)
        conjunto.agregar("Campo con Espacios");
        assert conjunto.pertenece("Campo con Espacios") : "ERROR: Campos con espacios deberían funcionar";
        
        // Test: Case sensitivity (importante para nombres de campos)
        conjunto.agregar("Campo Norte");
        conjunto.agregar("CAMPO NORTE");  // Diferente por case sensitivity
        assert conjunto.pertenece("Campo Norte") : "ERROR: 'Campo Norte' debería pertenecer";
        assert conjunto.pertenece("CAMPO NORTE") : "ERROR: 'CAMPO NORTE' debería pertenecer";
        assert !conjunto.pertenece("campo norte") : "ERROR: 'campo norte' no debería pertenecer";
        
        // Test: Agregar-sacar-agregar mismo elemento
        conjunto.agregar("Campo Test");
        conjunto.sacar("Campo Test");
        conjunto.agregar("Campo Test");
        assert conjunto.pertenece("Campo Test") : "ERROR: Campo Test debería estar después de agregar-sacar-agregar";
        
        System.out.println("✓ Casos según enunciado funcionan correctamente");
    }
} 