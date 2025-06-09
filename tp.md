PLAN DE IMPLEMENTACIÓN  
(orden estrictamente secuencial, no avances al paso siguiente hasta cumplir los requisitos de “check-list”)

────────────────────────────────────────
FASE 0 – Preparación
1. Verifica que el proyecto compile (aunque los métodos estén vacíos).  
2. Configura tu IDE para usar JDK 17+ y “UTF-8” por defecto.  
3. Activa “warnings as errors” ⇒ obligará a implementar todos los métodos.

Check-list F0  
☐ `mvn clean compile` / `gradle build` sin errores de sintaxis.  
☐ Todos los paquetes permanecen intactos (`tda`, `implementacion`, `algoritmos`).  

────────────────────────────────────────
FASE 1 – Utilidades Comunes
1. Crea clase privada `FechaUtils` dentro de paquete `implementacion` (NO pública).  
   • Método `static boolean fechaValida(int anio,int mes,int dia)`  
   • Considera años bisiestos (Febrero 29).  
2. Crea clase privada `StringUtils` (implementacion)  
   • `static String periodo(int anio,int mes)` → concatena con 2 dígitos de mes.  
   • `static int[] descomponerPeriodo(String periodo)` → {anio,mes}.

Check-list F1  
☐ Tests para 28-Feb, 29-Feb (2024), 31-Abr devuelven correcto.  
☐ `periodo(2024,3)` ⇒ `"202403"` y la inversa también.  

────────────────────────────────────────
FASE 2 – Estructuras Básicas (tdas ⇢ implementacion)

Paso 2.1 Conjuntos  
• `Conjunto` y `ConjuntoString` como lista enlazada sin repetidos.  
  Operaciones O(1) amortizadas (no hace falta ordenar).  

Paso 2.2 Colas  
• `ColaString` FIFO.  
• `ColaPrioridad` min-heap simplificado con lista enlazada ordenada por
  prioridad ascendente (menor valor primero).  

Check-list F2  
☐ `colaVacia()` funciona tras varias acolar/desacolar.  
☐ `Conjunto.pertenece()` O(n) y sin NPE.  
☐ No hay métodos públicos nuevos, solo privados/helpers.

────────────────────────────────────────
FASE 3 – Diccionarios

Paso 3.1 `DiccionarioSimple`  
• Lista enlazada `(clave,valor)` con reemplazo si la clave ya existe.  

Paso 3.2 `DiccionarioSimpleString`  
• Misma idea, pero `valor` es `DiccionarioSimpleTDA`.  
• Al `agregar(periodo,dia,cant)`  
  – Si el periodo no existe ⇒ crear `DiccionarioSimple` interno.  
  – Reutiliza `fechaValida` antes de insertar.  

Check-list F3  
☐ Insertar 2 días en mismo periodo y recuperar lista de claves ok.  
☐ Eliminar periodo y luego `claves()` no lo devuelve.  

────────────────────────────────────────
FASE 4 – Árbol Binario de Búsqueda  
(Clase `ArbolPrecipitaciones`)

Estructura nodo ya provista.

Métodos a implementar (orden recomendado):

4.1 `inicializar()`  
```
raiz = null;
```

4.2 Inserción de Campo  
Pseudo-código:
```
nodo insertarRec(nodo n, String campo){
    if(n == null){
        n = new nodo();
        n.campo = campo;
        n.mensualPrecipitaciones = new DiccionarioSimpleString();
        n.mensualPrecipitaciones.inicializarDiccionario();
        n.hijoIzquierdo = new ArbolPrecipitaciones(); n.hijoIzquierdo.inicializar();
        n.hijoDerecho   = new ArbolPrecipitaciones(); n.hijoDerecho.inicializar();
    }else if(campo.compareToIgnoreCase(n.campo) < 0){
        n.hijoIzquierdo = insertarRec(n.hijoIzquierdo, campo);
    }else if(campo.compareToIgnoreCase(n.campo) > 0){
        n.hijoDerecho   = insertarRec(n.hijoDerecho, campo);
    }
    return n;
}
```

4.3 `agregarMedicion(campo,año,mes,dia,prec)`  
```
if(!fechaValida) return;
agregar(campo);              // se autogestiona si ya existe
nodo n = buscar(raiz,campo); // ABB estándar
String per = periodo(año,mes);
n.mensualPrecipitaciones.agregar(per,dia,prec);
```

