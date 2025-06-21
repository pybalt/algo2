package test;

import implementacion.ArbolPrecipitaciones;
import tdas.ABBPrecipitacionesTDA;
import tdas.ColaStringTDA;
import tdas.ColaPrioridadTDA;

public class TestArbolPrecipitaciones {
    
    public static void main(String[] args) {
        System.out.println("=== TESTS ARBOL PRECIPITACIONES SEGUN ENUNCIADO ===");
        
        // Tests básicos obligatorios
        testInicializar();
        testAgregarCampo();
        testAgregarMedicion();
        testValidacionFechasSegunEnunciado();
        testEstructuraSegunEnunciado();
        testNavegacion();
        testArbolVacio();
        
        // Tests de funcionalidad específica del enunciado
        testPeriodosSegunEnunciado();
        testPrecipitacionesSegunEnunciado();
        testEliminarMedicionSegunEnunciado();
        testEliminarCampoSegunEnunciado();
        
        // Tests de casos del enunciado
        testConversionEnterosString();
        testEstructuraDiccionarios();
        testValidacionCompleta();
        testCasosLimiteSegunEnunciado();
        
        System.out.println("=== TODOS LOS TESTS COMPLETADOS SEGUN ENUNCIADO ===");
    }
    
    public static void testInicializar() {
        System.out.println("Test: Inicializar");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        assert arbol.arbolVacio() : "El arbol deberia estar vacio despues de inicializar";
        System.out.println("✓ Inicializar - PASS");
    }
    
    public static void testAgregarCampo() {
        System.out.println("Test: Agregar Campo (ABB de nombres de campos)");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        // Según enunciado: "campos de cultivo se registrarán en una estructura de tipo árbol binario de búsqueda"
        arbol.agregar("Campo Norte");
        assert !arbol.arbolVacio() : "El arbol no deberia estar vacio despues de agregar";
        assert arbol.raiz().equals("Campo Norte") : "La raiz deberia ser Campo Norte";
        
        // ABB debe mantener orden lexicográfico
        arbol.agregar("Campo Este");  // Menor lexicográficamente
        arbol.agregar("Campo Sur");   // Mayor lexicográficamente
        
        assert !arbol.hijoDer().arbolVacio() : "Hijo derecho no deberia estar vacio";
        assert !arbol.hijoIzq().arbolVacio() : "Hijo izquierdo no deberia estar vacio";
        
        // Verificar orden BST
        String hijoIzq = arbol.hijoIzq().raiz();
        String hijoDer = arbol.hijoDer().raiz();
        assert hijoIzq.compareToIgnoreCase("Campo Norte") < 0 : "Hijo izquierdo debe ser menor";
        assert hijoDer.compareToIgnoreCase("Campo Norte") > 0 : "Hijo derecho debe ser mayor";
        
        System.out.println("✓ Agregar Campo - PASS");
    }
    
    public static void testAgregarMedicion() {
        System.out.println("Test: Agregar Medicion (con validación de fechas)");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        // Según enunciado: agregar medición a campo inexistente debe ser ignorado
        arbol.agregarMedicion("Campo Inexistente", "2024", "03", 15, 25);
        assert arbol.arbolVacio() : "Agregar medicion a campo inexistente debe ser ignorado";
        
        // Primero agregar el campo
        arbol.agregar("Campo Central");
        assert !arbol.arbolVacio() : "Campo deberia existir";
        
        // Según enunciado: "Los años, meses, días y precipitaciones serán números enteros"
        // Pero año y mes se pasan como String (ya convertidos)
        arbol.agregarMedicion("Campo Central", "2024", "03", 15, 25);
        arbol.agregarMedicion("Campo Central", "2024", "03", 16, 30);
        arbol.agregarMedicion("Campo Central", "2024", "04", 10, 15);
        
        // Verificar que se crearon los periodos
        ColaStringTDA periodos = arbol.periodos();
        assert !periodos.colaVacia() : "Deberia tener periodos después de agregar mediciones";
        
        System.out.println("✓ Agregar Medicion - PASS");
    }
    
