package test;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class TestSimpleValidacion {
    
    public static void main(String[] args) {
        System.out.println("=== VALIDACIÓN SIMPLE ===");
        
        try {
            // Test básico: verificar que podemos leer los archivos
            testLecturaArchivos();
            
            // Test: verificar métodos públicos en ArbolPrecipitaciones
            testArbolPrecipitaciones();
            
            // Test: verificar métodos en Algoritmos
            testAlgoritmos();
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testLecturaArchivos() throws IOException {
        System.out.println("Test: Verificando lectura de archivos...");
        
        String bootstrapArbol = "../../tp-bootstrap/implementacion/ArbolPrecipitaciones.java";
        String implArbol = "./implementacion/ArbolPrecipitaciones.java";
        
        if (Files.exists(Paths.get(bootstrapArbol))) {
            System.out.println("✅ Bootstrap ArbolPrecipitaciones encontrado");
        } else {
            System.out.println("❌ Bootstrap ArbolPrecipitaciones NO encontrado");
        }
        
        if (Files.exists(Paths.get(implArbol))) {
            System.out.println("✅ Implementation ArbolPrecipitaciones encontrado");
        } else {
            System.out.println("❌ Implementation ArbolPrecipitaciones NO encontrado");
        }
    }
    
    private static void testArbolPrecipitaciones() throws IOException {
        System.out.println("Test: Verificando ArbolPrecipitaciones...");
        
        Set<String> bootstrapMethods = extractPublicMethods("../../tp-bootstrap/implementacion/ArbolPrecipitaciones.java");
        Set<String> implMethods = extractPublicMethods("./implementacion/ArbolPrecipitaciones.java");
        
        System.out.println("Bootstrap métodos: " + bootstrapMethods);
        System.out.println("Implementation métodos: " + implMethods);
        
        if (bootstrapMethods.isEmpty()) {
            System.out.println("ℹ️  Bootstrap está vacío - no hay restricciones de referencia");
            System.out.println("✅ ArbolPrecipitaciones implementado libremente");
            return;
        }
        
        Set<String> extraMethods = new HashSet<>(implMethods);
        extraMethods.removeAll(bootstrapMethods);
        
        if (!extraMethods.isEmpty()) {
            System.out.println("❌ VIOLACIÓN: Métodos públicos extra: " + extraMethods);
        } else {
            System.out.println("✅ No se agregaron métodos públicos");
        }
    }
    
    private static void testAlgoritmos() throws IOException {
        System.out.println("Test: Verificando Algoritmos...");
        
        Set<String> bootstrapMethods = extractPublicMethods("../../tp-bootstrap/algoritmos/Algoritmos.java");
        Set<String> implMethods = extractPublicMethods("./algoritmos/Algoritmos.java");
        
        System.out.println("Bootstrap métodos: " + bootstrapMethods);
        System.out.println("Implementation métodos: " + implMethods);
        
        if (bootstrapMethods.isEmpty()) {
            System.out.println("ℹ️  Bootstrap está vacío - no hay restricciones de referencia");
            System.out.println("✅ Algoritmos implementado libremente");
            return;
        }
        
        // Verificar que no faltan métodos requeridos
        Set<String> missingMethods = new HashSet<>(bootstrapMethods);
        missingMethods.removeAll(implMethods);
        
        if (!missingMethods.isEmpty()) {
            System.out.println("❌ VIOLACIÓN: Faltan métodos: " + missingMethods);
        }
        
        // Verificar métodos extra (permitimos constructor)
        Set<String> extraMethods = new HashSet<>(implMethods);
        extraMethods.removeAll(bootstrapMethods);
        extraMethods.remove("Algoritmos"); // Constructor permitido
        
        if (!extraMethods.isEmpty()) {
            System.out.println("❌ VIOLACIÓN: Métodos públicos extra: " + extraMethods);
        } else {
            System.out.println("✅ Algoritmos cumple con la interfaz");
        }
    }
    
    private static Set<String> extractPublicMethods(String filePath) throws IOException {
        String content = readFileContent(filePath);
        Set<String> publicMethods = new HashSet<>();
        
        // Regex para encontrar métodos públicos
        Pattern pattern = Pattern.compile("public\\s+[^{]*?\\s+(\\w+)\\s*\\([^)]*\\)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            String methodName = matcher.group(1);
            // Excluir palabras clave
            if (!methodName.equals("class") && !methodName.equals("interface")) {
                publicMethods.add(methodName);
            }
        }
        
        return publicMethods;
    }
    
    private static String readFileContent(String filePath) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            return "";
        }
        return Files.readString(Paths.get(filePath));
    }
} 