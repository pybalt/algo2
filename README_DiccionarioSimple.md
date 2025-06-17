# DiccionarioSimple - Hash Table Implementation

## Overview
`DiccionarioSimple` is a hash table implementation that stores key-value pairs using separate chaining for collision resolution. It provides O(1) average time complexity for basic operations (insert, search, delete).

## Internal Structure

### Hash Table Components
- **Buckets Array**: Fixed-size array of pointers to linked lists
- **Node Structure**: Each node contains key, value, and pointer to next node
- **Hash Function**: Maps keys to bucket indices using modulo operation

### Data Structure Visualization

```mermaid
graph TD
    subgraph "DiccionarioSimple Hash Table Structure"
        subgraph "Buckets Array (Capacity = 16)"
            B0["Index 0<br/>buckets[0]"]
            B1["Index 1<br/>buckets[1]"]
            B2["Index 2<br/>buckets[2]"]
            B3["Index 3<br/>buckets[3]"]
            B4["Index 4<br/>buckets[4]"]
            B5["Index 5<br/>buckets[5]"]
            B6["Index 6<br/>buckets[6]"]
            B7["Index 7<br/>buckets[7]"]
            B8["Index 8<br/>buckets[8]"]
            B9["Index 9<br/>buckets[9]"]
            B10["Index 10<br/>buckets[10]"]
            B11["Index 11<br/>buckets[11]"]
            B12["Index 12<br/>buckets[12]"]
            B13["Index 13<br/>buckets[13]"]
            B14["Index 14<br/>buckets[14]"]
            B15["Index 15<br/>buckets[15]"]
        end
        
        subgraph "Hash Function Examples"
            HF["hash(key) = Math.abs(key) % 16"]
            EX1["hash(42) = 42 % 16 = 10"]
            EX2["hash(18) = 18 % 16 = 2"]
            EX3["hash(58) = 58 % 16 = 10"]
            EX4["hash(7) = 7 % 16 = 7"]
        end
        
        subgraph "Node Structure"
            NS["class nodo {<br/>  int clave;<br/>  int valor;<br/>  nodo siguiente;<br/>}"]
        end
        
        %% Empty buckets (each with their own null pointer)
        B0 --> NULL0[null]
        B1 --> NULL1[null]
        B3 --> NULL3[null]
        B4 --> NULL4[null]
        B5 --> NULL5[null]
        B6 --> NULL6[null]
        B8 --> NULL8[null]
        B9 --> NULL9[null]
        B11 --> NULL11[null]
        B12 --> NULL12[null]
        B13 --> NULL13[null]
        B14 --> NULL14[null]
        B15 --> NULL15[null]
        
        %% Single node in bucket 2
        B2 --> N1["clave: 18<br/>valor: 100<br/>siguiente: null"]
        
        %% Single node in bucket 7
        B7 --> N2["clave: 7<br/>valor: 200<br/>siguiente: null"]
        
        %% Collision in bucket 10 (linked list)
        B10 --> N3["clave: 42<br/>valor: 300<br/>siguiente: →"]
        N3 --> N4["clave: 58<br/>valor: 400<br/>siguiente: null"]
        
        %% Style for empty buckets
        classDef emptyBucket fill:#f9f9f9,stroke:#ccc,stroke-dasharray: 5 5
        classDef filledBucket fill:#e1f5fe,stroke:#0277bd,stroke-width:2px
        classDef nodeStyle fill:#fff3e0,stroke:#ff6f00,stroke-width:2px
        classDef hashFunction fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px
        classDef collisionNode fill:#ffebee,stroke:#d32f2f,stroke-width:2px
        classDef nullPointer fill:#f5f5f5,stroke:#999,stroke-dasharray: 3 3
        
        class B0,B1,B3,B4,B5,B6,B8,B9,B11,B12,B13,B14,B15 emptyBucket
        class B2,B7,B10 filledBucket
        class N1,N2 nodeStyle
        class N3,N4 collisionNode
        class HF,EX1,EX2,EX3,EX4 hashFunction
        class NULL0,NULL1,NULL3,NULL4,NULL5,NULL6,NULL8,NULL9,NULL11,NULL12,NULL13,NULL14,NULL15 nullPointer
    end
```

