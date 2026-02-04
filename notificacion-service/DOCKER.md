# Servicio de Notificaciones - Docker

Microservicio de notificaciones dockerizado con NestJS, PostgreSQL y RabbitMQ.

## 🚀 Inicio Rápido

### Prerequisitos
- Docker
- Docker Compose

### Levantar el servicio

```bash
# Construir y levantar todos los contenedores
docker-compose up --build

# Levantar en segundo plano
docker-compose up -d --build
```

### Detener el servicio

```bash
# Detener los contenedores
docker-compose down

# Detener y eliminar los volúmenes (elimina los datos)
docker-compose down -v
```

## 📦 Servicios

El `docker-compose.yml` levanta los siguientes servicios:

### PostgreSQL
- **Puerto**: 5433:5432
- **Base de datos**: notificaciones_db
- **Usuario**: postgres
- **Contraseña**: postgres

### RabbitMQ
- **Puerto AMQP**: 5672
- **Puerto Management**: 15672
- **Usuario**: admin
- **Contraseña**: kausa
- **Management UI**: http://localhost:15672

### Notificacion Service
- **Puerto**: 3001
- **API Docs**: http://localhost:3001/api-docs
- **Health Check**: http://localhost:3001

## 🔧 Comandos Útiles

### Ver logs
```bash
# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f notificacion-service
```

### Reconstruir un servicio
```bash
docker-compose up -d --build notificacion-service
```

### Acceder a un contenedor
```bash
# Acceder al servicio de notificaciones
docker exec -it notificacion-service sh

# Acceder a PostgreSQL
docker exec -it notificacion-postgres psql -U postgres -d notificaciones_db
```

### Verificar estado de los contenedores
```bash
docker-compose ps
```

## 🌐 Variables de Entorno

Las variables de entorno están configuradas en el `docker-compose.yml`. Puedes crear un archivo `.env` basado en `.env.example` para desarrollo local.

## 📝 Desarrollo

Para desarrollo local sin Docker:

1. Copia `.env.example` a `.env`
2. Ajusta las variables de entorno (cambia `postgres` y `rabbitmq` a `localhost`)
3. Ejecuta `npm install`
4. Ejecuta `npm run start:dev`

## 🔍 Troubleshooting

Si tienes problemas con los contenedores:

1. Verifica que los puertos no estén en uso
2. Limpia los volúmenes: `docker-compose down -v`
3. Reconstruye las imágenes: `docker-compose build --no-cache`
4. Verifica los logs: `docker-compose logs`
