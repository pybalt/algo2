# Sistema de Precipitaciones Agrícolas - Documentación de Implementaciones

## Índice
1. [Arquitectura General](#arquitectura-general)
2. [Árbol de Precipitaciones](#árbol-de-precipitaciones)
3. [Estructuras de Datos Hash](#estructuras-de-datos-hash)
4. [Estructuras de Datos Lineales](#estructuras-de-datos-lineales)
5. [Utilidades](#utilidades)
6. [Flujo de Datos](#flujo-de-datos)
7. [Complejidades](#complejidades)

---

## Arquitectura General

El sistema implementa una estructura de datos jerárquica de 3 niveles para almacenar precipitaciones agrícolas:

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
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:3px,color:#fff
    style E fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style H fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style B fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style C fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style D fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
```

**Estructura de 3 Niveles:**
1. **Nivel 1**: ABB de campos agrícolas (ordenamiento lexicográfico)
2. **Nivel 2**: Hash table de períodos AAAAMM para cada campo
3. **Nivel 3**: Hash table de días→precipitaciones para cada período

---

## Árbol de Precipitaciones

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
    
    style A fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style B fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style C fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style D fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
```

### Operaciones Principales

#### 1. Agregar Campo
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["agregar(campo)"] --> B{¿raiz == null?}
    B -->|Sí| C["Crear raíz<br/>Inicializar diccionario<br/>Crear hijos vacíos"]
    B -->|No| D{¿esMayor(raiz, campo)?}
    D -->|Sí| E["hijoDerecho.agregar(campo)"]
    D -->|No| F{¿esMenor(raiz, campo)?}
    F -->|Sí| G["hijoIzquierdo.agregar(campo)"]
    F -->|No| H["Campo duplicado - ignorar"]
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style C fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style E fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style G fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style H fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
```

#### 2. Agregar Medición con Validación
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["agregarMedicion(campo, año, mes, día, precipitación)"] --> B["Utils.Fecha.fechaValida(año, mes, día)"]
    B -->|false| C["return - ignorar medición"]
    B -->|true| D["periodo = Utils.Periodo.periodo(año, mes)"]
    D --> E["nodo = buscar(raiz, campo)"]
    E -->|null| F["return - campo no existe"]
    E -->|found| G["nodo.mensualPrecipitaciones.agregar(periodo, día, precipitación)"]
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style B fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style C fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style D fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style G fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
```

#### 3. Eliminación de Campo (4 Casos BST)
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["eliminar(campo)"] --> B{¿Es hoja?}
    B -->|Sí| C["raiz = null"]
    B -->|No| D{¿Solo hijo izquierdo?}
    D -->|Sí| E["Reemplazar con mayor del subárbol izquierdo"]
    D -->|No| F{¿Solo hijo derecho?}
    F -->|Sí| G["Reemplazar con menor del subárbol derecho"]
    F -->|No| H["Reemplazar con mayor del subárbol izquierdo<br/>Transferir mensualPrecipitaciones"]
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style C fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style E fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style G fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style H fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
```

### Método Crítico: obtenerNodo()
```java
private nodoArbol obtenerNodo(ABBPrecipitacionesTDA arbol){
    return ((ArbolPrecipitaciones)arbol).raiz;
}
```
Este método permite acceso directo al nodo interno para transferir `mensualPrecipitaciones` durante eliminación.

---

## Estructuras de Datos Hash

### DiccionarioSimpleString (Hash Table de Períodos)

**Configuración:**
- Capacidad inicial: 16 buckets
- Factor de carga: 0.75
- Colisiones: Separate chaining con listas enlazadas
- Redimensionamiento: Duplica capacidad automáticamente

```mermaid
%%{init: {'theme':'dark'}}%%
graph TB
    subgraph "Hash Table DiccionarioSimpleString"
        A["Bucket 0"] --> B["202401 → DiccionarioSimple"]
        B --> B2["202413 → DiccionarioSimple"]
        C["Bucket 1"] --> D["202403 → DiccionarioSimple"]
        E["Bucket 2"] --> F["null"]
        G["Bucket 3"] --> H["202402 → DiccionarioSimple"]
    end
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style C fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style E fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style G fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style B fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style B2 fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style D fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style H fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
```

#### Función Hash para Strings
```java
private int hash(String periodo) {
    if (periodo == null) return 0;
    return Math.abs(periodo.hashCode()) % capacidad;
}
```

#### Redimensionamiento Inteligente
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["size >= capacidad * 0.75"] --> B["Guardar viejosBuckets"]
    B --> C["capacidad *= 2"]
    C --> D["Crear nuevo array buckets"]
    D --> E["size = 0"]
    E --> F["Para cada viejo bucket"]
    F --> G["Recorrer lista enlazada"]
    G --> H["Calcular nuevo hash(periodo)"]
    H --> I["Reubicar nodo directamente"]
    I --> J["size++"]
    
    style A fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style C fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style H fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
```

### DiccionarioSimple (Hash Table de Días)

**Configuración idéntica** pero para mapeo `int día → int precipitación`:

```mermaid
%%{init: {'theme':'dark'}}%%
graph TB
    subgraph "DiccionarioSimple - Precipitaciones Diarias"
        A["Bucket 0"] --> B["Día 16: 30mm"]
        C["Bucket 1"] --> D["null"]
        E["Bucket 15"] --> F["Día 15: 25mm"]
        G["Bucket 5"] --> H["Día 5: 15mm"]
        H --> H2["Día 21: 40mm"]
    end
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style C fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style E fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style G fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style B fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style D fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style F fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style H fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style H2 fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
```

#### Función Hash para Enteros
```java
private int hash(int clave) {
    return Math.abs(clave) % capacidad;
}
```

---

## Estructuras de Datos Lineales

### ColaPrioridad (Lista Enlazada Ordenada)

**Ordenamiento:** Por prioridad ascendente (días cronológicos)

```mermaid
%%{init: {'theme':'dark'}}%%
graph LR
    A["primero"] --> B["día: 5<br/>precipitación: 15mm<br/>prioridad: 5"]
    B --> C["día: 15<br/>precipitación: 25mm<br/>prioridad: 15"]
    C --> D["día: 20<br/>precipitación: 30mm<br/>prioridad: 20"]
    D --> E["null"]
    
    style A fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style B fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style C fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style D fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style E fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
```

#### Inserción Ordenada
```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["acolarPrioridad(valor, prioridad)"] --> B{¿Cola vacía O prioridad < primero.prioridad?}
    B -->|Sí| C["Insertar al principio"]
    B -->|No| D["Buscar posición correcta"]
    
    D --> E["while (actual.siguiente != null &&<br/>actual.siguiente.prioridad <= prioridad)"]
    E --> F["Insertar después de actual"]
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style C fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style D fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style F fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
```

### ColaString (FIFO con Puntero Último)

**Optimización crítica:** Puntero `ultimo` para inserción O(1)

```mermaid
%%{init: {'theme':'dark'}}%%
graph LR
    A["primero"] --> B["202401"]
    B --> C["202402"] 
    C --> D["202403"]
    D --> E["null"]
    F["ultimo"] --> D
    
    style A fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style B fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style C fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style D fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style E fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style F fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
```

**Conversión automática:** `valor.toLowerCase()` en `acolar()`

### Conjuntos con Hash Tables

Ambos `Conjunto` y `ConjuntoString` usan **separate chaining**:

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
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style C fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style E fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style G fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style B fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style B2 fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style F fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style H fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style I fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
```

#### Elegir() Optimizado
```java
int indice;
do {
    indice = r.nextInt(capacidad);
} while (buckets[indice] == null);

```

**Factor de carga:** 0.85 (mayor que diccionarios para optimizar `elegir()`)

---

## Utilidades

### Utils.Fecha - Validación Completa

```mermaid
%%{init: {'theme':'dark'}}%%
flowchart TD
    A["fechaValida(año, mes, día)"] --> B{¿1900 ≤ año ≤ 2100?}
    B -->|No| C["false"]
    B -->|Sí| D{¿1 ≤ mes ≤ 12?}
    D -->|No| C
    D -->|Sí| E{¿1 ≤ día ≤ diasEnMes(año, mes)?}
    E -->|No| C
    E -->|Sí| F["true"]
    
    E --> G["diasEnMes() considera años bisiestos"]
    G --> H{¿mes == 2 && esBisiesto(año)?}
    H -->|Sí| I["29 días"]
    H -->|No| J["diasMes[mes-1]"]
    
    style A fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style C fill:#dc2626,stroke:#ef4444,stroke-width:2px,color:#fff
    style F fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style G fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style I fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style J fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
```

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
    
    style A fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style B fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style C fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style D fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
    style E fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
```

**Sobrecarga de métodos:**
```java
static String periodo(int anio, int mes)      // Para conversión interna
static String periodo(String anio, String mes) // Para API pública
```

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

### Consulta de Períodos (Algoritmo Complejo)
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

**Nota crítica:** El algoritmo preserva las claves originales de cada diccionario mediante copia temporal.

---

## Complejidades

### Tabla Completa de Complejidades

| Operación | Mejor Caso | Caso Promedio | Peor Caso | Notas Implementación |
|-----------|------------|---------------|-----------|---------------------|
| **ArbolPrecipitaciones** |
| agregar campo | O(1) | O(log n) | O(n) | n = campos, ABB puede degenerarse |
| buscar campo | O(1) | O(log n) | O(n) | Búsqueda recursiva en ABB |
| agregar medición | O(1) | O(log n + 1) | O(n + k) | Incluye validación + hash |
| eliminar campo | O(log n) | O(log n) | O(n) | Casos BST + transferencia datos |
| periodos() | O(m × p) | O(m × p) | O(m × p) | m=campos, p=períodos promedio |
| precipitaciones() | O(d) | O(d) | O(d) | d = días en período específico |
| **Hash Tables (Diccionarios)** |
| agregar | O(1) | O(1) | O(k + n) | k=colisiones, n=redimensionamiento |
| recuperar | O(1) | O(1) | O(k) | k = longitud cadena colisiones |
| eliminar | O(1) | O(1) | O(k) | Búsqueda en cadena |
| claves/obtenerClaves | O(n) | O(n) | O(n) | Debe recorrer todos los buckets |
| redimensionar | O(n) | O(n) | O(n) | Rehash completo, amortizado |
| **ColaPrioridad** |
| acolarPrioridad | O(1) | O(n/2) | O(n) | Inserción ordenada por día |
| desacolar | O(1) | O(1) | O(1) | Eliminar primero |
| **ColaString** |
| acolar | O(1) | O(1) | O(1) | Puntero último crítico |
| desacolar | O(1) | O(1) | O(1) | Actualiza puntero último si vacía |
| **Conjuntos Hash** |
| agregar | O(1) | O(1) | O(k + n) | Verificación duplicados + redim |
| pertenece | O(1) | O(1) | O(k) | Hash directo + cadena |
| elegir | O(1) | O(1) | O(c) | c = intentos hasta bucket no-null |
| sacar | O(1) | O(1) | O(k) | Eliminar de cadena |

### Análisis de Memoria por Estructura

```mermaid
%%{init: {'theme':'dark'}}%%
graph TB
    A["Memoria Total del Sistema"] --> B["ABB Campos<br/>O(n × log n)"]
    A --> C["Hash Tables Períodos<br/>O(n × p × 1.33)"]
    A --> D["Hash Tables Días<br/>O(n × p × d × 1.33)"]
    A --> E["Estructuras Auxiliares<br/>O(p + d)"]
    
    B --> F["n = número de campos<br/>Cada nodo: 4 referencias"]
    C --> G["p = períodos por campo<br/>Factor carga 0.75 → overhead 33%"]
    D --> H["d = días por período<br/>Doble hash table anidado"]
    E --> I["Colas y conjuntos temporales<br/>para consultas"]
    
    style A fill:#dc2626,stroke:#ef4444,stroke-width:3px,color:#fff
    style B fill:#059669,stroke:#10b981,stroke-width:2px,color:#fff
    style C fill:#d97706,stroke:#f59e0b,stroke-width:2px,color:#fff
    style D fill:#1e3a8a,stroke:#3b82f6,stroke-width:2px,color:#fff
    style E fill:#7c3aed,stroke:#a855f7,stroke-width:2px,color:#fff
```

### Factores de Carga Optimizados

| Estructura | Factor Carga | Razón |
|------------|--------------|-------|
| DiccionarioSimpleString | 0.75 | Balance memoria/velocidad para períodos |
| DiccionarioSimple | 0.75 | Acceso frecuente a días específicos |
| Conjunto/ConjuntoString | 0.85 | Optimiza `elegir()` - más buckets ocupados |

---

## Características Destacadas de la Implementación

### 1. Robustez y Validación
- **Validación completa de fechas:** Años bisiestos, días por mes, rangos válidos
- **Operaciones seguras:** Métodos no fallan con datos inválidos
- **Preservación de estado:** Algoritmos mantienen integridad de estructuras originales

### 2. Optimizaciones de Rendimiento
- **Hash tables anidados:** Acceso O(1) promedio en 3 niveles
- **Redimensionamiento automático:** Mantiene factor de carga óptimo
- **Puntero último en ColaString:** Evita O(n) en inserción
- **Separate chaining:** Manejo elegante de colisiones

### 3. Algoritmos Sofisticados
- **Eliminación BST completa:** 4 casos con transferencia de datos
- **Elegir() aleatorio optimizado:** Hash table + selección en cadenas
- **Preservación de claves:** Algoritmo de períodos mantiene diccionarios intactos
- **Casting seguro:** `obtenerNodo()` para acceso interno controlado

### 4. Decisiones de Diseño Inteligentes
- **Factor de carga diferenciado:** 0.75 vs 0.85 según uso
- **Conversión automática:** `toLowerCase()` en ColaString
- **Sobrecarga de métodos:** Utils.Periodo para flexibilidad
- **Manejo de null:** Funciones hash robustas ante valores nulos

---

*Documentación técnica basada en análisis completo del código fuente*  
*Sistema de Precipitaciones Agrícolas - Algoritmos y Estructuras de Datos II* 