4.4 Eliminación de Campo  
– Caso 0,1,2 hijos clásico (in-order successor).  

4.5 Eliminación de Medición  
– Si tras borrar el día el diccionario interno queda vacío, opcionalmente borrar periodo; si sin periodos, se puede mantener el campo.

4.6 Métodos de navegación (`raiz`, `hijoIzq`, etc.) → devuelven referencias correctas o throws si `arbolVacio`.

4.7 `periodos()`  
– In-order traversal; por cada nodo, acolar claves del diccionario interno (evitar repetidos usando `ConjuntoString`).  

4.8 `precipitaciones(periodo)`  
– Devuelve cola de días ordenados crecientemente (`ColaPrioridad`) con la precipitación del campo actual para ese periodo.

Check-list F4  
☐ Inserción en ABB conserva orden.  
☐ `esMenor/Mayor` corregidos (`compareToIgnoreCase` == <0, >0).  
☐ Todos los métodos devuelven respuesta sin NullPointer.  

────────────────────────────────────────
FASE 5 – Algoritmos Globales (`algoritmos/Algoritmos.java`)

A) CRUD simple  
• `agregarMedicion` / `eliminarMedicion` / `eliminarCampo` delegan en `ArbolPrecipitaciones`.

B) Consultas agregadas (lógica compleja)

B.1 `medicionesMes(anio,mes)`  
Pseudo-código:
```
ColaPrioridadTDA res = new ColaPrioridad(); res.inicializarCola();
int[] suma = new int[32]; int[] cont = new int[32];
DFS(nodo){
   if(n.arbolVacio()) return;
   DiccionarioSimpleTDA dic = n.mensualPrecipitaciones.recuperar(periodo);
   foreach dia in dic.obtenerClaves():
        suma[dia]  += dic.recuperar(dia);
        cont[dia]  += 1;
   DFS(hijoIzq); DFS(hijoDer);
}
for d=1..31 if(cont[d]>0) res.acolarPrioridad(suma[d]/cont[d], d);
```

B.2 `medicionesCampoMes(campo,año,mes)`  
– Buscar nodo; llamar `precipitaciones(periodo)`.

B.3 `mesMasLluvioso()`  
```
int[] lluviaMes = new int[12];
DFS(nodo){
   foreach periodo p en n.mensualPrecipitaciones.claves():
        int[] ym = descomponerPeriodo(p);
        foreach dia in dic.obtenerClaves():
             lluviaMes[ym[1]-1] += dic.recuperar(dia);
}
ColaPrioridadTDA res = new ColaPrioridad(); res.inicializarCola();
for m=1..12 res.acolarPrioridad(lluviaMes[m-1], m);
return res; // primero() será el mes con más mm porque ColaPrioridad es MIN?
// Si queremos 'mayor primero' invertir prioridades (usar -valor).
```

B.4 `promedioLluviaEnUnDia(anio,mes,dia)`  
– DFS sumando precipitación / cantidadCamposConMedicion.

B.5 `campoMasLLuvisoHistoria()`  
– DFS, acumulando total por campo; devolver máximo.

B.6 `camposConLLuviaMayorPromedio(anio,mes)`  
– Calcular promedio totalPeriodos; segunda pasada devolviendo campos que superan ese promedio (acolar en `ColaString`).

Check-list F5  
☐ Cada método recorre ABB una sola vez (O(n)).  
☐ Devuelve estructura pedida (ColaPrioridad / ColaString).  
☐ No se agregaron métodos públicos nuevos.  

────────────────────────────────────────
FASE 6 – Pruebas
1. Test unitarios JUnit5 por estructura y por algoritmo.  
2. Casos borde:  
   • Feb 29 en año no bisiesto → se ignora / lanza error.  
   • Campo inexistente en eliminación → no rompe.  
   • Colas vacías manejadas.  

Check-list F6  
☐ 90 %+ cobertura en estructuras básicas.  
☐ Todos tests verdes.  

────────────────────────────────────────
FASE 7 – Documentación y Entrega
1. Completar README con descripción y complejidad temporal (Θ).  
2. Carátula en PDF.  
3. Verificar que no existan métodos públicos extras y los nombres coincidan con los TDAs.  

FIN DEL PLAN