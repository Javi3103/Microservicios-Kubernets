# Docker - Microservicio de Tickets

Este documento explica cómo ejecutar el microservicio de tickets usando Docker.

## Requisitos Previos

- Docker instalado (v20.10 o superior)
- Docker Compose instalado (v2.0 o superior)

## Archivos Docker

- `Dockerfile`: Configuración para construir la imagen del microservicio
- `docker-compose.yml`: Orquestación de servicios (app + PostgreSQL)
- `.dockerignore`: Archivos excluidos de la imagen Docker
- `.env.example`: Plantilla de variables de entorno

## Uso Rápido

### 1. Configurar Variables de Entorno

Copia el archivo de ejemplo y ajusta las variables según tu entorno:

```bash
cp .env.example .env
```

Edita `.env` y configura las URLs de los otros microservicios.

### 2. Iniciar con Docker Compose (Recomendado)

Ejecuta el microservicio junto con PostgreSQL:

```bash
docker-compose up -d
```

Esto iniciará:
- PostgreSQL en el puerto 5432
- Microservicio GraphQL en el puerto 4000

### 3. Verificar el Estado

```bash
docker-compose ps
```

### 4. Ver Logs

```bash
# Todos los servicios
docker-compose logs -f

# Solo la aplicación
docker-compose logs -f app

# Solo la base de datos
docker-compose logs -f postgres
```

### 5. Detener los Servicios

```bash
docker-compose down
```

Para eliminar también los volúmenes (datos de la BD):

```bash
docker-compose down -v
```

## Uso con Solo Dockerfile

Si prefieres ejecutar solo la aplicación sin la base de datos:

### 1. Construir la Imagen

```bash
docker build -t ms-tickets:latest .
```

### 2. Ejecutar el Contenedor

```bash
docker run -d \
  -p 4000:4000 \
  -e DB_HOST=tu_host_db \
  -e DB_PORT=5432 \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  -e DB_NAME=tickets_db \
  -e ZONE_SERVICE_URL=http://tu-zone-service/graphql \
  -e PERSONA_SERVICE_URL=http://tu-persona-service/graphql \
  --name ms-tickets \
  ms-tickets:latest
```

## Configuración de Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `DB_HOST` | Host de PostgreSQL | `localhost` |
| `DB_PORT` | Puerto de PostgreSQL | `5432` |
| `DB_USERNAME` | Usuario de la BD | `postgres` |
| `DB_PASSWORD` | Contraseña de la BD | `postgres` |
| `DB_NAME` | Nombre de la base de datos | `tickets_db` |
| `ZONE_SERVICE_URL` | URL del servicio de zonas | - |
| `PERSONA_SERVICE_URL` | URL del servicio de personas | - |
| `NODE_ENV` | Entorno de ejecución | `production` |

## Acceso al Servicio

Una vez iniciado, el servicio GraphQL estará disponible en:

- **URL**: http://localhost:4000
- **GraphQL Playground**: http://localhost:4000/graphql (si está habilitado)

## Solución de Problemas

### El contenedor no inicia

Revisa los logs:
```bash
docker-compose logs app
```

### Error de conexión a la base de datos

Verifica que PostgreSQL esté ejecutándose:
```bash
docker-compose ps postgres
```

Verifica la salud de PostgreSQL:
```bash
docker exec ms-tickets-db pg_isready -U postgres
```

### Reconstruir después de cambios en el código

```bash
docker-compose up -d --build
```

## Desarrollo

Para desarrollo, es recomendable usar el comando `npm run dev` localmente en lugar de Docker, ya que permite hot-reload.

## Producción

Para producción, considera:

1. Usar un registro de contenedores (Docker Hub, AWS ECR, etc.)
2. Configurar variables de entorno mediante secretos
3. Usar un orquestador (Kubernetes, Docker Swarm)
4. Implementar health checks
5. Configurar límites de recursos (CPU, memoria)

## Comandos Útiles

```bash
# Reconstruir imagen
docker-compose build

# Ver uso de recursos
docker stats ms-tickets-app

# Acceder al contenedor
docker exec -it ms-tickets-app sh

# Limpiar recursos no usados
docker system prune -a
```
