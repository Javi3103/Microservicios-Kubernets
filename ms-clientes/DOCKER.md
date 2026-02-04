# Dockerización del Microservicio MS-Clientes

Este proyecto está completamente dockerizado y listo para ejecutarse en contenedores.

## Requisitos Previos

- Docker Desktop instalado y ejecutándose
- Docker Compose (incluido en Docker Desktop)
- Al menos 2GB de RAM disponible

## Estructura de Contenedores

El `docker-compose.yml` configura los siguientes servicios:

1. **db** - Base de datos PostgreSQL principal (puerto 5432)
2. **kong-db** - Base de datos PostgreSQL para Kong (puerto 5433)
3. **kong-migration** - Migración de Kong
4. **kong** - API Gateway Kong (puertos 8000, 8443, 8001, 8444)
5. **ms-clientes** - Microservicio de Clientes (puerto 8081)

## Ejecución

### Opción 1: Usando Docker Compose (RECOMENDADO)

```bash
# Construir e iniciar todos los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f ms-clientes

# Ver estado de los servicios
docker-compose ps

# Detener todos los servicios
docker-compose down

# Detener y eliminar volúmenes (CUIDADO: borra datos)
docker-compose down -v
```

### Opción 2: Construir imagen manualmente

```bash
# Construir la imagen
docker build -t ms-clientes:latest .

# Ejecutar el contenedor
docker run -d \
  --name ms-clientes \
  -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/db_parkin_users \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=kausa \
  ms-clientes:latest
```

## Verificación

Una vez que los servicios estén en ejecución:

### Health Check del Microservicio

```bash
curl http://localhost:8081/actuator/health
```

Respuesta esperada:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "result": 1
      }
    }
  }
}
```

### Verificar Base de Datos

```bash
# Conectarse a PostgreSQL
docker exec -it db psql -U postgres -d db_parkin_users

# Listar tablas
\dt

# Salir
\q
```

### Verificar Kong

```bash
# Estado del Admin API de Kong
curl http://localhost:8001/

# Ver servicios registrados
curl http://localhost:8001/services
```

## Configuración

Las variables de entorno están configuradas en `docker-compose.yml`:

| Variable | Valor | Descripción |
|----------|-------|-------------|
| SPRING_DATASOURCE_URL | jdbc:postgresql://db:5432/db_parkin_users | URL de conexión a BD |
| SPRING_DATASOURCE_USERNAME | postgres | Usuario de BD |
| SPRING_DATASOURCE_PASSWORD | kausa | Contraseña de BD |
| SPRING_JPA_HIBERNATE_DDL_AUTO | update | Auto-crear/actualizar tablas |
| SERVER_PORT | 8081 | Puerto de la aplicación |

Para cambiar estas variables, edita `docker-compose.yml` en la sección `ms-clientes` > `environment`.

## Troubleshooting

### El microservicio no se conecta a la BD

1. Verifica que el contenedor `db` esté en estado `healthy`:
   ```bash
   docker-compose ps
   ```

2. Revisa los logs:
   ```bash
   docker-compose logs db
   ```

3. Prueba la conexión manualmente:
   ```bash
   docker exec db pg_isready -U postgres
   ```

### Puerto ya está en uso

Cambia el mapeo de puertos en `docker-compose.yml`:

```yaml
ms-clientes:
  ports:
    - "9081:8081"  # Cambiar puerto externo
```

### Limpiar todo y empezar de cero

```bash
docker-compose down -v
docker system prune -a
docker-compose up -d
```

## Estadísticas de la Imagen

- **Tamaño Base**: ~100MB (eclipse-temurin:21-jre-alpine)
- **Tamaño Compilado**: ~200-300MB (incluyendo dependencias)
- **Tiempo de Compilación**: 2-5 minutos (primera vez)
- **Tiempo de Inicio**: 30-40 segundos

## Seguridad

Para producción:

1. **Cambiar contraseñas**:
   - Actualiza `POSTGRES_PASSWORD` y `SPRING_DATASOURCE_PASSWORD`
   - Usa variables de entorno secretas

2. **Usar secretos de Docker** (si usas Docker Swarm/Kubernetes):
   ```yaml
   secrets:
     db_password:
       file: ./secrets/db_password.txt
   ```

3. **Configurar SSL/TLS** para Kong

4. **Limitar recursos**:
   ```yaml
   ms-clientes:
     deploy:
       resources:
         limits:
           cpus: '1'
           memory: 512M
   ```

## Pasos Siguientes

1. Configura las rutas en Kong para apuntar a `ms-clientes`
2. Agrega más microservicios al `docker-compose.yml`
3. Implementa orquestación con Kubernetes (si es necesario)
