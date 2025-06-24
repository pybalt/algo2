# Sistema de Precipitaciones Agrícolas

## Descripción del Proyecto

Este proyecto implementa un sistema para gestionar y analizar datos de precipitaciones agrícolas utilizando estructuras de datos avanzadas. El sistema permite almacenar mediciones de lluvia por campo, período (año-mes) y día, proporcionando algoritmos eficientes para consultas y análisis estadísticos.

## Arquitectura del Sistema

El proyecto sigue una arquitectura de 3 capas bien definida:

### 1. TDA (Tipos de Datos Abstractos) - `tdas/`
Interfaces que definen las operaciones sin especificar la implementación:
- `ABBPrecipitacionesTDA.java` - Árbol binario de búsqueda para campos
- `DiccionarioSimpleTDA.java` - Diccionario clave-valor entero
- `DiccionarioSimpleStringTDA.java` - Diccionario con claves string
- `ColaPrioridadTDA.java` - Cola con prioridades
- `ColaStringTDA.java` - Cola FIFO de strings
- `ConjuntoTDA.java` - Conjunto de enteros
- `ConjuntoStringTDA.java` - Conjunto de strings

### 2. Implementación - `implementacion/`
Clases concretas que implementan los TDAs usando estructuras de datos eficientes:
- `ArbolPrecipitaciones.java` - ABB con hash tables anidados
- `DiccionarioSimple.java` - Hash table con separate chaining
- `DiccionarioSimpleString.java` - Hash table de dos niveles
- `ColaPrioridad.java` - Lista enlazada ordenada
- `ColaString.java` - Lista enlazada FIFO con puntero último
- `Conjunto.java` - Hash table para enteros
- `ConjuntoString.java` - Hash table para strings
- `Utils.java` - Utilidades para validación y conversión

### 3. Algoritmos - `algoritmos/`
Lógica de negocio que utiliza las implementaciones:
- `Algoritmos.java` - Algoritmos de análisis de precipitaciones

## Estructura de Datos Principal

El sistema utiliza una estructura jerárquica de 3 niveles:

```
ArbolPrecipitaciones (ABB)
├── Campo1
│   └── DiccionarioSimpleString
│       ├── "202401" → DiccionarioSimple
│       │   ├── 15 → 25mm
│       │   └── 20 → 30mm
│       └── "202402" → DiccionarioSimple
│           └── 5 → 15mm
├── Campo2
│   └── DiccionarioSimpleString
│       └── "202401" → DiccionarioSimple
│           └── 10 → 40mm
└── ...
```

**Nivel 1**: ABB de campos (ordenamiento lexicográfico)  
**Nivel 2**: Hash table de períodos AAAAMM  
**Nivel 3**: Hash table de días → precipitaciones (mm)

## Uso del Sistema

### Ejemplo Básico

```java
import implementacion.ArbolPrecipitaciones;
import algoritmos.Algoritmos;

// Crear el sistema
ArbolPrecipitaciones arbol = new ArbolPrecipitaciones();
arbol.inicializar();

Algoritmos algoritmos = new Algoritmos(arbol);

// Agregar mediciones
algoritmos.agregarMedicion("Campo Norte", 2024, 1, 15, 25); // 25mm el 15 de enero
algoritmos.agregarMedicion("Campo Norte", 2024, 1, 20, 30); // 30mm el 20 de enero
algoritmos.agregarMedicion("Campo Sur", 2024, 1, 15, 20);   // 20mm el 15 de enero

// Consultas
ColaPrioridadTDA mediciones = algoritmos.medicionesMes(2024, 1);
String campoMasLluvioso = algoritmos.campoMasLLuvisoHistoria();
float promedio = algoritmos.promedioLluviaEnUnDia(2024, 1, 15);
```

### Operaciones Disponibles

#### Operaciones CRUD
- `agregarMedicion(campo, año, mes, día, precipitación)` - Agregar medición
- `eliminarMedicion(campo, año, mes, día)` - Eliminar medición específica
- `eliminarCampo(campo)` - Eliminar campo completo

#### Consultas de Análisis
- `medicionesMes(año, mes)` - Promedio de precipitaciones por día
- `medicionesCampoMes(campo, año, mes)` - Precipitaciones de un campo específico
- `mesMasLluvioso()` - Mes con mayor precipitación histórica
- `promedioLluviaEnUnDia(año, mes, día)` - Promedio en un día específico
- `campoMasLLuvisoHistoria()` - Campo con mayor precipitación total
- `camposConLLuviaMayorPromedio(año, mes)` - Campos sobre el promedio

