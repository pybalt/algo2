package test;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class TestValidacionImplementacion {
    
    private static final String BOOTSTRAP_PATH = "../tp-bootstrap/";
    private static final String IMPLEMENTATION_PATH = "./";
    
    public static void main(String[] args) {
        System.out.println("=== VALIDACIÓN DE IMPLEMENTACIÓN CONTRA REFERENCIA ===");
        
        try {
            // Test 1: Verificar que no se agregaron métodos públicos
            testNoSeAgregaronMetodosPublicos();
            
            // Test 2: Verificar que no se modificaron nombres de métodos
            testNoSeModificaronNombresMetodos();
            
            // Test 3: Verificar que no se cambiaron firmas de métodos
            testNoSeCambiaronFirmasMetodos();
            
            // Test 4: Verificar estructura de clases
            testEstructuraClases();
            
            System.out.println("=== TODAS LAS VALIDACIONES PASARON ===");
            
        } catch (Exception e) {
            System.err.println("ERROR en validación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testNoSeAgregaronMetodosPublicos() throws IOException {
        System.out.println("Test: Verificando que no se agregaron métodos públicos...");
        
        String[] implementationFiles = {
            "implementacion/ArbolPrecipitaciones.java",
            "implementacion/ColaString.java", 
            "implementacion/ColaPrioridad.java",
            "implementacion/Conjunto.java",
            "implementacion/ConjuntoString.java",
            "implementacion/DiccionarioSimple.java",
            "implementacion/DiccionarioSimpleString.java"
        };
        
        for (String fileName : implementationFiles) {
            Set<String> bootstrapPublicMethods = extractPublicMethods(BOOTSTRAP_PATH + fileName);
            Set<String> implementationPublicMethods = extractPublicMethods(IMPLEMENTATION_PATH + fileName);
            
            // Verificar que no hay métodos públicos adicionales
            Set<String> extraMethods = new HashSet<>(implementationPublicMethods);
            extraMethods.removeAll(bootstrapPublicMethods);
            
            if (!extraMethods.isEmpty()) {
                System.err.println("VIOLACIÓN: Se agregaron métodos públicos en " + fileName);
                System.err.println("Métodos extra: " + extraMethods);
                System.err.println("Bootstrap: " + bootstrapPublicMethods);
                System.err.println("Implementation: " + implementationPublicMethods);
                throw new AssertionError("VIOLACIÓN en " + fileName);
            }
            
            System.out.println("✅ " + fileName + " - No se agregaron métodos públicos");
        }
    }
    
    private static void testNoSeModificaronNombresMetodos() throws IOException {
        System.out.println("Test: Verificando que no se modificaron nombres de métodos...");
        
        String[] implementationFiles = {
            "implementacion/ArbolPrecipitaciones.java",
            "algoritmos/Algoritmos.java"
        };
        
        for (String fileName : implementationFiles) {
            Set<String> bootstrapMethods = extractAllMethodNames(BOOTSTRAP_PATH + fileName);
            Set<String> implementationMethods = extractAllMethodNames(IMPLEMENTATION_PATH + fileName);
            
            // Los métodos del bootstrap deben existir en la implementación
            Set<String> missingMethods = new HashSet<>(bootstrapMethods);
            missingMethods.removeAll(implementationMethods);
            
            if (!missingMethods.isEmpty()) {
                throw new AssertionError("VIOLACIÓN: Faltan métodos en " + fileName + ": " + missingMethods);
            }
            
            System.out.println("✅ " + fileName + " - Todos los métodos requeridos están presentes");
        }
    }
    
    private static void testNoSeCambiaronFirmasMetodos() throws IOException {
        System.out.println("Test: Verificando que no se cambiaron firmas de métodos...");
        
        // Verificar interfaces TDA (no deben cambiar NUNCA)
        String[] tdaFiles = {
            "tdas/ABBPrecipitacionesTDA.java",
            "tdas/ColaPrioridadTDA.java",
            "tdas/ColaStringTDA.java",
            "tdas/ConjuntoTDA.java",
            "tdas/ConjuntoStringTDA.java",
            "tdas/DiccionarioSimpleTDA.java",
            "tdas/DiccionarioSimpleStringTDA.java"
        };
        
        for (String fileName : tdaFiles) {
            String bootstrapContent = readFileContent(BOOTSTRAP_PATH + fileName);
            String implementationContent = readFileContent(IMPLEMENTATION_PATH + fileName);
            
            // Normalizar espacios en blanco para comparación
            bootstrapContent = normalizeWhitespace(bootstrapContent);
            implementationContent = normalizeWhitespace(implementationContent);
            
            if (!bootstrapContent.equals(implementationContent)) {
                throw new AssertionError("VIOLACIÓN: Se modificó la interfaz TDA: " + fileName);
            }
            
            System.out.println("✅ " + fileName + " - Interfaz TDA no modificada");
        }
    }
    
    private static void testEstructuraClases() throws IOException {
        System.out.println("Test: Verificando estructura de clases...");
        
        // Verificar que las clases principales existen
        String[] requiredFiles = {
            "algoritmos/Algoritmos.java",
            "implementacion/ArbolPrecipitaciones.java"
        };
        
        for (String fileName : requiredFiles) {
            Path filePath = Paths.get(IMPLEMENTATION_PATH + fileName);
            if (!Files.exists(filePath)) {
                throw new AssertionError("VIOLACIÓN: Falta archivo requerido: " + fileName);
            }
            
            System.out.println("✅ " + fileName + " - Archivo existe");
        }
        
        // Verificar que Algoritmos tiene los métodos requeridos
        Set<String> algoritmosMethodsRequired = Set.of(
            "agregarMedicion",
            "eliminarMedicion", 
            "eliminarCampo",
            "medicionesMes",
            "medicionesCampoMes",
            "mesMasLluvioso",
            "promedioLluviaEnUnDia",
            "campoMasLLuvisoHistoria",
            "camposConLLuviaMayorPromedio"
        );
        
        Set<String> implementedMethods = extractAllMethodNames(IMPLEMENTATION_PATH + "algoritmos/Algoritmos.java");
        
        for (String requiredMethod : algoritmosMethodsRequired) {
            if (!implementedMethods.contains(requiredMethod)) {
                throw new AssertionError("VIOLACIÓN: Falta método requerido en Algoritmos: " + requiredMethod);
            }
        }
        
        System.out.println("✅ Algoritmos.java - Todos los métodos requeridos implementados");
    }
    
    // Métodos auxiliares
    
    private static Set<String> extractPublicMethods(String filePath) throws IOException {
        String content = readFileContent(filePath);
        Set<String> publicMethods = new HashSet<>();
        
        // Regex para encontrar métodos públicos
        Pattern pattern = Pattern.compile("public\\s+[^{]*?\\s+(\\w+)\\s*\\([^)]*\\)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            String methodName = matcher.group(1);
            // Excluir constructores y palabras clave
            if (!methodName.equals("class") && !methodName.equals("interface")) {
                publicMethods.add(methodName);
            }
        }
        
        return publicMethods;
    }
    
    private static Set<String> extractAllMethodNames(String filePath) throws IOException {
        String content = readFileContent(filePath);
        Set<String> methods = new HashSet<>();
        
        // Regex para encontrar todos los métodos (public, private, etc.)
        Pattern pattern = Pattern.compile("(public|private|protected)?\\s+[^{]*?\\s+(\\w+)\\s*\\([^)]*\\)\\s*\\{", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            String methodName = matcher.group(2);
            // Excluir constructores, clases e interfaces
            if (!methodName.equals("class") && !methodName.equals("interface") && 
                !Character.isUpperCase(methodName.charAt(0))) {
                methods.add(methodName);
            }
        }
        
        return methods;
    }
    
    private static String readFileContent(String filePath) throws IOException {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            // Si el archivo no existe en bootstrap, retornar string vacío
            if (filePath.contains("tp-bootstrap")) {
                return "";
            }
            throw e;
        }
    }
    
    private static String normalizeWhitespace(String content) {
        return content.replaceAll("\\s+", " ").trim();
    }
} 