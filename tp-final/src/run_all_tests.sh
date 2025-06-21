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
passed=0
failed=0

for test_class in "${tests[@]}"; do
    echo "=========================================="
    echo "EJECUTANDO: $test_class"
    echo "=========================================="
    
    java "$test_class"
    
    if [ $? -eq 0 ]; then
        echo "✓ $test_class - PASS"
        ((passed++))
    else
        echo "❌ $test_class - FAIL"
        ((failed++))
    fi
    echo ""
done

echo "=========================================="
echo "RESUMEN DE TESTS"
echo "=========================================="
echo "Tests ejecutados: $((passed + failed))"
echo "Tests exitosos: $passed"
echo "Tests fallidos: $failed"
echo "==========================================" 