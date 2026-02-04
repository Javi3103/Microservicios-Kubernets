# Script para construir y pushear imágenes a Docker Hub
# Usuario: javi3103

$DOCKERHUB_USER = "javi3103"
$MICROSERVICIOS = @(
    @{name="ms-clientes"; path="ms-clientes"; port="8081"},
    @{name="ms-tickets"; path="ms-tickets"; port="4000"},
    @{name="notificacion-service"; path="notificacion-service"; port="3000"},
    @{name="zone-core"; path="zone_core"; port="8080"}
)

Write-Host "======================================"
Write-Host "Docker Build & Push Script"
Write-Host "Usuario: $DOCKERHUB_USER"
Write-Host "======================================"
Write-Host ""

# Verificar si estamos logueados en Docker Hub
Write-Host "Verificando login en Docker Hub..."
docker info | Out-Null
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: No estas logueado en Docker Hub"
    Write-Host "Ejecuta: docker login"
    exit 1
}

Write-Host "Login verificado"
Write-Host ""

# Recorrer cada microservicio
foreach ($ms in $MICROSERVICIOS) {
    $serviceName = $ms.name
    $servicePath = $ms.path
    $port = $ms.port
    $imageName = "$DOCKERHUB_USER/$serviceName"
    $fullImageName = "${imageName}:latest"
    
    Write-Host "======================================"
    Write-Host "Procesando: $serviceName"
    Write-Host "Ruta: $servicePath"
    Write-Host "Imagen: $fullImageName"
    Write-Host "======================================"
    
    # Cambiar al directorio del microservicio
    if (-not (Test-Path $servicePath)) {
        Write-Host "ERROR: La carpeta $servicePath no existe"
        continue
    }
    
    Push-Location $servicePath
    
    # Build de la imagen
    Write-Host ""
    Write-Host "[1/3] Construyendo imagen Docker..."
    docker build -t $fullImageName -t "${imageName}:v1.0.0" .
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Build exitoso"
    }
    else {
        Write-Host "Error en build"
        Pop-Location
        continue
    }
    
    # Push a Docker Hub
    Write-Host ""
    Write-Host "[2/3] Pusheando a Docker Hub..."
    docker push $fullImageName
    docker push "${imageName}:v1.0.0"
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Push exitoso"
    }
    else {
        Write-Host "Error en push"
        Pop-Location
        continue
    }
    
    Write-Host ""
    Write-Host "[3/3] Informacion de la imagen:"
    docker images | Select-String $imageName
    
    Write-Host ""
    Write-Host "$serviceName completado exitosamente"
    Write-Host ""
    
    Pop-Location
}

Write-Host ""
Write-Host "======================================"
Write-Host "Todas las imagenes han sido pusheadas"
Write-Host "======================================"
Write-Host ""
Write-Host "Puedes verlas en:"
foreach ($ms in $MICROSERVICIOS) {
    $repoUrl = "https://hub.docker.com/r/$DOCKERHUB_USER/" + $ms.name
    Write-Host "  $repoUrl"
}