    public static void testValidacionFechasSegunEnunciado() {
        System.out.println("Test: Validacion de Fechas según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        arbol.agregar("Campo Test");
        
        // Según enunciado: "Deberá controlar y validar la cantidad de días de cada mes"
        // Las fechas inválidas deben ser IGNORADAS (no lanzar excepciones)
        
        // Fechas inválidas que deben ser ignoradas silenciosamente
        arbol.agregarMedicion("Campo Test", "2024", "02", 30, 30); // 30 de febrero
        arbol.agregarMedicion("Campo Test", "2024", "13", 15, 35); // Mes 13
        arbol.agregarMedicion("Campo Test", "2024", "00", 15, 40); // Mes 0
        arbol.agregarMedicion("Campo Test", "2024", "06", 0, 45);  // Día 0
        arbol.agregarMedicion("Campo Test", "2024", "06", 32, 50); // Día 32
        arbol.agregarMedicion("Campo Test", "2023", "02", 29, 55); // 29 feb no bisiesto
        arbol.agregarMedicion("Campo Test", "2024", "04", 31, 60); // 31 de abril
        
        // Fechas válidas que deben ser aceptadas
        arbol.agregarMedicion("Campo Test", "2024", "02", 29, 10); // 29 feb bisiesto
        arbol.agregarMedicion("Campo Test", "2024", "04", 30, 20); // 30 de abril
        arbol.agregarMedicion("Campo Test", "2024", "12", 31, 30); // 31 de diciembre
        arbol.agregarMedicion("Campo Test", "2024", "01", 31, 40); // 31 de enero
        
        // Verificar que solo se agregaron las fechas válidas
        ColaStringTDA periodos = arbol.periodos();
        assert !periodos.colaVacia() : "Deberia tener periodos de fechas válidas";
        
        // Contar periodos válidos esperados: 202402, 202404, 202412, 202401 = 4
        int contadorPeriodos = 0;
        while (!periodos.colaVacia()) {
            String periodo = periodos.primero();
            periodos.desacolar();
            contadorPeriodos++;
            // Verificar formato según enunciado: año+mes concatenados
            assert periodo.length() == 6 : "Periodo debe tener 6 caracteres (AAAAMM)";
            assert periodo.matches("\\d{6}") : "Periodo debe ser numérico";
        }
        
        assert contadorPeriodos == 4 : "Deberia tener exactamente 4 periodos válidos, tiene: " + contadorPeriodos;
        
        System.out.println("✓ Validacion de Fechas según Enunciado - PASS");
    }
    
    public static void testEstructuraSegunEnunciado() {
        System.out.println("Test: Estructura según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        // Según enunciado: ABB contiene nombre del campo (String)
        arbol.agregar("Campo Norte");
        arbol.agregar("Campo Sur");
        arbol.agregar("Campo Este");
        arbol.agregar("Campo Oeste");
        
        // Cada campo tiene DiccionarioSimpleStringTDA asociado
        // Clave: periodo (año+mes concatenados como String)
        // Valor: DiccionarioSimpleTDA (día -> precipitación)
        
        arbol.agregarMedicion("Campo Norte", "2024", "03", 15, 25);
        arbol.agregarMedicion("Campo Norte", "2024", "03", 16, 30);
        arbol.agregarMedicion("Campo Sur", "2024", "04", 10, 20);
        arbol.agregarMedicion("Campo Este", "2024", "03", 20, 35);
        
        // Verificar estructura ABB
        assert !arbol.arbolVacio() : "Arbol no deberia estar vacio";
        verificarEstructuraBST(arbol);
        
        // Verificar que periodos se generan correctamente
        ColaStringTDA periodos = arbol.periodos();
        assert !periodos.colaVacia() : "Deberia tener periodos";
        
        boolean tiene202403 = false, tiene202404 = false;
        int totalPeriodos = 0;
        
        while (!periodos.colaVacia()) {
            String periodo = periodos.primero();
            periodos.desacolar();
            totalPeriodos++;
            
            // Verificar formato según enunciado
            assert periodo.length() == 6 : "Periodo debe tener 6 caracteres";
            assert periodo.matches("\\d{6}") : "Periodo debe ser numérico";
            
            if (periodo.equals("202403")) tiene202403 = true;
            if (periodo.equals("202404")) tiene202404 = true;
        }
        
        assert tiene202403 : "Deberia tener periodo 202403";
        assert tiene202404 : "Deberia tener periodo 202404";
        assert totalPeriodos == 2 : "Deberia tener exactamente 2 periodos únicos";
        
        System.out.println("✓ Estructura según Enunciado - PASS");
    }
    
    public static void testNavegacion() {
        System.out.println("Test: Navegacion ABB");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        // Crear estructura ABB con orden lexicográfico
        arbol.agregar("Campo M");
        arbol.agregar("Campo F");  // Menor -> hijo izquierdo
        arbol.agregar("Campo T");  // Mayor -> hijo derecho
        
        // Verificar navegación básica
        assert arbol.raiz().equals("Campo M") : "Raiz deberia ser Campo M";
        assert arbol.hijoIzq().raiz().equals("Campo F") : "Hijo izquierdo deberia ser Campo F";
        assert arbol.hijoDer().raiz().equals("Campo T") : "Hijo derecho deberia ser Campo T";
        
        // Verificar que hojas tienen hijos vacíos
        assert arbol.hijoIzq().hijoIzq().arbolVacio() : "Hijo izquierdo de hoja deberia estar vacio";
        assert arbol.hijoIzq().hijoDer().arbolVacio() : "Hijo derecho de hoja deberia estar vacio";
        assert arbol.hijoDer().hijoIzq().arbolVacio() : "Hijo izquierdo de hoja deberia estar vacio";
        assert arbol.hijoDer().hijoDer().arbolVacio() : "Hijo derecho de hoja deberia estar vacio";
        
        System.out.println("✓ Navegacion - PASS");
    }
    
    public static void testArbolVacio() {
        System.out.println("Test: Arbol Vacio");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        assert arbol.arbolVacio() : "Arbol recien inicializado deberia estar vacio";
        
        arbol.agregar("Campo Único");
        assert !arbol.arbolVacio() : "Arbol con elementos no deberia estar vacio";
        
        System.out.println("✓ Arbol Vacio - PASS");
    }
    
    public static void testPeriodosSegunEnunciado() {
        System.out.println("Test: Periodos según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        // Test árbol vacío
        ColaStringTDA periodosVacio = arbol.periodos();
        assert periodosVacio != null : "periodos() no deberia devolver null";
        assert periodosVacio.colaVacia() : "Arbol vacio deberia devolver cola vacia";
        
        // Agregar campos y mediciones
        arbol.agregar("Campo A");
        arbol.agregar("Campo B");
        arbol.agregar("Campo C");
        
        // Según enunciado: período = año+mes concatenados
        arbol.agregarMedicion("Campo A", "2024", "01", 15, 25);
        arbol.agregarMedicion("Campo A", "2024", "03", 10, 30);
        arbol.agregarMedicion("Campo B", "2024", "01", 20, 15); // Mismo período que Campo A
        arbol.agregarMedicion("Campo B", "2024", "02", 5, 40);
        arbol.agregarMedicion("Campo C", "2024", "03", 12, 35); // Mismo período que Campo A
        
        ColaStringTDA periodos = arbol.periodos();
        assert !periodos.colaVacia() : "Deberia tener periodos";
        
        // Verificar períodos únicos (sin duplicados)
        boolean tiene202401 = false, tiene202402 = false, tiene202403 = false;
        int totalPeriodos = 0;
        
        while (!periodos.colaVacia()) {
            String periodo = periodos.primero();
            periodos.desacolar();
            totalPeriodos++;
            
            // Verificar formato según enunciado
            assert periodo.length() == 6 : "Periodo debe ser AAAAMM (6 caracteres)";
            assert periodo.matches("\\d{6}") : "Periodo debe ser numérico";
            
            if (periodo.equals("202401")) tiene202401 = true;
            if (periodo.equals("202402")) tiene202402 = true;
            if (periodo.equals("202403")) tiene202403 = true;
        }
        
        assert totalPeriodos == 3 : "Deberia tener exactamente 3 periodos únicos";
        assert tiene202401 : "Deberia tener periodo 202401";
        assert tiene202402 : "Deberia tener periodo 202402";
        assert tiene202403 : "Deberia tener periodo 202403";
        
        System.out.println("✓ Periodos según Enunciado - PASS");
    }
    
    public static void testPrecipitacionesSegunEnunciado() {
        System.out.println("Test: Precipitaciones según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        arbol.agregar("Campo Test");
        arbol.agregarMedicion("Campo Test", "2024", "03", 15, 25);
        arbol.agregarMedicion("Campo Test", "2024", "03", 16, 30);
        arbol.agregarMedicion("Campo Test", "2024", "03", 10, 20);
        
        // Según enunciado: precipitaciones() debe devolver ColaPrioridadTDA
        // con días del período específico
        ColaPrioridadTDA precip = arbol.precipitaciones("202403");
        
        if (precip == null) {
            System.out.println("❌ FALLA: precipitaciones() devuelve null - MÉTODO NO IMPLEMENTADO");
            throw new AssertionError("Método precipitaciones() debe estar implementado según enunciado");
        }
        
        assert !precip.colaVacia() : "Deberia tener precipitaciones para 202403";
        
        // Test período inexistente
        ColaPrioridadTDA precipVacio = arbol.precipitaciones("202412");
        assert precipVacio.colaVacia() : "Periodo inexistente deberia devolver cola vacia";
        
        System.out.println("✓ Precipitaciones según Enunciado - PASS");
    }
    
    public static void testEliminarMedicionSegunEnunciado() {
        System.out.println("Test: Eliminar Medicion según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        arbol.agregar("Campo Test");
        
        // Agregar mediciones
        arbol.agregarMedicion("Campo Test", "2024", "03", 15, 25);
        arbol.agregarMedicion("Campo Test", "2024", "03", 16, 30);
        arbol.agregarMedicion("Campo Test", "2024", "04", 10, 20);
        
        // Según enunciado: eliminación debe ser robusta (no lanzar excepciones)
        
        // Eliminar medición existente
        arbol.eliminarMedicion("Campo Test", "2024", "03", 15);
        
        // Eliminar medición inexistente - debe ser ignorada
        arbol.eliminarMedicion("Campo Test", "2024", "03", 20); // Día no existe
        arbol.eliminarMedicion("Campo Inexistente", "2024", "03", 16); // Campo no existe
        arbol.eliminarMedicion("Campo Test", "2024", "12", 15); // Período no existe
        
        // Eliminar con fecha inválida - debe ser ignorada por validación
        arbol.eliminarMedicion("Campo Test", "2024", "02", 30); // 30/02 inválido
        arbol.eliminarMedicion("Campo Test", "2024", "13", 15); // Mes 13 inválido
        
        // Verificar que el árbol sigue funcionando
        ColaStringTDA periodos = arbol.periodos();
        assert !periodos.colaVacia() : "Deberia mantener periodos válidos";
        
        System.out.println("✓ Eliminar Medicion según Enunciado - PASS");
    }
    
    public static void testEliminarCampoSegunEnunciado() {
        System.out.println("Test: Eliminar Campo según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        // Eliminar de árbol vacío - debe ser ignorado
        arbol.eliminar("Campo Inexistente");
        assert arbol.arbolVacio() : "Eliminar de arbol vacio debe mantenerlo vacio";
        
        // Test eliminar hoja
        arbol.agregar("Campo Único");
        arbol.eliminar("Campo Único");
        assert arbol.arbolVacio() : "Eliminar única hoja debe dejar arbol vacio";
        
        // Test eliminar con estructura más compleja
        arbol.inicializar();
        arbol.agregar("Campo M");
        arbol.agregar("Campo F");
        arbol.agregar("Campo T");
        
        // Agregar mediciones para verificar que se mantienen
        arbol.agregarMedicion("Campo M", "2024", "01", 15, 100);
        arbol.agregarMedicion("Campo F", "2024", "02", 10, 200);
        arbol.agregarMedicion("Campo T", "2024", "03", 5, 300);
        
        int camposIniciales = contarCampos(arbol);
        assert camposIniciales == 3 : "Deberia tener 3 campos inicialmente";
        
        // Eliminar hoja
        arbol.eliminar("Campo F");
        int camposDespues = contarCampos(arbol);
        assert camposDespues == 2 : "Deberia tener 2 campos después de eliminar hoja";
        
        // Verificar que la estructura BST se mantiene
        verificarEstructuraBST(arbol);
        
        // Verificar que las mediciones de otros campos se mantienen
        ColaStringTDA periodos = arbol.periodos();
        assert !periodos.colaVacia() : "Deberia mantener periodos de campos restantes";
        
        System.out.println("✓ Eliminar Campo según Enunciado - PASS");
    }
    
    public static void testConversionEnterosString() {
        System.out.println("Test: Conversion Enteros a String según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        arbol.agregar("Campo Test");
        
        // Según enunciado: "Los años, meses, días y precipitaciones serán números enteros"
        // "La conversión de números enteros a cadenas puede hacerlo mediante la clase String"
        
        // Simular conversión como especifica el enunciado
        int anioInt = 2024;
        int mesInt = 3;
        int diaInt = 15;
        int precipitacionInt = 100;
        
        String anioStr = String.valueOf(anioInt);
        String mesStr = String.valueOf(mesInt);
        
        // Los métodos reciben String para año/mes, int para día/precipitación
        arbol.agregarMedicion("Campo Test", anioStr, mesStr, diaInt, precipitacionInt);
        
        // Verificar que se creó el período correctamente
        ColaStringTDA periodos = arbol.periodos();
        assert !periodos.colaVacia() : "Deberia tener periodo";
        
        String periodo = periodos.primero();
        assert periodo.equals("202403") : "Periodo deberia ser '202403' (año+mes con formato 02d)";
        
        System.out.println("✓ Conversion Enteros a String según Enunciado - PASS");
    }
    
    public static void testEstructuraDiccionarios() {
        System.out.println("Test: Estructura de Diccionarios según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        arbol.agregar("Campo Test");
        
        // Según enunciado: 
        // ABB -> DiccionarioSimpleStringTDA (periodo -> DiccionarioSimpleTDA)
        // DiccionarioSimpleTDA (día -> precipitación)
        
        // Agregar mediciones en mismo período
        arbol.agregarMedicion("Campo Test", "2024", "03", 15, 25);
        arbol.agregarMedicion("Campo Test", "2024", "03", 16, 30);
        arbol.agregarMedicion("Campo Test", "2024", "03", 17, 35);
        
        // Agregar mediciones en diferente período
        arbol.agregarMedicion("Campo Test", "2024", "04", 10, 40);
        arbol.agregarMedicion("Campo Test", "2024", "04", 11, 45);
        
        // Verificar que se crearon los períodos correctos
        ColaStringTDA periodos = arbol.periodos();
        int contadorPeriodos = 0;
        boolean tiene202403 = false, tiene202404 = false;
        
        while (!periodos.colaVacia()) {
            String periodo = periodos.primero();
            periodos.desacolar();
            contadorPeriodos++;
            
            if (periodo.equals("202403")) tiene202403 = true;
            if (periodo.equals("202404")) tiene202404 = true;
        }
        
        assert contadorPeriodos == 2 : "Deberia tener exactamente 2 periodos";
        assert tiene202403 : "Deberia tener periodo 202403";
        assert tiene202404 : "Deberia tener periodo 202404";
        
        System.out.println("✓ Estructura de Diccionarios según Enunciado - PASS");
    }
    
    public static void testValidacionCompleta() {
        System.out.println("Test: Validacion Completa según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        arbol.agregar("Campo Validacion");
        
        // Test años bisiestos según enunciado
        arbol.agregarMedicion("Campo Validacion", "2024", "02", 29, 10); // 2024 es bisiesto
        arbol.agregarMedicion("Campo Validacion", "2023", "02", 29, 20); // 2023 no es bisiesto - ignorar
        arbol.agregarMedicion("Campo Validacion", "2000", "02", 29, 30); // 2000 es bisiesto
        arbol.agregarMedicion("Campo Validacion", "1900", "02", 29, 40); // 1900 no es bisiesto - ignorar
        
        // Test días por mes
        arbol.agregarMedicion("Campo Validacion", "2024", "01", 31, 50); // Enero 31 días - válido
        arbol.agregarMedicion("Campo Validacion", "2024", "04", 30, 60); // Abril 30 días - válido
        arbol.agregarMedicion("Campo Validacion", "2024", "04", 31, 70); // Abril 31 días - inválido
        arbol.agregarMedicion("Campo Validacion", "2024", "06", 31, 80); // Junio 31 días - inválido
        
        // Test meses válidos
        arbol.agregarMedicion("Campo Validacion", "2024", "12", 15, 90);  // Mes 12 - válido
        arbol.agregarMedicion("Campo Validacion", "2024", "13", 15, 100); // Mes 13 - inválido
        arbol.agregarMedicion("Campo Validacion", "2024", "00", 15, 110); // Mes 0 - inválido
        
        // Test días válidos
        arbol.agregarMedicion("Campo Validacion", "2024", "05", 1, 120);  // Día 1 - válido
        arbol.agregarMedicion("Campo Validacion", "2024", "05", 0, 130);  // Día 0 - inválido
        arbol.agregarMedicion("Campo Validacion", "2024", "05", 32, 140); // Día 32 - inválido
        
        ColaStringTDA periodos = arbol.periodos();
        int periodosValidos = 0;
        while (!periodos.colaVacia()) {
            periodos.desacolar();
            periodosValidos++;
        }
        
        // Solo deben quedar los períodos de fechas válidas
        assert periodosValidos >= 4 : "Deberia tener al menos 4 periodos de fechas válidas";
        
        System.out.println("✓ Validacion Completa según Enunciado - PASS");
    }
    
    public static void testCasosLimiteSegunEnunciado() {
        System.out.println("Test: Casos Limite según Enunciado");
        ABBPrecipitacionesTDA arbol = new ArbolPrecipitaciones();
        arbol.inicializar();
        
        // Test campo duplicado - según enunciado debe ser ignorado
        arbol.agregar("Campo Duplicado");
        arbol.agregar("Campo Duplicado"); // Duplicado - debe ser ignorado
        arbol.agregar("Campo Duplicado"); // Duplicado - debe ser ignorado
        
        // Verificar que solo hay un campo
        assert arbol.raiz().equals("Campo Duplicado") : "Deberia tener el campo una sola vez";
        assert arbol.hijoIzq().arbolVacio() : "No deberia tener hijo izquierdo";
        assert arbol.hijoDer().arbolVacio() : "No deberia tener hijo derecho";
        
        // Test medición mismo día múltiples veces - debe sobreescribir
        arbol.agregarMedicion("Campo Duplicado", "2024", "01", 15, 10);
        arbol.agregarMedicion("Campo Duplicado", "2024", "01", 15, 20); // Sobreescribe
        arbol.agregarMedicion("Campo Duplicado", "2024", "01", 15, 30); // Sobreescribe
        
        // Test valores extremos de precipitación
        arbol.agregarMedicion("Campo Duplicado", "2024", "02", 1, 0);     // Precipitación 0
        arbol.agregarMedicion("Campo Duplicado", "2024", "02", 2, 1000);  // Precipitación alta
        
        // Test años extremos válidos
        arbol.agregarMedicion("Campo Duplicado", "1900", "01", 1, 50);
        arbol.agregarMedicion("Campo Duplicado", "2100", "12", 31, 75);
        
        ColaStringTDA periodos = arbol.periodos();
        assert !periodos.colaVacia() : "Deberia tener periodos";
        
        System.out.println("✓ Casos Limite según Enunciado - PASS");
    }
    
    // =================== MÉTODOS AUXILIARES ===================
    
    private static int contarCampos(ABBPrecipitacionesTDA arbol) {
        if (arbol.arbolVacio()) {
            return 0;
        }
        return 1 + contarCampos(arbol.hijoIzq()) + contarCampos(arbol.hijoDer());
    }
    
    private static void verificarEstructuraBST(ABBPrecipitacionesTDA arbol) {
        if (!arbol.arbolVacio()) {
            String raiz = arbol.raiz();
            
            if (!arbol.hijoIzq().arbolVacio()) {
                String hijoIzq = arbol.hijoIzq().raiz();
                assert hijoIzq.compareToIgnoreCase(raiz) < 0 : 
                       "Violación BST: Hijo izquierdo " + hijoIzq + " >= raiz " + raiz;
                verificarEstructuraBST(arbol.hijoIzq());
            }
            
            if (!arbol.hijoDer().arbolVacio()) {
                String hijoDer = arbol.hijoDer().raiz();
                assert hijoDer.compareToIgnoreCase(raiz) > 0 : 
                       "Violación BST: Hijo derecho " + hijoDer + " <= raiz " + raiz;
                verificarEstructuraBST(arbol.hijoDer());
            }
        }
    }
} 