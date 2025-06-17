# DiccionarioSimpleString - Two-Level Hash Table Implementation

## Overview
`DiccionarioSimpleString` implements a specialized two-level hash table designed for storing precipitation data by periods and days. It maps **String periods** (like "202401") to **DiccionarioSimpleTDA** objects, which internally store daily precipitation measurements.

## Architecture: Nested Hash Tables

### Level 1: Period Hash Table (String → DiccionarioSimple)
- **Keys**: Period strings (format: "YYYYMM" like "202401", "202402")
- **Values**: DiccionarioSimpleTDA instances (each containing daily data)
- **Collision Resolution**: Separate chaining with linked lists

### Level 2: Daily Hash Table (int → int) 
- **Keys**: Day numbers (1-31)
- **Values**: Precipitation amounts in mm
- **Implementation**: Standard DiccionarioSimple for each period

## Data Structure Visualization

```mermaid
graph TD
    subgraph "DiccionarioSimpleString - Two-Level Hash Table"
        subgraph "Level 1: Periods Hash Table"
            B0["Index 0"] --> NULL0[null]
            B1["Index 1"] --> NULL1[null]
            B2["Index 2"] --> NULL2[null]
            B3["Index 3"] --> P1["periodo: '202401'<br/>precipitacionesMes: DiccionarioSimple<br/>siguiente: null"]
            B4["Index 4"] --> NULL4[null]
            B5["Index 5"] --> P2["periodo: '202402'<br/>precipitacionesMes: DiccionarioSimple<br/>siguiente: →"]
            P2 --> P3["periodo: '202309'<br/>precipitacionesMes: DiccionarioSimple<br/>siguiente: null"]
            B6["Index 6"] --> NULL6[null]
            B7["Index 7"] --> P4["periodo: '202403'<br/>precipitacionesMes: DiccionarioSimple<br/>siguiente: null"]
            B8["Index 8"] --> NULL8[null]
        end
        
        subgraph "Level 2A: 202401 Internal Dictionary"
            P1 -.-> J_TABLE["Jan 2024 Hash Table"]
            J_TABLE --> J3["Index 3"] --> JD15["dia: 15<br/>cantidad: 25mm"]
            J_TABLE --> J5["Index 5"] --> JD20["dia: 20<br/>cantidad: 30mm"]
            J_TABLE --> J0["Index 0"] --> JN0[null]
            J_TABLE --> J1["Index 1"] --> JN1[null]
        end
        
        subgraph "Level 2B: 202402 Internal Dictionary"
            P2 -.-> F_TABLE["Feb 2024 Hash Table"]
            F_TABLE --> F5["Index 5"] --> FD5["dia: 5<br/>cantidad: 15mm"]
            F_TABLE --> F0["Index 0"] --> FN0[null]
            F_TABLE --> F1["Index 1"] --> FN1[null]
        end
        
        subgraph "Level 2C: 202309 Internal Dictionary"
            P3 -.-> S_TABLE["Sep 2023 Hash Table"]
            S_TABLE --> S2["Index 2"] --> SD10["dia: 10<br/>cantidad: 40mm"]
            S_TABLE --> S0["Index 0"] --> SN0[null]
        end
        
        subgraph "Hash Function Examples"
            HF1["Level 1: hash(periodo) = Math.abs(periodo.hashCode()) % 16<br/>hash('202401') = 3<br/>hash('202402') = 5<br/>hash('202309') = 5 (COLLISION!)"]
            HF2["Level 2: hash(dia) = Math.abs(dia) % capacidad<br/>hash(15) = 3, hash(20) = 5, hash(5) = 5, hash(10) = 2"]
        end
        
        subgraph "Data Structure"
            NS1["Period Node Structure:<br/>String periodo<br/>DiccionarioSimpleTDA precipitacionesMes<br/>nodo siguiente"]
            NS2["Day Node Structure:<br/>int clave (day)<br/>int valor (mm)<br/>nodo siguiente"]
        end
        
        classDef emptyBucket fill:#f9f9f9,stroke:#ccc,stroke-dasharray: 5 5
        classDef periodNode fill:#e8f5e8,stroke:#2e7d32,stroke-width:2px
        classDef dayNode fill:#fff3e0,stroke:#ff6f00,stroke-width:2px
        classDef hashFunction fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px
        classDef nullPointer fill:#f5f5f5,stroke:#999,stroke-dasharray: 3 3
        classDef collision fill:#ffebee,stroke:#d32f2f,stroke-width:2px
        classDef table fill:#e3f2fd,stroke:#1976d2,stroke-width:2px
        
        class B0,B1,B2,B4,B6,B8 emptyBucket
        class P1,P2,P4 periodNode
        class P3 collision
        class JD15,JD20,FD5,SD10 dayNode
        class HF1,HF2 hashFunction
        class NULL0,NULL1,NULL2,NULL4,NULL6,NULL8,JN0,JN1,FN0,FN1,SN0 nullPointer
        class J_TABLE,F_TABLE,S_TABLE table
    end
```

