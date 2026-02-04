# Script de utilidades para Docker Compose
# Uso: .\docker-utils.ps1 [comando]

param(
    [Parameter(Position=0)]
    [string]$Command = "help"
)

function Show-Help {
    Write-Host "=====================================" -ForegroundColor Cyan
    Write-Host "  MS-Clientes Docker Utilities" -ForegroundColor Cyan
    Write-Host "=====================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Comandos disponibles:" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "  start      - Iniciar todos los servicios" -ForegroundColor Green
    Write-Host "  stop       - Detener todos los servicios" -ForegroundColor Green
    Write-Host "  restart    - Reiniciar todos los servicios" -ForegroundColor Green
    Write-Host "  rebuild    - Reconstruir e iniciar servicios" -ForegroundColor Green
    Write-Host "  status     - Ver estado de los servicios" -ForegroundColor Green
    Write-Host "  logs       - Ver logs de todos los servicios" -ForegroundColor Green
    Write-Host "  logs-app   - Ver logs del microservicio" -ForegroundColor Green
    Write-Host "  logs-kong  - Ver logs de Kong" -ForegroundColor Green
    Write-Host "  logs-db    - Ver logs de la base de datos" -ForegroundColor Green
    Write-Host "  clean      - Detener y eliminar volúmenes (⚠️ BORRA DATOS)" -ForegroundColor Green
    Write-Host "  shell-app  - Acceder a shell del microservicio" -ForegroundColor Green
    Write-Host "  shell-db   - Acceder a PostgreSQL" -ForegroundColor Green
    Write-Host "  test-kong  - Probar conexión a Kong" -ForegroundColor Green
    Write-Host "  help       - Mostrar esta ayuda" -ForegroundColor Green
    Write-Host ""
}

function Start-Services {
    Write-Host "🚀 Iniciando servicios..." -ForegroundColor Cyan
    docker-compose up -d
    Write-Host "✅ Servicios iniciados" -ForegroundColor Green
}

function Stop-Services {
    Write-Host "🛑 Deteniendo servicios..." -ForegroundColor Cyan
    docker-compose down
    Write-Host "✅ Servicios detenidos" -ForegroundColor Green
}

function Restart-Services {
    Write-Host "🔄 Reiniciando servicios..." -ForegroundColor Cyan
    docker-compose restart
    Write-Host "✅ Servicios reiniciados" -ForegroundColor Green
}

function Rebuild-Services {
    Write-Host "🔨 Reconstruyendo e iniciando servicios..." -ForegroundColor Cyan
    docker-compose up --build -d
    Write-Host "✅ Servicios reconstruidos e iniciados" -ForegroundColor Green
}

function Show-Status {
    Write-Host "📊 Estado de los servicios:" -ForegroundColor Cyan
    docker-compose ps
}

function Show-Logs {
    Write-Host "📜 Mostrando logs (Ctrl+C para salir)..." -ForegroundColor Cyan
    docker-compose logs -f
}

function Show-AppLogs {
    Write-Host "📜 Logs de ms-clientes (Ctrl+C para salir)..." -ForegroundColor Cyan
    docker-compose logs -f ms-clientes
}

function Show-KongLogs {
    Write-Host "📜 Logs de Kong (Ctrl+C para salir)..." -ForegroundColor Cyan
    docker-compose logs -f kong
}

function Show-DbLogs {
    Write-Host "📜 Logs de PostgreSQL (Ctrl+C para salir)..." -ForegroundColor Cyan
    docker-compose logs -f db
}

function Clean-Environment {
    Write-Host "⚠️  ADVERTENCIA: Esto eliminará todos los datos!" -ForegroundColor Red
    $confirmation = Read-Host "¿Estás seguro? (si/no)"
    if ($confirmation -eq 'si') {
        Write-Host "🧹 Limpiando entorno..." -ForegroundColor Cyan
        docker-compose down -v
        Write-Host "✅ Entorno limpiado" -ForegroundColor Green
    } else {
        Write-Host "❌ Operación cancelada" -ForegroundColor Yellow
    }
}

function Shell-App {
    Write-Host "🐚 Accediendo al contenedor ms-clientes..." -ForegroundColor Cyan
    docker exec -it ms-clientes sh
}

function Shell-Db {
    Write-Host "🐚 Accediendo a PostgreSQL..." -ForegroundColor Cyan
    docker exec -it parkin-db psql -U postgres -d db_parkin_users
}

function Test-Kong {
    Write-Host "🧪 Probando Kong..." -ForegroundColor Cyan
    Write-Host ""
    Write-Host "1. Status de Kong:" -ForegroundColor Yellow
    try {
        $response = Invoke-RestMethod -Uri "http://localhost:8001/status" -Method Get
        Write-Host "✅ Kong está funcionando" -ForegroundColor Green
        $response | ConvertTo-Json
    } catch {
        Write-Host "❌ Kong no responde" -ForegroundColor Red
    }
    
    Write-Host ""
    Write-Host "2. Servicios configurados:" -ForegroundColor Yellow
    try {
        $services = Invoke-RestMethod -Uri "http://localhost:8001/services" -Method Get
        Write-Host "✅ Servicios encontrados:" -ForegroundColor Green
        $services.data | ForEach-Object { Write-Host "  - $($_.name)" -ForegroundColor Cyan }
    } catch {
        Write-Host "❌ No se pudieron obtener los servicios" -ForegroundColor Red
    }
    
    Write-Host ""
    Write-Host "3. Rutas configuradas:" -ForegroundColor Yellow
    try {
        $routes = Invoke-RestMethod -Uri "http://localhost:8001/routes" -Method Get
        Write-Host "✅ Rutas encontradas:" -ForegroundColor Green
        $routes.data | ForEach-Object { Write-Host "  - $($_.name): $($_.paths)" -ForegroundColor Cyan }
    } catch {
        Write-Host "❌ No se pudieron obtener las rutas" -ForegroundColor Red
    }
}

# Ejecutar comando
switch ($Command.ToLower()) {
    "start"      { Start-Services }
    "stop"       { Stop-Services }
    "restart"    { Restart-Services }
    "rebuild"    { Rebuild-Services }
    "status"     { Show-Status }
    "logs"       { Show-Logs }
    "logs-app"   { Show-AppLogs }
    "logs-kong"  { Show-KongLogs }
    "logs-db"    { Show-DbLogs }
    "clean"      { Clean-Environment }
    "shell-app"  { Shell-App }
    "shell-db"   { Shell-Db }
    "test-kong"  { Test-Kong }
    "help"       { Show-Help }
    default      { 
        Write-Host "❌ Comando no reconocido: $Command" -ForegroundColor Red
        Write-Host ""
        Show-Help 
    }
}