#### ASCII Alternative View
```
buckets[16]:
┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│  0  │  1  │  2  │  3  │  4  │  5  │  6  │  7  │  8  │  9  │ 10  │ 11  │ 12  │ 13  │ 14  │ 15  │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
│null │null │  ●  │null │null │null │null │  ●  │null │null │  ●  │null │null │null │null │null │
           │                                   │                   │
           ▼                                   ▼                   ▼
    ┌──────────┐                      ┌──────────┐         ┌──────────┐
    │ key: 18  │                      │ key: 7   │         │ key: 42  │
    │ val: 100 │                      │ val: 200 │         │ val: 300 │
    │ next:null│                      │ next:null│         │ next: ●  │
    └──────────┘                      └──────────┘         └──────────┘
                                                                     │
                                                                     ▼
                                                              ┌──────────┐
                                                              │ key: 58  │
                                                              │ val: 400 │
                                                              │ next:null│
                                                              └──────────┘
```

## Example Operations

### 1. Hash Function
```java
private int hash(int clave) {
    return Math.abs(clave) % capacidad;
}
```

**Examples:**
- `hash(18) = 18 % 16 = 2` → Goes to bucket 2
- `hash(7) = 7 % 16 = 7` → Goes to bucket 7  
- `hash(42) = 42 % 16 = 10` → Goes to bucket 10
- `hash(58) = 58 % 16 = 10` → **COLLISION!** Also goes to bucket 10

### 2. Collision Resolution (Separate Chaining)

When two keys hash to the same bucket (like keys 42 and 58 both going to bucket 10), they form a linked list:

```
Bucket 10: [42,300] → [58,400] → null
```

### 3. Insertion Process

1. **Calculate hash**: `index = hash(key)`
2. **Search bucket**: Check if key already exists in linked list
3. **Update or Insert**: 
   - If key exists: update value
   - If key doesn't exist: create new node at head of list

### 4. Search Process

1. **Calculate hash**: `index = hash(key)`
2. **Traverse linked list**: Search through nodes in bucket[index]
3. **Return value**: If found, return associated value; otherwise return 0

## Load Factor and Resizing

- **Load Factor Threshold**: 0.75
- **Initial Capacity**: 16 buckets
- **Resizing Strategy**: Double capacity when load factor exceeds threshold
- **Rehashing**: All elements are redistributed using new capacity

### Before Resizing (Capacity = 16, Size = 12)
```
Load Factor = 12/16 = 0.75 ≥ 0.75 → RESIZE TRIGGERED
```

### After Resizing (Capacity = 32, Size = 12)
```
Load Factor = 12/32 = 0.375 < 0.75 → Optimal performance
```

## Complexity Analysis

| Operation | Average Case | Worst Case |
|-----------|--------------|------------|
| Insert    | O(1)         | O(n)       |
| Search    | O(1)         | O(n)       |
| Delete    | O(1)         | O(n)       |
| Resize    | O(n)         | O(n)       |

**Note**: Worst case occurs when all keys hash to the same bucket, creating a long linked list.

## Key Features

1. **Dynamic Resizing**: Automatically grows to maintain performance
2. **Collision Handling**: Uses separate chaining with linked lists
3. **Memory Efficient**: Only allocates space for actual elements
4. **Generic Operations**: Supports standard dictionary operations

## Implementation Details

### Node Structure
```java
class nodo {
    int clave;      // Key
    int valor;      // Value  
    nodo siguiente; // Pointer to next node in chain
}
```

### Main Fields
```java
private nodo[] buckets;                    // Array of bucket heads
private int capacidad = 16;                // Current capacity
private int size = 0;                      // Number of elements
private static final double FACTOR_CARGA = 0.75; // Load factor limit
```

This implementation provides efficient key-value storage with automatic performance optimization through dynamic resizing. 