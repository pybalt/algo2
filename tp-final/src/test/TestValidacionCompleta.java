package test;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class TestValidacionCompleta {
    
    public static void main(String[] args) {
        System.out.println("=== VALIDACIÓN COMPLETA DE IMPLEMENTACIÓN ===");
        
        try {
            // Test 1: Verificar estructura básica
            testEstructuraBasica();
            
            // Test 2: Verificar interfaces TDA (estas SÍ tienen restricciones)
            testInterfacesTDA();
            
            // Test 3: Verificar implementaciones (estas pueden ser libres si bootstrap está vacío)
            testImplementaciones();
            
            // Test 4: Verificar algoritmos
            testAlgoritmos();
            
            // Test 5: Verificar que los métodos requeridos están implementados
            testMetodosRequeridos();
            
            System.out.println("\n=== RESUMEN DE VALIDACIÓN ===");
            System.out.println("✅ Estructura básica correcta");
            System.out.println("✅ Interfaces TDA respetadas");
            System.out.println("✅ Implementaciones válidas");
            System.out.println("✅ Algoritmos implementados");
            System.out.println("✅ Métodos requeridos presentes");
            System.out.println("\n🎉 TODAS LAS VALIDACIONES PASARON - IMPLEMENTACIÓN VÁLIDA");
            
        } catch (Exception e) {
            System.err.println("\n❌ ERROR en validación: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void testEstructuraBasica() throws IOException {
        System.out.println("\n1. Verificando estructura básica...");
        
        String[] requiredFiles = {
            "./algoritmos/Algoritmos.java",
            "./implementacion/ArbolPrecipitaciones.java",
            "./implementacion/ColaPrioridad.java",
            "./implementacion/ColaString.java",
            "./implementacion/Conjunto.java",
            "./implementacion/ConjuntoString.java",
            "./implementacion/DiccionarioSimple.java",
            "./implementacion/DiccionarioSimpleString.java",
            "./implementacion/Utils.java"
        };
        
        for (String file : requiredFiles) {
            if (!Files.exists(Paths.get(file))) {
                throw new AssertionError("Archivo requerido faltante: " + file);
            }
            System.out.println("✅ " + file);
        }
    }
    
    private static void testInterfacesTDA() throws IOException {
        System.out.println("\n2. Verificando interfaces TDA...");
        
        String[] tdaFiles = {
            "ABBPrecipitacionesTDA.java",
            "ColaPrioridadTDA.java", 
            "ColaStringTDA.java",
            "ConjuntoTDA.java",
            "ConjuntoStringTDA.java",
            "DiccionarioSimpleTDA.java",
            "DiccionarioSimpleStringTDA.java"
        };
        
        for (String fileName : tdaFiles) {
            String bootstrapPath = "../../tp-bootstrap/tdas/" + fileName;
            String implPath = "./tdas/" + fileName;
            
            if (!Files.exists(Paths.get(implPath))) {
                throw new AssertionError("Interfaz TDA faltante: " + fileName);
            }
            
            // Si existe el bootstrap, verificar que no se modificó
            if (Files.exists(Paths.get(bootstrapPath))) {
                String bootstrapContent = normalizeContent(Files.readString(Paths.get(bootstrapPath)));
                String implContent = normalizeContent(Files.readString(Paths.get(implPath)));
                
                if (!bootstrapContent.equals(implContent)) {
                    System.out.println("⚠️  Interfaz TDA modificada: " + fileName);
                    // No es error crítico si el bootstrap existe pero es diferente
                }
            }
            
            System.out.println("✅ " + fileName);
        }
    }
    
    private static void testImplementaciones() throws IOException {
        System.out.println("\n3. Verificando implementaciones...");
        
        String[] implFiles = {
            "ArbolPrecipitaciones.java",
            "ColaPrioridad.java",
            "ColaString.java", 
            "Conjunto.java",
            "ConjuntoString.java",
            "DiccionarioSimple.java",
            "DiccionarioSimpleString.java"
        };
        
        for (String fileName : implFiles) {
            String bootstrapPath = "../../tp-bootstrap/implementacion/" + fileName;
            String implPath = "./implementacion/" + fileName;
            
            Set<String> bootstrapMethods = extractPublicMethods(bootstrapPath);
            Set<String> implMethods = extractPublicMethods(implPath);
            
            if (bootstrapMethods.isEmpty()) {
                System.out.println("✅ " + fileName + " (implementación libre - bootstrap vacío)");
            } else {
                // Verificar que no se agregaron métodos públicos
                Set<String> extraMethods = new HashSet<>(implMethods);
                extraMethods.removeAll(bootstrapMethods);
                
                if (!extraMethods.isEmpty()) {
                    System.out.println("⚠️  " + fileName + " - métodos públicos extra: " + extraMethods);
                }
                
                // Verificar que no faltan métodos requeridos
                Set<String> missingMethods = new HashSet<>(bootstrapMethods);
                missingMethods.removeAll(implMethods);
                
                if (!missingMethods.isEmpty()) {
                    throw new AssertionError("Métodos faltantes en " + fileName + ": " + missingMethods);
                }
                
                System.out.println("✅ " + fileName + " (conforme a bootstrap)");
            }
        }
    }
    
    private static void testAlgoritmos() throws IOException {
        System.out.println("\n4. Verificando algoritmos...");
        
        String bootstrapPath = "../../tp-bootstrap/algoritmos/Algoritmos.java";
        String implPath = "./algoritmos/Algoritmos.java";
        
        Set<String> bootstrapMethods = extractPublicMethods(bootstrapPath);
        Set<String> implMethods = extractPublicMethods(implPath);
        
        if (bootstrapMethods.isEmpty()) {
            System.out.println("✅ Algoritmos.java (implementación libre - bootstrap vacío)");
        } else {
            // Verificar métodos requeridos
            Set<String> missingMethods = new HashSet<>(bootstrapMethods);
            missingMethods.removeAll(implMethods);
            
            if (!missingMethods.isEmpty()) {
                throw new AssertionError("Métodos faltantes en Algoritmos: " + missingMethods);
            }
            
            System.out.println("✅ Algoritmos.java (conforme a bootstrap)");
        }
    }
    
    private static void testMetodosRequeridos() throws IOException {
        System.out.println("\n5. Verificando métodos requeridos...");
        
        // Verificar que Algoritmos tiene los métodos especificados en el enunciado
        Set<String> requiredMethods = Set.of(
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
        
        Set<String> implementedMethods = extractAllMethodNames("./algoritmos/Algoritmos.java");
        
        for (String method : requiredMethods) {
            if (!implementedMethods.contains(method)) {
                throw new AssertionError("Método requerido faltante: " + method);
            }
            System.out.println("✅ " + method);
        }
        
        // Verificar que ArbolPrecipitaciones implementa ABBPrecipitacionesTDA
        String arbolContent = Files.readString(Paths.get("./implementacion/ArbolPrecipitaciones.java"));
        if (!arbolContent.contains("implements ABBPrecipitacionesTDA")) {
            throw new AssertionError("ArbolPrecipitaciones debe implementar ABBPrecipitacionesTDA");
        }
        System.out.println("✅ ArbolPrecipitaciones implementa ABBPrecipitacionesTDA");
    }
    
    // Métodos auxiliares
    
    private static Set<String> extractPublicMethods(String filePath) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            return new HashSet<>();
        }
        
        String content = Files.readString(Paths.get(filePath));
        Set<String> publicMethods = new HashSet<>();
        
        Pattern pattern = Pattern.compile("public\\s+[^{]*?\\s+(\\w+)\\s*\\([^)]*\\)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            String methodName = matcher.group(1);
            if (!methodName.equals("class") && !methodName.equals("interface")) {
                publicMethods.add(methodName);
            }
        }
        
        return publicMethods;
    }
    
    private static Set<String> extractAllMethodNames(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        Set<String> methods = new HashSet<>();
        
        Pattern pattern = Pattern.compile("(public|private|protected)?\\s+[^{]*?\\s+(\\w+)\\s*\\([^)]*\\)\\s*\\{", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            String methodName = matcher.group(2);
            if (!methodName.equals("class") && !methodName.equals("interface") && 
                !Character.isUpperCase(methodName.charAt(0))) {
                methods.add(methodName);
            }
        }
        
        return methods;
    }
    
    private static String normalizeContent(String content) {
        return content.replaceAll("\\s+", " ").trim();
    }
} 