# Sistema de Precipitaciones Agrícolas - Documentación de Implementaciones

## Índice
1. [Arquitectura General](#arquitectura-general)
2. [Árbol de Precipitaciones](#árbol-de-precipitaciones)
3. [Estructuras de Datos Hash](#estructuras-de-datos-hash)
4. [Estructuras de Datos Lineales](#estructuras-de-datos-lineales)
5. [Utilidades](#utilidades)
6. [Algoritmos](#algoritmos)
7. [Flujo de Datos](#flujo-de-datos)
8. [Complejidades](#complejidades)

---

## Arquitectura General

El sistema implementa una estructura de datos jerárquica de 3 niveles para almacenar precipitaciones agrícolas siguiendo el patrón TDA → Implementación → Algoritmos:

```mermaid
%%{init: {'theme':'dark'}}%%
graph TB
    A["ArbolPrecipitaciones<br/>ABB de Campos"] --> B["Campo Norte"]
    A --> C["Campo Centro"] 
    A --> D["Campo Sur"]
    
    B --> E["DiccionarioSimpleString<br/>Hash Table: Período → DiccionarioSimple"]
    C --> F["DiccionarioSimpleString<br/>Hash Table: Período → DiccionarioSimple"]
    D --> G["DiccionarioSimpleString<br/>Hash Table: Período → DiccionarioSimple"]
    
    E --> H["202401<br/>DiccionarioSimple<br/>Hash Table: Día → mm"]
    E --> I["202402<br/>DiccionarioSimple<br/>Hash Table: Día → mm"]
    
    H --> J["Día 15: 25mm"]
    H --> K["Día 20: 30mm"]
    I --> L["Día 5: 15mm"]
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:3px,color:#ffffff
    style E fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style H fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style B fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style C fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style D fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
```

### Capas del Sistema:
- **TDA (Tipos de Datos Abstractos)**: Interfaces que definen las operaciones
- **Implementación**: Clases concretas que implementan los TDAs
- **Algoritmos**: Lógica de negocio que utiliza las implementaciones

### Estructura de 3 Niveles de Datos:
1. **Nivel 1**: ABB de campos agrícolas (ordenamiento lexicográfico)
2. **Nivel 2**: Hash table de períodos AAAAMM para cada campo
3. **Nivel 3**: Hash table de días→precipitaciones para cada período

---

## Árbol de Precipitaciones

### Implementación: `ArbolPrecipitaciones`
**TDA**: `ABBPrecipitacionesTDA`

### Estructura del Nodo

```mermaid
%%{init: {'theme':'dark'}}%%
graph TD
    subgraph "Clase nodoArbol"
        A["String campo<br/>Nombre del campo"]
        B["DiccionarioSimpleStringTDA<br/>mensualPrecipitaciones"]
        C["ABBPrecipitacionesTDA<br/>hijoIzquierdo"]
        D["ABBPrecipitacionesTDA<br/>hijoDerecho"]
    end
    
    A --> E["Campo Norte"]
    B --> F["Hash table de períodos"]
    C --> G["Campos lexicográficamente menores"]
    D --> H["Campos lexicográficamente mayores"]
    
    style A fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style B fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style C fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style D fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
```

```java
class nodoArbol {
    String campo;                           // Nombre del campo
    DiccionarioSimpleStringTDA mensualPrecipitaciones;  // Hash table de períodos
    ABBPrecipitacionesTDA hijoIzquierdo;   // Subárbol izquierdo
    ABBPrecipitacionesTDA hijoDerecho;     // Subárbol derecho
}
```

### Operaciones Principales

#### 1. Inicialización
```java
public void inicializar() {
    raiz = null;
}
```

#### 2. Agregar Campo
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["agregar(campo)"] --> B{"raiz == null?"}
    B -->|Si| C["Crear raíz<br/>Inicializar diccionario<br/>Crear hijos vacíos"]
    B -->|No| D{"esMayor(raiz, campo)?"}
    D -->|Si| E["hijoDerecho.agregar(campo)"]
    D -->|No| F{"esMenor(raiz, campo)?"}
    F -->|Si| G["hijoIzquierdo.agregar(campo)"]
    F -->|No| H["Campo duplicado - ignorar"]
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style C fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style E fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style G fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style H fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
```

- **Proceso**: Inserción BST estándar con comparación lexicográfica
- **Inicialización automática**: Cada nuevo campo crea su propio `DiccionarioSimpleString`
- **Comparación**: `compareToIgnoreCase()` para orden alfabético insensible a mayúsculas

#### 3. Agregar Medición con Validación
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["agregarMedicion(campo, año, mes, día, precipitación)"] --> B["Utils.Fecha.fechaValida(año, mes, día)"]
    B -->|false| C["return - ignorar medición"]
    B -->|true| D["periodo = Utils.Periodo.periodo(año, mes)"]
    D --> E["nodo = buscar(raiz, campo)"]
    E -->|null| F["agregar(campo) - crear campo"]
    E -->|found| G["nodo.mensualPrecipitaciones.agregar(periodo, día, precipitación)"]
    F --> G
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style B fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style C fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style D fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style G fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
```

```java
public void agregarMedicion(String valor, String anio, String mes, int dia, int precipitacion) {
    if(!Utils.Fecha.fechaValida(Integer.valueOf(anio), Integer.valueOf(mes), dia)){
        return; // Validación de fecha
    }
    String periodo = Utils.Periodo.periodo(anio, mes);
    // Buscar o crear campo, luego agregar medición
}
```

#### 4. Eliminación de Campo (4 Casos BST)
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["eliminar(campo)"] --> B{"Es hoja?"}
    B -->|Si| C["raiz = null"]
    B -->|No| D{"Solo hijo izquierdo?"}
    D -->|Si| E["Reemplazar con mayor del subárbol izquierdo"]
    D -->|No| F{"Solo hijo derecho?"}
    F -->|Si| G["Reemplazar con menor del subárbol derecho"]
    F -->|No| H["Reemplazar con mayor del subárbol izquierdo<br/>Transferir mensualPrecipitaciones"]
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style C fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style E fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style G fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style H fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
```

- **Caso 1**: Nodo hoja → `raiz = null`
- **Caso 2**: Solo hijo izquierdo → Reemplazar con mayor del subárbol izquierdo
- **Caso 3**: Solo hijo derecho → Reemplazar con menor del subárbol derecho  
- **Caso 4**: Ambos hijos → Usar in-order successor con transferencia de datos

### Método Crítico: `obtenerNodo()`
```java
private nodoArbol obtenerNodo(ABBPrecipitacionesTDA arbol){
    return ((ArbolPrecipitaciones)arbol).raiz;
}
```
Permite acceso directo al nodo interno para transferir `mensualPrecipitaciones` durante eliminación.

### Algoritmo de Períodos
```java
public ColaStringTDA periodos() {
    ColaStringTDA resultado = new ColaString();
    ConjuntoStringTDA periodosUnicos = new ConjuntoString();
    
    recolectarPeriodos(raiz, periodosUnicos); // DFS para recopilar
    
    // Convertir conjunto a cola
    while(!periodosUnicos.estaVacio()){
        String periodo = periodosUnicos.elegir();
        resultado.acolar(periodo);
        periodosUnicos.sacar(periodo);
    }
    return resultado;
}
```

**Característica especial**: Preserva las claves originales de cada diccionario mediante copia temporal.

---

## Estructuras de Datos Hash

### DiccionarioSimple (Hash Table de Días)
**TDA**: `DiccionarioSimpleTDA`

**Configuración:**
- Capacidad inicial: 16 buckets
- Factor de carga: 0.75
- Colisiones: Separate chaining con listas enlazadas
- Redimensionamiento: Duplica capacidad automáticamente

```mermaid
%%{init: {'theme':'dark'}}%%
graph TB
    subgraph "DiccionarioSimple - Hash Table"
        A["Bucket 0"] --> B["Día 16: 30mm"]
        C["Bucket 1"] --> D["null"]
        E["Bucket 15"] --> F["Día 15: 25mm"]
        G["Bucket 5"] --> H["Día 5: 15mm"]
        H --> H2["Día 21: 40mm"]
    end
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style C fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style E fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style G fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style B fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style D fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style F fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style H fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style H2 fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
```

```java
class nodo {
    int clave;      // Día del mes
    int valor;      // Precipitación en mm
    nodo siguiente; // Lista enlazada para colisiones
}
```

#### Función Hash para Enteros
```java
private int hash(int clave) {
    return Math.abs(clave) % capacidad;
}
```

#### Redimensionamiento Automático
- **Condición**: `size >= capacidad * FACTOR_CARGA`
- **Proceso**: Crea nuevo array, rehash todos los elementos
- **Complejidad**: O(n) amortizado a O(1)

### DiccionarioSimpleString (Hash Table de Períodos)
**TDA**: `DiccionarioSimpleStringTDA`

**Estructura de dos niveles:**
- **Nivel 1**: String período → DiccionarioSimpleTDA
- **Nivel 2**: int día → int precipitación

```mermaid
%%{init: {'theme':'dark'}}%%
graph TB
    subgraph "DiccionarioSimpleString - Hash Table de Períodos"
        A["Bucket 0"] --> B["202401 → DiccionarioSimple"]
        B --> B2["202413 → DiccionarioSimple"]
        C["Bucket 1"] --> D["202403 → DiccionarioSimple"]
        E["Bucket 2"] --> F["null"]
        G["Bucket 3"] --> H["202402 → DiccionarioSimple"]
    end
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style C fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style E fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style G fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style B fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style B2 fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style D fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style H fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
```

```java
class nodo {
    String periodo;                           // "YYYYMM" format
    DiccionarioSimpleTDA precipitacionesMes; // Hash table interno
    nodo siguiente;                          // Para colisiones
}
```

#### Función Hash para Strings
```java
private int hash(String periodo) {
    if (periodo == null) return 0;
    return Math.abs(periodo.hashCode()) % capacidad;
}
```

#### Operación Especial: `agregar(String periodo, int dia, int cantidad)`
1. Busca período en hash table principal
2. Si no existe: crea nuevo nodo con DiccionarioSimple interno
3. Agrega/actualiza día en diccionario interno
4. Maneja redimensionamiento si es necesario

---

## Estructuras de Datos Lineales

### ColaPrioridad (Lista Enlazada Ordenada)
**TDA**: `ColaPrioridadTDA`

**Ordenamiento**: Por prioridad ascendente (menores prioridades primero)

```mermaid
%%{init: {'theme':'dark'}}%%
graph LR
    A["primero"] --> B["día: 5<br/>precipitación: 15mm<br/>prioridad: 5"]
    B --> C["día: 15<br/>precipitación: 25mm<br/>prioridad: 15"]
    C --> D["día: 20<br/>precipitación: 30mm<br/>prioridad: 20"]
    D --> E["null"]
    
    style A fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style B fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style C fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style D fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style E fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
```

```java
class nodo {
    int valor;      // Precipitación
    int prioridad;  // Día (usado para ordenamiento)
    nodo siguiente;
}
```

#### Inserción Ordenada
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["acolarPrioridad(valor, prioridad)"] --> B{"Cola vacía O prioridad < primero.prioridad?"}
    B -->|Si| C["Insertar al principio"]
    B -->|No| D["Buscar posición correcta"]
    
    D --> E["while (actual.siguiente != null &&<br/>actual.siguiente.prioridad <= prioridad)"]
    E --> F["Insertar después de actual"]
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style C fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style D fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style F fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
```

```java
public void acolarPrioridad(int valor, int prioridad) {
    // Buscar posición correcta manteniendo orden ascendente
    // Insertar en posición apropiada
}
```

### ColaString (FIFO)
**TDA**: `ColaStringTDA`

**Optimización crítica**: Puntero `ultimo` para inserción O(1)

```mermaid
%%{init: {'theme':'dark'}}%%
graph LR
    A["primero"] --> B["202401"]
    B --> C["202402"] 
    C --> D["202403"]
    D --> E["null"]
    F["ultimo"] --> D
    
    style A fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style B fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style C fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style D fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style E fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style F fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
```

```java
class nodo {
    String valor;
    nodo siguiente;
}
private nodo primero;
private nodo ultimo; // Optimización para acolar O(1)
```

**Conversión automática**: `valor.toLowerCase()` en `acolar()`

### Conjuntos con Hash Tables

#### Conjunto (Hash Table de Enteros)
**TDA**: `ConjuntoTDA`
- Factor de carga: 0.85 (optimizado para `elegir()`)
- Separate chaining para colisiones

#### ConjuntoString (Hash Table de Strings)  
**TDA**: `ConjuntoStringTDA`
- Misma configuración que Conjunto
- Optimizado para selección aleatoria con `elegir()`

#### Método `elegir()` Optimizado
```mermaid
%%{init: {'theme':'dark'}}%%
graph TB
    subgraph "ConjuntoString - Hash Table"
        A["Bucket 0"] --> B["Campo Norte"]
        B --> B2["Campo Noroeste"]
        C["Bucket 1"] --> D["null"]
        E["Bucket 2"] --> F["Campo Sur"]
        G["Bucket 3"] --> H["Campo Este"]
    end
    
    I["elegir()"] --> J["Random bucket hasta encontrar no-null"]
    J --> K["Random posición en cadena de colisiones"]
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style C fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style E fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style G fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style B fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style B2 fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style F fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style H fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style I fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
```

```java
public String elegir() {
    int indice;
    do {
        indice = r.nextInt(capacidad);
    } while (buckets[indice] == null);
    
    // Selección aleatoria dentro de la cadena
    return buckets[indice].valor;
}
```

---

## Utilidades

### Utils.Fecha - Validación Completa

```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["fechaValida(año, mes, día)"] --> B{"1900 <= año <= 2100?"}
    B -->|No| C["false"]
    B -->|Si| D{"1 <= mes <= 12?"}
    D -->|No| C
    D -->|Si| E{"1 <= día <= diasEnMes(año, mes)?"}
    E -->|No| C
    E -->|Si| F["true"]
    
    E --> G["diasEnMes() considera años bisiestos"]
    G --> H{"mes == 2 && esBisiesto(año)?"}
    H -->|Si| I["29 días"]
    H -->|No| J["diasMes[mes-1]"]
    
    style A fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style C fill:#7c2626,stroke:#dc2626,stroke-width:2px,color:#ffffff
    style F fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style G fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style I fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style J fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
```

```java
public static boolean fechaValida(int anio, int mes, int dia) {
    if (anio < 1900 || anio > 2100) return false;
    if (mes < 1 || mes > 12) return false;
    if (dia < 1 || dia > diasEnMes(anio, mes)) return false;
    return true;
}
```

**Características:**
- Validación de rangos: años 1900-2100, meses 1-12
- Considera años bisiestos para febrero
- Valida días según el mes específico

#### Algoritmo Año Bisiesto
```java
private static boolean esBisiesto(int anio) {
    return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
}
```

### Utils.Periodo - Generación AAAAMM

```mermaid
%%{init: {'theme':'dark'}}%%
flowchart LR
    A["año: 2024<br/>mes: 3"] --> B["String.valueOf(año)"]
    B --> C["String.format('%02d', mes)"]
    C --> D["2024 + 03"]
    D --> E["202403"]
    
    style A fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style B fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style C fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style D fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
    style E fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
```

```java
public static String periodo(int anio, int mes) {
    return String.valueOf(anio) + String.format("%02d", mes);
}

public static String periodo(String anio, String mes) {
    return anio + String.format("%02d", Integer.parseInt(mes));
}
```

**Métodos adicionales:**
- `descomponerPeriodo(String)` → `int[]{anio, mes}`
- `obtenerMes(String)` → extrae mes del período
- `obtenerAnio(String)` → extrae año del período

---

## Algoritmos

### Clase `Algoritmos`
**Patrón**: Wrapper que utiliza `ABBPrecipitacionesTDA` interno

### Operaciones CRUD
- `agregarMedicion()`: Delega a árbol con conversión de tipos
- `eliminarMedicion()`: Elimina medición específica
- `eliminarCampo()`: Elimina campo completo

### Algoritmos de Consulta Avanzada

#### 1. `medicionesMes(int anio, int mes)`
**Objetivo**: Promedio de precipitaciones por día en todos los campos

```java
public ColaPrioridadTDA medicionesMes(int anio, int mes) {
    String periodo = Utils.Periodo.periodo(anio, mes);
    DiccionarioSimple acumuladorDias = new DiccionarioSimple();
    DiccionarioSimple contadorCampos = new DiccionarioSimple();
    
    recopilarPrecipitacionesTodosLosCampos(arbol, periodo, acumuladorDias, contadorCampos);
    
    // Calcular promedios y crear cola prioridad
}
```

#### 2. `medicionesCampoMes(String campo, int anio, int mes)`
**Objetivo**: Precipitaciones de un campo específico en un mes

#### 3. `mesMasLluvioso()`
**Objetivo**: Mes con mayor precipitación total histórica

#### 4. `promedioLluviaEnUnDia(int anio, int mes, int dia)`
**Objetivo**: Promedio de precipitación en un día específico entre todos los campos

#### 5. `campoMasLLuvisoHistoria()`
**Objetivo**: Campo con mayor precipitación acumulada total

#### 6. `camposConLLuviaMayorPromedio(int anio, int mes)`
**Objetivo**: Campos que superan el promedio de precipitación en un período

### Algoritmos de Soporte Privados
- `buscarCampo()`: Búsqueda BST de campo específico
- `calcularLluviaPeriodo()`: Suma precipitaciones de un período
- `recopilarPrecipitacionesTodosLosCampos()`: DFS para acumular datos
- `encontrarMesMasLluvioso()`: Búsqueda recursiva del máximo

---

## Flujo de Datos

### Inserción Completa de Medición
```mermaid
%%{init: {'theme':'dark'}}%%
sequenceDiagram
    participant C as Cliente
    participant A as ArbolPrecipitaciones
    participant U as Utils.Fecha
    participant N as nodoArbol
    participant DS as DiccionarioSimpleString
    participant D as DiccionarioSimple
    
    C->>A: agregarMedicion("Campo Norte", "2024", "03", 15, 25)
    A->>U: fechaValida(2024, 3, 15)
    U-->>A: true
    A->>A: buscar(raiz, "Campo Norte")
    A->>N: encontrado
    A->>DS: agregar("202403", 15, 25)
    DS->>D: new DiccionarioSimple() si período nuevo
    DS->>D: agregar(15, 25)
    D-->>DS: ✓ Hash table día→mm actualizado
    DS-->>A: ✓ Hash table período→días actualizado
    A-->>C: ✓ Medición agregada en estructura 3-nivel
```

1. **Validación**: `Utils.Fecha.fechaValida()`
2. **Conversión**: `Utils.Periodo.periodo()` 
3. **Búsqueda/Creación**: Campo en ABB
4. **Almacenamiento**: Período → Día → Precipitación (3 niveles)

### Consulta de Períodos
```mermaid
%%{init: {'theme':'dark'}}%%
sequenceDiagram
    participant C as Cliente
    participant A as ArbolPrecipitaciones
    participant CS as ConjuntoString
    participant CoS as ColaString
    participant CSC as ConjuntoString Copia
    
    C->>A: periodos()
    A->>CS: inicializar() - conjunto único
    A->>A: recolectarPeriodos(raiz, periodosUnicos)
    
    loop Para cada nodo ABB
        A->>A: claves = nodo.mensualPrecipitaciones.claves()
        A->>CSC: crear copia para preservar claves originales
        loop Mientras claves no vacío
            A->>CS: agregar(período) - elimina duplicados
            A->>CSC: agregar(período) - para restaurar
        end
        A->>A: restaurar claves originales desde copia
    end
    
    A->>CoS: inicializar()
    loop Mientras conjunto no vacío
        A->>CS: elegir() - selección aleatoria
        A->>CoS: acolar(período)
        A->>CS: sacar(período)
    end
    A-->>C: ColaString con períodos únicos
```

1. **Recolección**: DFS en ABB para obtener todos los períodos
2. **Deduplicación**: Uso de `ConjuntoString` para eliminar duplicados
3. **Preservación**: Copia temporal para mantener diccionarios originales intactos
4. **Resultado**: `ColaString` con períodos únicos

**Nota crítica**: El algoritmo preserva las claves originales de cada diccionario mediante copia temporal.

---

## Complejidades

### Tabla Completa de Complejidades

| Operación | Mejor Caso | Caso Promedio | Peor Caso | Notas |
|-----------|------------|---------------|-----------|--------|
| **ArbolPrecipitaciones** |
| agregar campo | O(1) | O(log n) | O(n) | n = campos, ABB puede degenerarse |
| buscar campo | O(1) | O(log n) | O(n) | Búsqueda recursiva en ABB |
| agregar medición | O(1) | O(log n) | O(n + k) | Incluye validación + hash |
| eliminar campo | O(log n) | O(log n) | O(n) | 4 casos BST + transferencia |
| periodos() | O(m × p) | O(m × p) | O(m × p) | m=campos, p=períodos promedio |
| precipitaciones() | O(d) | O(d) | O(d) | d = días en período específico |
| **Hash Tables (Diccionarios)** |
| agregar | O(1) | O(1) | O(k + n) | k=colisiones, n=redimensionamiento |
| recuperar | O(1) | O(1) | O(k) | k = longitud cadena colisiones |
| eliminar | O(1) | O(1) | O(k) | Búsqueda en cadena |
| obtenerClaves/claves | O(n) | O(n) | O(n) | Recorrer todos los buckets |
| redimensionar | O(n) | O(n) | O(n) | Rehash completo, amortizado |
| **ColaPrioridad** |
| acolarPrioridad | O(1) | O(n/2) | O(n) | Inserción ordenada |
| desacolar | O(1) | O(1) | O(1) | Eliminar primero |
| **ColaString** |
| acolar | O(1) | O(1) | O(1) | Puntero último crítico |
| desacolar | O(1) | O(1) | O(1) | Actualiza último si vacía |
| **Conjuntos Hash** |
| agregar | O(1) | O(1) | O(k + n) | Verificación duplicados + redim |
| pertenece | O(1) | O(1) | O(k) | Hash directo + cadena |
| elegir | O(1) | O(1) | O(c) | c = intentos hasta bucket no-null |
| sacar | O(1) | O(1) | O(k) | Eliminar de cadena |
| **Algoritmos** |
| medicionesMes | O(n × p × d) | O(n × p × d) | O(n × p × d) | DFS completo del árbol |
| mesMasLluvioso | O(n × p × d) | O(n × p × d) | O(n × p × d) | Recorrido completo |
| campoMasLluvioso | O(n × p × d) | O(n × p × d) | O(n × p × d) | Suma total por campo |

### Factores de Carga Optimizados

| Estructura | Factor Carga | Razón |
|------------|--------------|-------|
| DiccionarioSimple | 0.75 | Balance memoria/velocidad |
| DiccionarioSimpleString | 0.75 | Acceso frecuente a períodos |
| Conjunto/ConjuntoString | 0.85 | Optimiza `elegir()` - más buckets ocupados |

### Análisis de Memoria

```mermaid
%%{init: {'theme':'dark'}}%%
graph TB
    A["Memoria Total del Sistema"] --> B["ABB Campos<br/>O(n)"]
    A --> C["Hash Tables Períodos<br/>O(n × p × 1.33)"]
    A --> D["Hash Tables Días<br/>O(n × p × d × 1.33)"]
    A --> E["Estructuras Auxiliares<br/>O(p + d)"]
    
    B --> F["n = número de campos<br/>Cada nodo: 4 referencias"]
    C --> G["p = períodos por campo<br/>Factor carga 0.75 → overhead 33%"]
    D --> H["d = días por período<br/>Doble hash table anidado"]
    E --> I["Colas y conjuntos temporales<br/>para consultas"]
    
    style A fill:#7c2626,stroke:#dc2626,stroke-width:3px,color:#ffffff
    style B fill:#166534,stroke:#22c55e,stroke-width:2px,color:#ffffff
    style C fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#ffffff
    style D fill:#1e40af,stroke:#3b82f6,stroke-width:2px,color:#ffffff
    style E fill:#7c2d12,stroke:#ea580c,stroke-width:2px,color:#ffffff
```

**Memoria total del sistema**: O(n × p × d) donde:
- n = número de campos
- p = períodos promedio por campo  
- d = días promedio por período

**Distribución por estructura:**
- ABB campos: O(n)
- Hash tables períodos: O(n × p × 1.33) 
- Hash tables días: O(n × p × d × 1.33)
- Estructuras auxiliares: O(p + d)

---

## Características Destacadas de la Implementación

### 1. Robustez y Validación
- **Validación completa de fechas**: Años bisiestos, días por mes, rangos válidos (1900-2100)
- **Operaciones seguras**: Métodos no fallan con datos inválidos
- **Preservación de estado**: Algoritmos mantienen integridad de estructuras originales

### 2. Optimizaciones de Rendimiento
- **Hash tables anidados**: Acceso O(1) promedio en 3 niveles
- **Redimensionamiento automático**: Mantiene factor de carga óptimo
- **Puntero último en ColaString**: Evita O(n) en inserción
- **Separate chaining**: Manejo elegante de colisiones

### 3. Algoritmos Sofisticados
- **Eliminación BST completa**: 4 casos con transferencia de datos
- **Elegir() aleatorio optimizado**: Hash table + selección en cadenas
- **Preservación de claves**: Algoritmo de períodos mantiene diccionarios intactos
- **Casting seguro**: `obtenerNodo()` para acceso interno controlado

### 4. Decisiones de Diseño Inteligentes
- **Factor de carga diferenciado**: 0.75 vs 0.85 según uso
- **Conversión automática**: `toLowerCase()` en ColaString
- **Sobrecarga de métodos**: Utils.Periodo para flexibilidad
- **Manejo de null**: Funciones hash robustas ante valores nulos

### 5. Arquitectura en Capas
- **Separación clara**: TDA → Implementación → Algoritmos
- **Interfaces bien definidas**: Cada TDA especifica su contrato
- **Reutilización**: Implementaciones utilizadas por múltiples algoritmos
- **Mantenibilidad**: Cambios en implementación no afectan algoritmos

---

*Sistema de Precipitaciones Agrícolas - Algoritmos y Estructuras de Datos II* 