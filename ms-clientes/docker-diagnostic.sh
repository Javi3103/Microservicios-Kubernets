#!/bin/bash
# Script de diagnóstico para verificar la configuración Docker

echo "🔍 Verificando configuración Docker..."
echo ""

# Verificar Docker
echo "1️⃣  Verificando Docker..."
if command -v docker &> /dev/null; then
    echo "   ✅ Docker instalado: $(docker --version)"
else
    echo "   ❌ Docker NO instalado"
    exit 1
fi

# Verificar Docker Compose
echo ""
echo "2️⃣  Verificando Docker Compose..."
if command -v docker-compose &> /dev/null; then
    echo "   ✅ Docker Compose instalado: $(docker-compose --version)"
else
    echo "   ❌ Docker Compose NO instalado"
    exit 1
fi

# Verificar que el daemon está corriendo
echo ""
echo "3️⃣  Verificando daemon de Docker..."
if docker info &> /dev/null; then
    echo "   ✅ Docker daemon está corriendo"
else
    echo "   ❌ Docker daemon no está corriendo"
    exit 1
fi

# Verificar archivos necesarios
echo ""
echo "4️⃣  Verificando archivos de configuración..."
files=("Dockerfile" "docker-compose.yml" "pom.xml")
for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "   ✅ $file encontrado"
    else
        echo "   ❌ $file NO encontrado"
    fi
done

# Verificar puertos disponibles
echo ""
echo "5️⃣  Verificando disponibilidad de puertos..."
ports=(5432 5433 8000 8001 8081 8443 8444)
for port in "${ports[@]}"; do
    if ! nc -z localhost $port 2>/dev/null; then
        echo "   ✅ Puerto $port disponible"
    else
        echo "   ⚠️  Puerto $port ya está en uso"
    fi
done

# Verificar espacio en disco
echo ""
echo "6️⃣  Verificando espacio en disco..."
available=$(df . | awk 'NR==2 {print $4}')
if [ "$available" -gt 2097152 ]; then
    echo "   ✅ Espacio disponible: $(numfmt --to=iec-i --suffix=B $((available*1024)))"
else
    echo "   ⚠️  Espacio bajo: $(numfmt --to=iec-i --suffix=B $((available*1024)))"
fi

# Verificar memoria
echo ""
echo "7️⃣  Verificando memoria disponible..."
if command -v free &> /dev/null; then
    mem=$(free -b | awk 'NR==2 {print $7}')
    if [ "$mem" -gt 2147483648 ]; then
        echo "   ✅ Memoria disponible: $(numfmt --to=iec-i --suffix=B $mem)"
    else
        echo "   ⚠️  Memoria baja: $(numfmt --to=iec-i --suffix=B $mem)"
    fi
fi

echo ""
echo "✅ Diagnóstico completado"
echo ""
echo "Para iniciar los servicios, ejecuta:"
echo "  docker-compose up -d"
echo ""
echo "Para verificar el estado:"
echo "  docker-compose ps"
echo ""
