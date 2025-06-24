package test;

import algoritmos.Algoritmos;
import implementacion.ArbolPrecipitaciones;
import tdas.ABBPrecipitacionesTDA;

public class TestAlgoritmos {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS TDD PARA ALGORITMOS ===");
        
        // Tests básicos
        testCrearAlgoritmos();
        testAgregarMedicion();
        
        // Test principal
        testMesMasLluvioso();
        testMesMasLluviosoConDatosComplejos();
        testMesMasLluviosoArbolVacio();
        
        System.out.println("=== TODOS LOS TESTS PASARON ===");
    }
    
    private static void testCrearAlgoritmos() {
        System.out.println("Test: Crear Algoritmos");
        
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        Algoritmos alg = new Algoritmos(arbol);
        
        assert alg != null : "Algoritmos debería crearse correctamente";
        
        System.out.println("✅ Test crear algoritmos PASÓ");
    }
    
    private static void testAgregarMedicion() {
        System.out.println("Test: Agregar medición");
        
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        Algoritmos alg = new Algoritmos(arbol);
        
        // Agregar medición
        alg.agregarMedicion("Campo1", 2024, 1, 15, 100);
        
        // Verificar que se agregó (el árbol no debería estar vacío)
        assert !arbol.arbolVacio() : "El árbol debería tener datos después de agregar medición";
        
        System.out.println("✅ Test agregar medición PASÓ");
    }
    
    private static void testMesMasLluvioso() {
        System.out.println("Test: mesMasLluvioso() - Caso básico");
        
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        Algoritmos alg = new Algoritmos(arbol);
        
        // Agregar datos según tu interpretación:
        // Enero 2024: 100mm (Campo1)
        // Febrero 2024: 200mm (Campo1) ← Debería ganar
        // Enero 2023: 50mm (Campo2)
        alg.agregarMedicion("Campo1", 2024, 1, 15, 100);  
        alg.agregarMedicion("Campo1", 2024, 2, 10, 200);  
        alg.agregarMedicion("Campo2", 2023, 1, 5, 50);    
        
        int mesGanador = alg.mesMasLluvioso();
        System.out.println("Mes más lluvioso: " + mesGanador);
        
        // Según tu interpretación: Febrero (200mm) > Enero 2024 (100mm) > Enero 2023 (50mm)
        assert mesGanador == 2 : "Esperaba mes 2 (Febrero), pero fue: " + mesGanador;
        
        System.out.println("✅ Test mesMasLluvioso básico PASÓ");
    }
    
    private static void testMesMasLluviosoConDatosComplejos() {
        System.out.println("Test: mesMasLluvioso() - Datos complejos");
        
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        Algoritmos alg = new Algoritmos(arbol);
        
        // Datos según tu ejemplo en el comentario:
        // 202501: 55mm
        // 202402: 150mm  
        // 202302: 150mm
        // 200101: 250mm ← Debería ganar (mes 1)
        
        alg.agregarMedicion("Campo1", 2025, 1, 10, 55);   // 202501: 55mm
        alg.agregarMedicion("Campo2", 2024, 2, 15, 150);  // 202402: 150mm
        alg.agregarMedicion("Campo3", 2023, 2, 20, 150);  // 202302: 150mm
        alg.agregarMedicion("Campo4", 2001, 1, 5, 250);   // 200101: 250mm
        
        int mesGanador = alg.mesMasLluvioso();
        System.out.println("Mes más lluvioso (datos complejos): " + mesGanador);
        
        // Según tu interpretación: Enero (250mm en 2001) debería ganar
        assert mesGanador == 1 : "Esperaba mes 1 (Enero), pero fue: " + mesGanador;
        
        System.out.println("✅ Test mesMasLluvioso datos complejos PASÓ");
    }
    
    private static void testMesMasLluviosoArbolVacio() {
        System.out.println("Test: mesMasLluvioso() - Árbol vacío");
        
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        Algoritmos alg = new Algoritmos(arbol);
        
        int mesGanador = alg.mesMasLluvioso();
        System.out.println("Mes más lluvioso (árbol vacío): " + mesGanador);
        
        // Con árbol vacío debería retornar -1
        assert mesGanador == -1 : "Esperaba -1 para árbol vacío, pero fue: " + mesGanador;
        
        System.out.println("✅ Test mesMasLluvioso árbol vacío PASÓ");
    }
} 