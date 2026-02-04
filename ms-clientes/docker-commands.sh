#!/bin/bash

# Script para gestionar Docker Compose del Microservicio MS-Clientes
# Uso: bash docker-commands.sh [up|down|logs|ps|build|clean|test]

command="${1:-help}"

show_help() {
    cat << "EOF"
╔════════════════════════════════════════════════════════════════╗
║   MS-Clientes Docker Management Script                         ║
╚════════════════════════════════════════════════════════════════╝

Comandos disponibles:

  up         Iniciar todos los servicios (docker-compose up -d)
  down       Detener todos los servicios
  restart    Reiniciar todos los servicios
  logs       Ver logs en tiempo real (Ctrl+C para salir)
  logs-ms    Ver logs solo del microservicio
  ps         Ver estado de los contenedores
  build      Construir la imagen
  clean      Detener y limpiar volúmenes (CUIDADO: borra datos)
  test       Verificar health de los servicios
  shell-ms   Acceder a la shell del contenedor ms-clientes
  shell-db   Acceder a PostgreSQL
  help       Mostrar esta ayuda

Ejemplos:
  bash docker-commands.sh up
  bash docker-commands.sh logs-ms
  bash docker-commands.sh test
EOF
}

start_services() {
    echo "🚀 Iniciando servicios..."
    docker-compose up -d
    sleep 3
    show_status
}

stop_services() {
    echo "⏹️  Deteniendo servicios..."
    docker-compose down
}

restart_services() {
    echo "🔄 Reiniciando servicios..."
    docker-compose restart
    sleep 3
    show_status
}

show_logs() {
    echo "📋 Mostrando logs..."
    docker-compose logs -f
}

show_logs_ms() {
    echo "📋 Mostrando logs de ms-clientes..."
    docker-compose logs -f ms-clientes
}

show_status() {
    echo
    echo "📊 Estado de los contenedores:"
    docker-compose ps
}

build_image() {
    echo "🔨 Compilando imagen..."
    docker-compose build
}

clean_all() {
    echo "🧹 Limpiando volúmenes y contenedores..."
    echo "⚠️  ADVERTENCIA: Se eliminarán todos los datos en volúmenes!"
    read -p "¿Estás seguro? (s/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Ss]$ ]]; then
        docker-compose down -v
        echo "✅ Limpieza completada"
    else
        echo "❌ Operación cancelada"
    fi
}

test_services() {
    echo
    echo "🧪 Verificando servicios..."
    echo "Esperando a que los servicios estén listos..."
    sleep 10
    
    echo
    echo "🏥 Health Check de ms-clientes:"
    if curl -s http://localhost:8081/actuator/health | grep -q "UP"; then
        echo "✅ ms-clientes está UP"
        curl -s http://localhost:8081/actuator/health | jq .
    else
        echo "❌ ms-clientes no responde"
    fi
    
    echo
    echo "🦍 Kong Admin API:"
    if curl -s http://localhost:8001/ > /dev/null; then
        echo "✅ Kong está UP"
    else
        echo "⚠️  Kong no responde (puede estar iniciándose)"
    fi
    
    echo
    echo "🐘 Base de Datos PostgreSQL:"
    if docker exec db pg_isready -U postgres > /dev/null 2>&1; then
        echo "✅ PostgreSQL está UP"
    else
        echo "❌ PostgreSQL no responde"
    fi
    
    echo
    show_status
}

access_ms_shell() {
    echo "📦 Accediendo al contenedor ms-clientes..."
    docker exec -it ms-clientes /bin/sh
}

access_db_shell() {
    echo "💾 Conectando a PostgreSQL..."
    docker exec -it db psql -U postgres -d db_parkin_users
}

# Ejecutar comando
case "$command" in
    up) start_services ;;
    down) stop_services ;;
    restart) restart_services ;;
    logs) show_logs ;;
    logs-ms) show_logs_ms ;;
    ps) show_status ;;
    build) build_image ;;
    clean) clean_all ;;
    test) test_services ;;
    shell-ms) access_ms_shell ;;
    shell-db) access_db_shell ;;
    help) show_help ;;
    *) 
        echo "❌ Comando no reconocido: $command"
        show_help
        ;;
esac
