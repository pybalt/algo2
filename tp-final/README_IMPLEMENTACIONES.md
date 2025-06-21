# Sistema de Precipitaciones Agrícolas - Documentación de Implementaciones

## Índice
1. [Arquitectura General](#arquitectura-general)
2. [Árbol de Precipitaciones](#árbol-de-precipitaciones)
3. [Estructuras de Datos de Soporte](#estructuras-de-datos-de-soporte)
4. [Utilidades](#utilidades)
5. [Flujo de Datos](#flujo-de-datos)
6. [Complejidades](#complejidades)

---

## Arquitectura General

El sistema implementa una estructura de datos jerárquica para almacenar y consultar precipitaciones en campos agrícolas organizados por períodos temporales.

```mermaid
graph TB
    A[ArbolPrecipitaciones<br/>ABB de Campos] --> B[Campo Norte]
    A --> C[Campo Centro] 
    A --> D[Campo Sur]
    
    B --> E[DiccionarioSimpleString<br/>Períodos → Días]
    C --> F[DiccionarioSimpleString<br/>Períodos → Días]
    D --> G[DiccionarioSimpleString<br/>Períodos → Días]
    
    E --> H[202401<br/>DiccionarioSimple]
    E --> I[202402<br/>DiccionarioSimple]
    
    H --> J[Día 15: 25mm]
    H --> K[Día 20: 30mm]
    I --> L[Día 5: 15mm]
    
    style A fill:#e1f5fe
    style E fill:#f3e5f5
    style H fill:#e8f5e8
```

---

## Árbol de Precipitaciones

### Estructura Principal

La clase `ArbolPrecipitaciones` implementa un **Árbol Binario de Búsqueda (ABB)** donde cada nodo representa un campo agrícola.

```mermaid
graph TD
    subgraph "Nodo del ABB"
        A[String campo<br/>Campo Norte]
        B[DiccionarioSimpleStringTDA<br/>mensualPrecipitaciones]
        C[ABBPrecipitacionesTDA<br/>hijoIzquierdo]
        D[ABBPrecipitacionesTDA<br/>hijoDerecho]
    end
    
    A --> E[Nombre del campo]
    B --> F[Períodos y precipitaciones]
    C --> G[Campos menores lexicográficamente]
    D --> H[Campos mayores lexicográficamente]
    
    style A fill:#ffcdd2
    style B fill:#c8e6c9
    style C fill:#fff3e0
    style D fill:#fff3e0
```

### Operaciones Principales

#### 1. Agregar Campo
```mermaid
flowchart TD
    A[agregar campo] --> B{¿Árbol vacío?}
    B -->|Sí| C[Crear raíz con campo]
    B -->|No| D{¿Campo < raíz?}
    D -->|Sí| E[Insertar en hijo izquierdo]
    D -->|No| F{¿Campo > raíz?}
    F -->|Sí| G[Insertar en hijo derecho]
    F -->|No| H[Campo duplicado - ignorar]
    
    C --> I[Inicializar diccionarios]
    C --> J[Crear hijos vacíos]
```

#### 2. Agregar Medición
```mermaid
flowchart TD
    A[agregarMedicion] --> B[Validar fecha<br/>Utils.Fecha.fechaValida]
    B -->|Inválida| C[Ignorar medición]
    B -->|Válida| D[Generar período<br/>Utils.Periodo.periodo]
    D --> E[Buscar campo en ABB]
    E -->|No existe| F[Ignorar medición]
    E -->|Existe| G[Agregar al diccionario<br/>del campo]
    
    G --> H[DiccionarioSimpleString<br/>agregar período, día, precipitación]
```

#### 3. Obtener Períodos
```mermaid
flowchart TD
    A[periodos] --> B[Crear ColaString resultado]
    A --> C[Crear ConjuntoString únicos]
    A --> D[recolectarPeriodos recursivo]
    
    D --> E[Recorrer ABB in-order]
    E --> F[Para cada campo:<br/>obtener claves del diccionario]
    F --> G[Agregar períodos al conjunto]
    
    G --> H[Transferir conjunto → cola]
    H --> I[Retornar cola de períodos]
```

---

## Estructuras de Datos de Soporte

### DiccionarioSimpleString

Implementa un **Hash Table** que mapea períodos (strings) a diccionarios de precipitaciones diarias.

```mermaid
graph LR
    subgraph "Hash Table - DiccionarioSimpleString"
        A[Bucket 0] --> B[202401 → DiccionarioSimple]
        C[Bucket 1] --> D[202403 → DiccionarioSimple]
        E[Bucket 2] --> F[null]
        G[Bucket 3] --> H[202402 → DiccionarioSimple]
    end
    
    B --> I[Hash: abs 202401 hashCode mod 16]
    D --> J[Hash: abs 202403 hashCode mod 16]
    H --> K[Hash: abs 202402 hashCode mod 16]
    
    style A fill:#e3f2fd
    style C fill:#e3f2fd
    style E fill:#ffebee
    style G fill:#e3f2fd
```

#### Función Hash para Strings
```mermaid
flowchart LR
    A[Período 202401] --> B[periodo.hashCode]
    B --> C[Math.abs resultado]
    C --> D[resultado mod capacidad]
    D --> E[Índice del bucket]
    
    style A fill:#fff3e0
    style E fill:#e8f5e8
```

### DiccionarioSimple

Hash Table que mapea días (int) a precipitaciones (int).

```mermaid
graph TB
    subgraph "DiccionarioSimple - Precipitaciones Diarias"
        A[Bucket 0] --> B[Día 16: 30mm]
        C[Bucket 1] --> D[null]
        E[Bucket 2] --> F[Día 15: 25mm]
        G[Bucket 3] --> H[Día 5: 15mm]
    end
    
    B --> I[Hash: abs 16 % 16 = 0]
    F --> J[Hash: abs 15 % 16 = 15]
    H --> K[Hash: abs 5 % 16 = 5]
```

#### Redimensionamiento Automático
```mermaid
flowchart TD
    A[Agregar elemento] --> B{size >= capacidad * 0.75?}
    B -->|No| C[Insertar normalmente]
    B -->|Sí| D[Redimensionar]
    
    D --> E[Duplicar capacidad<br/>16 → 32 → 64...]
    E --> F[Crear nuevo array]
    F --> G[Rehash todos los elementos<br/>con nueva capacidad]
    G --> H[Insertar elemento]
```

### ColaPrioridad

Lista enlazada ordenada por prioridad (días cronológicos).

```mermaid
graph LR
    A[primero] --> B[día: 5<br/>precipitación: 15mm]
    B --> C[día: 15<br/>precipitación: 25mm]
    C --> D[día: 20<br/>precipitación: 30mm]
    D --> E[null]
    
    style B fill:#c8e6c9
    style C fill:#c8e6c9  
    style D fill:#c8e6c9
```

#### Inserción Ordenada
```mermaid
flowchart TD
    A[acolarPrioridad día, precipitación] --> B{¿Cola vacía O día < primero?}
    B -->|Sí| C[Insertar al inicio]
    B -->|No| D[Buscar posición correcta]
    
    D --> E[Recorrer hasta encontrar<br/>posición donde día <= siguiente.día]
    E --> F[Insertar en posición encontrada]
```

### ColaString

Cola FIFO para períodos con conversión automática a minúsculas.

```mermaid
graph LR
    A[primero] --> B[202401]
    B --> C[202402] 
    C --> D[202403]
    D --> E[null]
    F[ultimo] --> D
    
    style B fill:#fff3e0
    style C fill:#fff3e0
    style D fill:#fff3e0
```

### Conjuntos (Conjunto y ConjuntoString)

Listas enlazadas sin duplicados con selección aleatoria.

```mermaid
graph TB
    subgraph "ConjuntoString"
        A[primero] --> B[Campo Norte]
        B --> C[Campo Sur]
        C --> D[Campo Este]
        D --> E[null]
    end
    
    F[elegir] --> G[Random.nextInt cantidad]
    G --> H[Navegar hasta posición aleatoria]
    H --> I[Retornar elemento]
    
    style B fill:#e1f5fe
    style C fill:#e1f5fe
    style D fill:#e1f5fe
```

---

## Utilidades

### Utils.Fecha

Validación completa de fechas con soporte para años bisiestos.

```mermaid
flowchart TD
    A[fechaValida año, mes, día] --> B{¿1900 ≤ año ≤ 2100?}
    B -->|No| C[false]
    B -->|Sí| D{¿1 ≤ mes ≤ 12?}
    D -->|No| C
    D -->|Sí| E{¿1 ≤ día ≤ diasEnMes?}
    E -->|No| C
    E -->|Sí| F[true]
    
    E --> G[diasEnMes considera<br/>años bisiestos]
    G --> H{¿Febrero + año bisiesto?}
    H -->|Sí| I[29 días]
    H -->|No| J[Días normales del mes]
```

#### Algoritmo Año Bisiesto
```mermaid
flowchart TD
    A[esBisiesto año] --> B{¿año % 4 == 0?}
    B -->|No| C[false]
    B -->|Sí| D{¿año % 100 == 0?}
    D -->|No| E[true]
    D -->|Sí| F{¿año % 400 == 0?}
    F -->|Sí| E
    F -->|No| C
    
    style E fill:#c8e6c9
    style C fill:#ffcdd2
```

### Utils.Periodo

Generación de períodos en formato AAAAMM.

```mermaid
flowchart LR
    A[año: 2024<br/>mes: 3] --> B[String.valueOf año]
    B --> C[String.format 02d mes]
    C --> D[2024 + 03]
    D --> E[202403]
    
    style A fill:#fff3e0
    style E fill:#e8f5e8
```

---

## Flujo de Datos

### Inserción de Medición Completa
```mermaid
sequenceDiagram
    participant C as Cliente
    participant A as ArbolPrecipitaciones
    participant U as Utils.Fecha
    participant D as DiccionarioSimpleString
    participant DS as DiccionarioSimple
    
    C->>A: agregarMedicion(Campo Norte, 2024, 03, 15, 25)
    A->>U: fechaValida(2024, 3, 15)
    U-->>A: true
    A->>A: buscar(Campo Norte)
    A->>D: agregar(202403, 15, 25)
    D->>DS: agregar(15, 25)
    DS-->>D: ✓ Agregado
    D-->>A: ✓ Período actualizado
    A-->>C: ✓ Medición agregada
```

### Consulta de Períodos
```mermaid
sequenceDiagram
    participant C as Cliente
    participant A as ArbolPrecipitaciones
    participant CS as ConjuntoString
    participant CoS as ColaString
    
    C->>A: periodos()
    A->>CS: inicializar()
    A->>A: recolectarPeriodos(raiz, conjunto)
    loop Para cada campo en ABB
        A->>A: obtener claves diccionario
        A->>CS: agregar(período)
    end
    A->>CoS: inicializar()
    loop Mientras conjunto no vacío
        A->>CS: elegir()
        A->>CoS: acolar(período)
        A->>CS: sacar(período)
    end
    A-->>C: ColaString con períodos únicos
```

---

## Complejidades

### Tabla de Complejidades por Operación

| Operación | Mejor Caso | Caso Promedio | Peor Caso | Notas |
|-----------|------------|---------------|-----------|--------|
| **ArbolPrecipitaciones** |
| agregar campo | O(1) | O(log n) | O(n) | n = número de campos |
| buscar campo | O(1) | O(log n) | O(n) | ABB puede degenerarse |
| agregar medición | O(1) | O(log n + 1) | O(n + k) | k = colisiones hash |
| periodos() | O(n) | O(n) | O(n) | n = total de períodos |
| eliminar campo | O(1) | O(log n) | O(n) | Incluye reorganización ABB |
| **DiccionarioSimpleString** |
| agregar | O(1) | O(1) | O(k) | k = tamaño bucket |
| recuperar | O(1) | O(1) | O(k) | Con redimensionamiento |
| eliminar | O(1) | O(1) | O(k) | Amortizado por rehashing |
| **DiccionarioSimple** |
| agregar | O(1) | O(1) | O(k) | Hash table con rehashing |
| recuperar | O(1) | O(1) | O(k) | Búsqueda en bucket |
| eliminar | O(1) | O(1) | O(k) | k = colisiones |
| **ColaPrioridad** |
| acolar | O(1) | O(n) | O(n) | n = elementos en cola |
| desacolar | O(1) | O(1) | O(1) | Eliminar primero |
| **Conjuntos y Colas** |
| agregar/acolar | O(1) | O(1) | O(n) | Verificación duplicados |
| pertenece | O(1) | O(n/2) | O(n) | Búsqueda lineal |
| elegir | O(1) | O(n/2) | O(n) | Acceso aleatorio |

### Análisis de Memoria

```mermaid
graph TB
    A[Memoria Total] --> B[ABB Campos<br/>O log n]
    A --> C[Hash Tables Períodos<br/>O m * log p]
    A --> D[Hash Tables Días<br/>O m * p * d]
    A --> E[Estructuras Auxiliares<br/>O m + p]
    
    B --> F[n = número de campos]
    C --> G[m = campos, p = períodos promedio]
    D --> H[d = días promedio por período]
    E --> I[Colas y conjuntos temporales]
    
    style A fill:#ffcdd2
    style B fill:#c8e6c9
    style C fill:#fff3e0
    style D fill:#e1f5fe
    style E fill:#f3e5f5
```

**Donde:**
- **n** = número de campos agrícolas
- **m** = número de campos con datos
- **p** = número promedio de períodos por campo
- **d** = número promedio de días con datos por período
- **k** = número de colisiones en hash table

---

## Características de Implementación

### Ventajas del Diseño
1. **Escalabilidad**: Hash tables con redimensionamiento automático
2. **Eficiencia**: Acceso O(1) promedio a datos específicos
3. **Robustez**: Validación completa de fechas y manejo de errores
4. **Flexibilidad**: Estructura jerárquica adaptable a diferentes consultas
5. **Memoria**: Uso eficiente con estructuras dinámicas

### Decisiones de Diseño
1. **ABB para campos**: Permite consultas ordenadas y búsqueda eficiente
2. **Hash tables anidados**: Optimiza acceso por período y día
3. **Validación estricta**: Garantiza integridad de datos temporales
4. **Estructuras auxiliares**: Facilitan consultas complejas y agregaciones
5. **Factor de carga 0.75**: Balance entre memoria y rendimiento

---

*Documentación generada para el Sistema de Precipitaciones Agrícolas*  
*Algoritmos y Estructuras de Datos II - 2024* 