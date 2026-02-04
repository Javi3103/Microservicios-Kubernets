# Script para gestionar Docker Compose del Microservicio MS-Clientes
# Uso: .\docker-commands.ps1 [up|down|logs|ps|build|clean|test]

param(
    [string]$command = "help"
)

function Show-Help {
    Write-Host @"
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
  clean      Detener y limpiar volumenes (CUIDADO: borra datos)
  test       Verificar health de los servicios
  shell-ms   Acceder a la shell del contenedor ms-clientes
  shell-db   Acceder a PostgreSQL
  help       Mostrar esta ayuda

Ejemplos:
  .\docker-commands.ps1 up
  .\docker-commands.ps1 logs-ms
  .\docker-commands.ps1 test
"@
}

function Start-Services {
    Write-Host "[*] Iniciando servicios..." -ForegroundColor Green
    docker-compose up -d
    Start-Sleep -Seconds 3
    Show-Status
}

function Stop-Services {
    Write-Host "[X] Deteniendo servicios..." -ForegroundColor Yellow
    docker-compose down
}

function Restart-Services {
    Write-Host "[>] Reiniciando servicios..." -ForegroundColor Cyan
    docker-compose restart
    Start-Sleep -Seconds 3
    Show-Status
}

function Show-Logs {
    Write-Host "[L] Mostrando logs..." -ForegroundColor Cyan
    docker-compose logs -f
}

function Show-Logs-MS {
    Write-Host "[L] Mostrando logs de ms-clientes..." -ForegroundColor Cyan
    docker-compose logs -f ms-clientes
}

function Show-Status {
    Write-Host "`n[S] Estado de los contenedores:" -ForegroundColor Cyan
    docker-compose ps
}

function Build-Image {
    Write-Host "[B] Compilando imagen..." -ForegroundColor Green
    docker-compose build
}

function Clean-All {
    Write-Host "[C] Limpiando volumenes y contenedores..." -ForegroundColor Red
    Write-Host "[!] ADVERTENCIA: Se eliminaran todos los datos en volumenes!" -ForegroundColor Red
    $confirm = Read-Host "Estás seguro? (s/n)"
    
    if ($confirm -eq "s") {
        docker-compose down -v
        Write-Host "[OK] Limpieza completada" -ForegroundColor Green
    } else {
        Write-Host "[CANCEL] Operación cancelada" -ForegroundColor Yellow
    }
}

function Test-Services {
    Write-Host "`n[T] Verificando servicios..." -ForegroundColor Cyan
    
    # Esperar un poco para que los servicios esten listos
    Write-Host "Esperando a que los servicios esten listos..." -ForegroundColor Yellow
    Start-Sleep -Seconds 10
    
    # Health check del microservicio
    Write-Host "`n[HC] Health Check de ms-clientes:" -ForegroundColor Cyan
    try {
        $response = Invoke-RestMethod -Uri "http://localhost:8081/actuator/health" -ErrorAction Stop
        Write-Host "[OK] ms-clientes esta UP" -ForegroundColor Green
        Write-Host ($response | ConvertTo-Json) -ForegroundColor Green
    } catch {
        Write-Host "[ERROR] ms-clientes no responde" -ForegroundColor Red
        Write-Host $_.Exception.Message -ForegroundColor Red
    }
    
    # Kong Admin API
    Write-Host "`n[KONG] Kong Admin API:" -ForegroundColor Cyan
    try {
        $response = Invoke-RestMethod -Uri "http://localhost:8001/" -ErrorAction Stop
        Write-Host "[OK] Kong esta UP" -ForegroundColor Green
    } catch {
        Write-Host "[WARN] Kong no responde (puede estar iniciandose)" -ForegroundColor Yellow
    }
    
    # PostgreSQL
    Write-Host "`n[DB] Base de Datos PostgreSQL:" -ForegroundColor Cyan
    try {
        $output = docker exec db pg_isready -U postgres 2>&1
        if ($output -contains "accepting connections") {
            Write-Host "[OK] PostgreSQL esta UP" -ForegroundColor Green
        }
    } catch {
        Write-Host "[ERROR] PostgreSQL no responde" -ForegroundColor Red
    }
    
    Write-Host "`n" -ForegroundColor Cyan
    Show-Status
}

function Access-MS-Shell {
    Write-Host "[SH] Accediendo al contenedor ms-clientes..." -ForegroundColor Cyan
    docker exec -it ms-clientes /bin/sh
}

function Access-DB-Shell {
    Write-Host "[DB] Conectando a PostgreSQL..." -ForegroundColor Cyan
    docker exec -it db psql -U postgres -d db_parkin_users
}

# Ejecutar comando
switch ($command.ToLower()) {
    "up" { Start-Services }
    "down" { Stop-Services }
    "restart" { Restart-Services }
    "logs" { Show-Logs }
    "logs-ms" { Show-Logs-MS }
    "ps" { Show-Status }
    "build" { Build-Image }
    "clean" { Clean-All }
    "test" { Test-Services }
    "shell-ms" { Access-MS-Shell }
    "shell-db" { Access-DB-Shell }
    "help" { Show-Help }
    default { 
        Write-Host "❌ Comando no reconocido: $command" -ForegroundColor Red
        Show-Help
    }
}
