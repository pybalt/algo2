#!/bin/bash

echo "=========================================="
echo "COMPILANDO Y EJECUTANDO TODOS LOS TESTS"
echo "=========================================="

# Compilar todos los archivos Java
echo "Compilando archivos Java..."
javac -cp . implementacion/*.java tdas/*.java test/*.java

if [ $? -eq 0 ]; then
    echo "✓ Compilación exitosa"
    echo ""
else
    echo "❌ Error en la compilación"
    exit 1
fi

# Lista de todos los tests
tests=(
    "test.TestArbolPrecipitaciones"
    "test.TestColaPrioridad"
    "test.TestColaString"
    "test.TestComparacion"
    "test.TestConjunto"
    "test.TestConjuntoString"
)

# Ejecutar cada test
for test_class in "${tests[@]}"; do
    echo "=========================================="
    echo "EJECUTANDO: $test_class"
    echo "=========================================="
    
    java "$test_class"
    
    if [ $? -eq 0 ]; then
        echo "✓ $test_class - COMPLETADO"
    else
        echo "❌ $test_class - FALLÓ"
    fi
    
    echo ""
    echo "Presiona Enter para continuar al siguiente test..."
    read
done

echo "=========================================="
echo "TODOS LOS TESTS COMPLETADOS"
echo "==========================================" 