## Validaciones

El sistema incluye validaciones robustas:

### Fechas
- **Años**: 1900-2100
- **Meses**: 1-12
- **Días**: 1-31 (según el mes, considerando años bisiestos)

### Años Bisiestos
- Divisible por 4 Y no por 100, O divisible por 400
- Febrero tiene 29 días en años bisiestos

### Períodos
- Formato automático: AAAAMM (ej: "202401" para enero 2024)
- Validación antes de almacenar

## Características Técnicas

### Complejidades
- **Agregar medición**: O(log n) promedio (n = campos)
- **Consultas por campo**: O(log n) para encontrar + O(1) para acceder datos
- **Análisis globales**: O(n × p × d) donde n=campos, p=períodos, d=días

### Optimizaciones
- **Hash tables**: Factor de carga 0.75 con redimensionamiento automático
- **Separate chaining**: Manejo eficiente de colisiones
- **Puntero último**: ColaString con inserción O(1)
- **Elegir() optimizado**: Selección aleatoria eficiente en conjuntos

### Memoria
- **Espacial**: O(n × p × d) donde solo se almacenan datos existentes
- **Temporal**: Estructuras auxiliares para consultas complejas

## Compilación y Ejecución

### Compilar el Proyecto
```bash
# Compilar todas las clases
javac -d . src/tdas/*.java src/implementacion/*.java src/algoritmos/*.java

# Ejecutar ejemplo
java Main
```

## Estructura de Archivos

```
src/
├── algoritmos/
│   └── Algoritmos.java           # Lógica de negocio
├── implementacion/
│   ├── ArbolPrecipitaciones.java # ABB principal
│   ├── DiccionarioSimple.java    # Hash table enteros
│   ├── DiccionarioSimpleString.java # Hash table strings
│   ├── ColaPrioridad.java        # Cola ordenada
│   ├── ColaString.java           # Cola FIFO
│   ├── Conjunto.java             # Set de enteros
│   ├── ConjuntoString.java       # Set de strings
│   └── Utils.java                # Utilidades
├── tdas/
│   ├── ABBPrecipitacionesTDA.java
│   ├── DiccionarioSimpleTDA.java
│   ├── DiccionarioSimpleStringTDA.java
│   ├── ColaPrioridadTDA.java
│   ├── ColaStringTDA.java
│   ├── ConjuntoTDA.java
│   └── ConjuntoStringTDA.java
```

## Casos de Uso

### 1. Análisis Estacional
```java
// Encontrar el mes más lluvioso históricamente
int mes = algoritmos.mesMasLluvioso();
System.out.println("Mes más lluvioso: " + mes);
```

### 2. Comparación de Campos
```java
// Campos que superan el promedio en enero 2024
ColaString campos = algoritmos.camposConLLuviaMayorPromedio(2024, 1);
while (!campos.colaVacia()) {
    System.out.println("Campo destacado: " + campos.primero());
    campos.desacolar();
}
```

### 3. Análisis Diario
```java
// Promedio de lluvia en un día específico
float promedio = algoritmos.promedioLluviaEnUnDia(2024, 1, 15);
System.out.println("Promedio 15 enero: " + promedio + "mm");
```

### 4. Ranking de Campos
```java
// Campo con mayor precipitación histórica
String campoTop = algoritmos.campoMasLLuvisoHistoria();
System.out.println("Campo más lluvioso: " + campoTop);
```

## Consideraciones de Diseño

### Principios Aplicados
1. **Separación de responsabilidades**: Cada capa tiene una función específica
2. **Encapsulación**: Los TDAs ocultan detalles de implementación
3. **Reutilización**: Las implementaciones son genéricas y reutilizables
4. **Eficiencia**: Estructuras optimizadas para casos de uso específicos

### Decisiones Técnicas
- **ABB vs Hash**: ABB para orden lexicográfico de campos
- **Hash tables anidados**: Acceso eficiente a datos temporales
- **Factor de carga diferenciado**: 0.75 para diccionarios, 0.85 para conjuntos
- **Validación temprana**: Fechas inválidas se rechazan inmediatamente

---

*Sistema desarrollado para Algoritmos y Estructuras de Datos II* 