#### ASCII Alternative View
```
DiccionarioSimpleString (Level 1):
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  0  │  1  │  2  │  3  │  4  │  5  │  6  │  7  │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
│null │null │null │  ●  │null │  ●  │null │  ●  │
                  │           │           │
                  ▼           ▼           ▼
            ┌────────────┐ ┌────────────┐ ┌────────────┐
            │202401      │ │202402   ●──┼─│202403      │
            │Jan 2024    │ │Feb 2024    │ │Mar 2024    │
            │Dict ●──────┼─│Dict ●──────┼─│Dict ●──────┼─
            └────────────┘ └──┼─────────┘ └────────────┘
                 │            │                │
                 ▼            ▼                ▼
         ┌──────────────┐ ┌──────────────┐ ┌──────────────┐
         │ 202309       │ │              │ │              │
         │ Sep 2023     │ │              │ │              │
         │ Dict ●───────┼─│              │ │              │
         └──────────────┘ └──────────────┘ └──────────────┘
                │
                ▼
        
Level 2 - Internal Dictionaries:

Jan 2024 (202401):           Feb 2024 (202402):
┌───┬───┬───┬───┬───┐        ┌───┬───┬───┬───┬───┐
│ 0 │ 1 │ 2 │ 3 │ 4 │        │ 0 │ 1 │ 2 │ 3 │ 4 │
└───┴───┴───┴───┴───┘        └───┴───┴───┴───┴───┘
│   │   │   │●  │●  │        │   │   │   │   │   │
          │   │   │            │   │   │   │   │
          ▼   ▼   ▼            ▼   ▼   ▼   ▼   ▼
        [15][20]             [null across all]
        25mm 30mm            
                             ┌───┬───┐
                             │ 5 │...│
                             └───┴───┘
                             │●  │
                             ▼
                            [5]
                            15mm
```

## Example Operations

### 1. String Hash Function
```java
private int hash(String periodo) {
    if (periodo == null) return 0;
    return Math.abs(periodo.hashCode()) % capacidad;
}
```

**Examples:**
- `hash("202401") = Math.abs("202401".hashCode()) % 16 = 3`
- `hash("202402") = Math.abs("202402".hashCode()) % 16 = 5`
- `hash("202309") = Math.abs("202309".hashCode()) % 16 = 5` → **COLLISION!**

### 2. Two-Level Insertion Process

When calling `agregar("202401", 15, 25)`:

1. **Level 1 - Find/Create Period**:
   ```java
   int indice = hash("202401"); // Returns 3
   // Search bucket 3 for period "202401"
   // If not found, create new period node
   ```

2. **Level 2 - Add Daily Measurement**:
   ```java
   // Get or create internal DiccionarioSimple for "202401"
   DiccionarioSimpleTDA interno = nodo.precipitacionesMes;
   interno.agregar(15, 25); // Day 15: 25mm
   ```

### 3. Collision Handling Example

Both "202402" and "202309" hash to bucket 5:
```
Bucket 5: ["202402", Feb2024Dict] → ["202309", Sep2023Dict] → null
```

### 4. Search Process

To find precipitation for day 20 in January 2024:
```java
DiccionarioSimpleTDA enero = recuperar("202401");
if (enero != null) {
    int precipitacion = enero.recuperar(20); // Returns 30mm
}
```

## Real-World Usage Examples

### Adding Precipitation Data
```java
DiccionarioSimpleString dic = new DiccionarioSimpleString();
dic.inicializarDiccionario();

// January 2024 data
dic.agregar("202401", 15, 25);  // Jan 15: 25mm
dic.agregar("202401", 20, 30);  // Jan 20: 30mm

// February 2024 data  
dic.agregar("202402", 5, 15);   // Feb 5: 15mm

// September 2023 data (collision with Feb 2024!)
dic.agregar("202309", 10, 40);  // Sep 10: 40mm
```

### Querying Data
```java
// Get all periods with data
ConjuntoStringTDA periodos = dic.claves(); // {"202401", "202402", "202309"}

// Get January data
DiccionarioSimpleTDA enero = dic.recuperar("202401");
ConjuntoTDA dias = enero.obtenerClaves(); // {15, 20}

// Get specific day
int lluvia15 = enero.recuperar(15); // 25mm
```

## Memory Layout and Efficiency

### Space Complexity
- **Level 1**: O(P) where P = number of periods
- **Level 2**: O(D) where D = total number of days with data
- **Total**: O(P + D)

### Time Complexity
| Operation | Average Case | Worst Case |
|-----------|--------------|------------|
| agregar() | O(1) | O(P + D) |
| recuperar() | O(1) | O(P) |
| eliminar() | O(1) | O(P) |
| claves() | O(P) | O(P) |

### Load Factor Management
- **Level 1**: Auto-resizes when load factor ≥ 0.75
- **Level 2**: Each internal dictionary manages its own load factor
- **Cascading Efficiency**: Both levels maintain O(1) average performance

## Key Design Benefits

1. **Temporal Organization**: Periods naturally group related daily measurements
2. **Efficient Range Queries**: Can quickly access all days in a specific month
3. **Memory Efficient**: Only allocates internal dictionaries for periods with data
4. **Collision Isolation**: Period collisions don't affect daily data access
5. **Scalable**: Handles arbitrary number of periods and days per period

## Implementation Details

### Period Node Structure
```java
class nodo {
    String periodo;                    // "YYYYMM" format
    DiccionarioSimpleTDA precipitacionesMes; // Internal hash table
    nodo siguiente;                    // For collision chaining
}
```

### Main Fields
```java
private nodo[] buckets;                // Period hash table
private int capacidad = 16;            // Current capacity
private int size = 0;                  // Number of periods
private static final double FACTOR_CARGA = 0.75;
```

This nested hash table design provides optimal performance for precipitation data storage and retrieval, with natural temporal organization and efficient memory usage. 