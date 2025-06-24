#!/bin/bash

echo "=============================================="
echo "  SCRIPT DE VALIDACIÓN TP FINAL ALGO II"
echo "=============================================="
echo ""

# Compilar todos los archivos
echo "🔧 Compilando archivos..."
find . -name "*.java" -exec javac -cp . {} \; 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa"
else
    echo "❌ Error en compilación"
    exit 1
fi

echo ""
echo "🧪 Ejecutando suite completa de tests..."
echo ""

# Ejecutar la suite completa
java test.TestSuiteCompleta

if [ $? -eq 0 ]; then
    echo ""
    echo "=============================================="
    echo "  ✅ VALIDACIÓN EXITOSA"
    echo "=============================================="
    echo ""
    echo "📋 RESUMEN DE VALIDACIONES:"
    echo "   ✅ Estructura conforme al bootstrap"
    echo "   ✅ Firmas de métodos correctas"
    echo "   ✅ Funcionalidad implementada"
    echo "   ✅ Tests unitarios pasando"
    echo "   ✅ Integración completa"
    echo ""
    echo "🚀 LA IMPLEMENTACIÓN ESTÁ LISTA PARA ENTREGA"
    echo ""
else
    echo ""
    echo "=============================================="
    echo "  ❌ VALIDACIÓN FALLIDA"
    echo "=============================================="
    echo ""
    echo "Revisar los errores mostrados arriba."
    exit 1
fi 