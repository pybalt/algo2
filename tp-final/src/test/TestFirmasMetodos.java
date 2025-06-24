package test;

import java.io.*;
import java.nio.file.*;
import java.lang.reflect.*;
import java.util.*;

public class TestFirmasMetodos {
    
    public static void main(String[] args) {
        System.out.println("=== VALIDACIÓN DE FIRMAS DE MÉTODOS ===");
        
        try {
            // Test 1: Verificar firmas de Algoritmos
            testFirmasAlgoritmos();
            
            // Test 2: Verificar que ArbolPrecipitaciones implementa correctamente la interfaz
            testImplementacionArbol();
            
            // Test 3: Verificar tipos de retorno específicos
            testTiposRetorno();
            
            System.out.println("\n=== RESUMEN ===");
            System.out.println("✅ Todas las firmas de métodos son correctas");
            System.out.println("✅ Los tipos de retorno coinciden con el enunciado");
            System.out.println("✅ Las implementaciones son válidas");
            System.out.println("\n🎉 VALIDACIÓN DE FIRMAS EXITOSA");
            
        } catch (Exception e) {
            System.err.println("\n❌ ERROR en validación de firmas: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void testFirmasAlgoritmos() throws Exception {
        System.out.println("\n1. Verificando firmas de métodos en Algoritmos...");
        
        // Cargar la clase Algoritmos
        Class<?> clazz = Class.forName("algoritmos.Algoritmos");
        
        // Verificar constructor
        Constructor<?> constructor = clazz.getConstructor(
            Class.forName("tdas.ABBPrecipitacionesTDA")
        );
        System.out.println("✅ Constructor(ABBPrecipitacionesTDA)");
        
        // Verificar cada método requerido
        Method agregarMedicion = clazz.getMethod("agregarMedicion", String.class, int.class, int.class, int.class, int.class);
        if (agregarMedicion.getReturnType() != void.class) {
            throw new AssertionError("agregarMedicion debe devolver void");
        }
        System.out.println("✅ agregarMedicion(String, int, int, int, int): void");
        
        Method eliminarMedicion = clazz.getMethod("eliminarMedicion", String.class, int.class, int.class, int.class, int.class);
        if (eliminarMedicion.getReturnType() != void.class) {
            throw new AssertionError("eliminarMedicion debe devolver void");
        }
        System.out.println("✅ eliminarMedicion(String, int, int, int, int): void");
        
        Method eliminarCampo = clazz.getMethod("eliminarCampo", String.class);
        if (eliminarCampo.getReturnType() != void.class) {
            throw new AssertionError("eliminarCampo debe devolver void");
        }
        System.out.println("✅ eliminarCampo(String): void");
        
        Method medicionesMes = clazz.getMethod("medicionesMes", int.class, int.class);
        if (!medicionesMes.getReturnType().getName().equals("tdas.ColaPrioridadTDA")) {
            throw new AssertionError("medicionesMes debe devolver ColaPrioridadTDA");
        }
        System.out.println("✅ medicionesMes(int, int): ColaPrioridadTDA");
        
        Method medicionesCampoMes = clazz.getMethod("medicionesCampoMes", String.class, int.class, int.class);
        if (!medicionesCampoMes.getReturnType().getName().equals("tdas.ColaPrioridadTDA")) {
            throw new AssertionError("medicionesCampoMes debe devolver ColaPrioridadTDA");
        }
        System.out.println("✅ medicionesCampoMes(String, int, int): ColaPrioridadTDA");
        
        Method mesMasLluvioso = clazz.getMethod("mesMasLluvioso");
        if (mesMasLluvioso.getReturnType() != int.class) {
            throw new AssertionError("mesMasLluvioso debe devolver int");
        }
        System.out.println("✅ mesMasLluvioso(): int");
        
        Method promedioLluviaEnUnDia = clazz.getMethod("promedioLluviaEnUnDia", int.class, int.class, int.class);
        if (promedioLluviaEnUnDia.getReturnType() != float.class) {
            throw new AssertionError("promedioLluviaEnUnDia debe devolver float");
        }
        System.out.println("✅ promedioLluviaEnUnDia(int, int, int): float");
        
        Method campoMasLLuvisoHistoria = clazz.getMethod("campoMasLLuvisoHistoria");
        if (campoMasLLuvisoHistoria.getReturnType() != String.class) {
            throw new AssertionError("campoMasLLuvisoHistoria debe devolver String");
        }
        System.out.println("✅ campoMasLLuvisoHistoria(): String");
        
        Method camposConLLuviaMayorPromedio = clazz.getMethod("camposConLLuviaMayorPromedio", int.class, int.class);
        if (!camposConLLuviaMayorPromedio.getReturnType().getName().equals("implementacion.ColaString")) {
            throw new AssertionError("camposConLLuviaMayorPromedio debe devolver ColaString");
        }
        System.out.println("✅ camposConLLuviaMayorPromedio(int, int): ColaString");
    }
    
    private static void testImplementacionArbol() throws Exception {
        System.out.println("\n2. Verificando implementación de ArbolPrecipitaciones...");
        
        Class<?> clazz = Class.forName("implementacion.ArbolPrecipitaciones");
        Class<?> interfaceClass = Class.forName("tdas.ABBPrecipitacionesTDA");
        
        // Verificar que implementa la interfaz
        if (!interfaceClass.isAssignableFrom(clazz)) {
            throw new AssertionError("ArbolPrecipitaciones debe implementar ABBPrecipitacionesTDA");
        }
        System.out.println("✅ ArbolPrecipitaciones implementa ABBPrecipitacionesTDA");
        
        // Verificar métodos específicos de la interfaz
        Method inicializar = clazz.getMethod("inicializar");
        System.out.println("✅ inicializar()");
        
        Method agregar = clazz.getMethod("agregar", String.class);
        System.out.println("✅ agregar(String)");
        
        Method agregarMedicion = clazz.getMethod("agregarMedicion", String.class, String.class, String.class, int.class, int.class);
        System.out.println("✅ agregarMedicion(String, String, String, int, int)");
        
        Method eliminar = clazz.getMethod("eliminar", String.class);
        System.out.println("✅ eliminar(String)");
        
        Method raiz = clazz.getMethod("raiz");
        if (raiz.getReturnType() != String.class) {
            throw new AssertionError("raiz() debe devolver String");
        }
        System.out.println("✅ raiz(): String");
        
        Method periodos = clazz.getMethod("periodos");
        if (!periodos.getReturnType().getName().equals("tdas.ColaStringTDA")) {
            throw new AssertionError("periodos() debe devolver ColaStringTDA");
        }
        System.out.println("✅ periodos(): ColaStringTDA");
        
        Method precipitaciones = clazz.getMethod("precipitaciones", String.class);
        if (!precipitaciones.getReturnType().getName().equals("tdas.ColaPrioridadTDA")) {
            throw new AssertionError("precipitaciones(String) debe devolver ColaPrioridadTDA");
        }
        System.out.println("✅ precipitaciones(String): ColaPrioridadTDA");
    }
    
    private static void testTiposRetorno() throws Exception {
        System.out.println("\n3. Verificando tipos de retorno específicos...");
        
        // Verificar que los tipos de retorno son exactamente los esperados
        Class<?> algoritmos = Class.forName("algoritmos.Algoritmos");
        
        // mesMasLluvioso debe devolver int (no ColaPrioridadTDA como en el bootstrap)
        Method mesMasLluvioso = algoritmos.getMethod("mesMasLluvioso");
        if (mesMasLluvioso.getReturnType() != int.class) {
            throw new AssertionError("mesMasLluvioso debe devolver int según el enunciado");
        }
        System.out.println("✅ mesMasLluvioso devuelve int (correcto según enunciado)");
        
        // Verificar que las colas devuelven los tipos correctos
        Method medicionesMes = algoritmos.getMethod("medicionesMes", int.class, int.class);
        System.out.println("✅ medicionesMes devuelve ColaPrioridadTDA");
        
        Method camposConLLuviaMayorPromedio = algoritmos.getMethod("camposConLLuviaMayorPromedio", int.class, int.class);
        System.out.println("✅ camposConLLuviaMayorPromedio devuelve ColaString");
    }
} 