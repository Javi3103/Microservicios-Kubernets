# Informe de microservicios (Docker + Kubernetes)

Fecha: 2026-02-04

## Objetivos
- Documentar los 4 microservicios y su finalidad.
- Describir el `Dockerfile` de cada microservicio.
- Resumir lo realizado en los 10 manifiestos YAML de Kubernetes.
- Explicar cómo se subieron las imágenes a Docker Hub.

---

## Microservicios

### 1) ms-clientes (Java Spring Boot)
- **Responsabilidad:** gestión de clientes.
- **Puerto interno:** 8081.
- **Imagen Docker:** `javi3103/ms-clientes:latest`.

**Dockerfile (resumen):**
- **Multi-stage build** con Maven (compila el JAR) y runtime liviano con **Eclipse Temurin 21 JRE Alpine**.
- Copia `pom.xml`, `mvnw` y `.mvn` para **cachear dependencias**.
- Compila con `mvn clean package -DskipTests`.
- En la imagen final se copia el JAR a `/app/app.jar`.
- Expone `8081` y define variables de entorno por defecto para la BD.
- Arranca con `java -jar app.jar`.

---

### 2) ms-tickets (Node.js + TypeScript)
- **Responsabilidad:** gestión de tickets (API GraphQL/Apollo).
- **Puerto interno:** 4000.
- **Imagen Docker:** `javi3103/ms-tickets:latest`.

**Dockerfile (resumen):**
- **Multi-stage build** con Node 18 Alpine.
- Etapa build: instala dependencias (`npm ci`), compila TypeScript (`npm run build`).
- Etapa runtime: instala solo dependencias de producción (`npm ci --only=production`).
- Copia `dist` desde la etapa build.
- Expone `4000` y ejecuta `node dist/index.js`.

---

### 3) notificacion-service (Node.js + TypeScript / NestJS)
- **Responsabilidad:** servicio de notificaciones.
- **Puerto interno:** 3000.
- **Imagen Docker:** `javi3103/notificacion-service:latest`.

**Dockerfile (resumen):**
- **Multi-stage build** con Node 20 Alpine.
- Etapa build: instala dependencias y compila (`npm run build`).
- Etapa runtime: instala solo dependencias de producción.
- Copia `dist` desde la etapa build.
- Expone `3001` en el Dockerfile (el deployment usa `3000`).
- Ejecuta `node dist/main`.

---

### 4) zone-core (Java Spring Boot)
- **Responsabilidad:** lógica central de zonas.
- **Puerto interno:** 8080.
- **Imagen Docker:** `javi3103/zone-core:latest`.

**Dockerfile (resumen):**
- **Multi-stage build** con Maven 3.9 + Temurin 21.
- Compila con `./mvnw -DskipTests package`.
- Imagen final con **Eclipse Temurin 21 JRE**.
- Copia el JAR a `/app/app.jar`, expone `8080`.
- Ejecuta `java -jar /app/app.jar`.

---

## Kubernetes (10 manifiestos YAML)

### 00-namespace.yml
- Crea el **Namespace** `microservicios` para aislar todos los recursos.

### 01-ms-clientes-deployment.yml
- Deployment con **2 réplicas** y estrategia **RollingUpdate**.
- Imagen `javi3103/ms-clientes:latest`.
- Variables de entorno `SERVER_PORT` y `SPRING_PROFILES_ACTIVE`.
- **Liveness/Readiness** con `/actuator/health` en 8081.
- Límites y requests de CPU/RAM.

### 02-ms-clientes-service.yml
- Service **ClusterIP** para exponer `ms-clientes` dentro del clúster en 8081.

### 03-ms-tickets-deployment.yml
- Deployment con **2 réplicas** y RollingUpdate.
- Imagen `javi3103/ms-tickets:latest`.
- Variables `PORT=4000` y `NODE_ENV=production`.
- **Liveness/Readiness** por TCP en 4000.
- Requests/limits definidos.

### 04-ms-tickets-service.yml
- Service **ClusterIP** para `ms-tickets` en 4000.

### 05-notificacion-deployment.yml
- Deployment con **2 réplicas** y RollingUpdate.
- Imagen `javi3103/notificacion-service:latest`.
- Variables `PORT=3000` y `NODE_ENV=production`.
- **Liveness/Readiness** por TCP en 3000.
- Requests/limits definidos.

### 06-notificacion-service.yml
- Service **ClusterIP** para `notificacion-service` en 3000.

### 07-zone-core-deployment.yml
- Deployment con **2 réplicas** y RollingUpdate.
- Imagen `javi3103/zone-core:latest`.
- Variables `SERVER_PORT=8080` y `SPRING_PROFILES_ACTIVE=docker`.
- **Liveness/Readiness** con `/actuator/health` en 8080.
- Requests/limits definidos.

### 08-zone-core-service.yml
- Service **ClusterIP** para `zone-core` en 8080.

### 09-ingress.yml
- **Ingress NGINX** con `rewrite-target` y CORS habilitado.
- Rutas:
  - `/clientes` → `ms-clientes-service:8081`
  - `/tickets` → `ms-tickets-service:4000`
  - `/notificaciones` → `notificacion-service:3000`
  - `/zones` → `zone-core-service:8080`

---

## Publicación en Docker Hub

La publicación se automatizó con el script [docker-build-push.ps1](docker-build-push.ps1):
- Define el usuario `javi3103` y los 4 microservicios.
- Para cada servicio:
  1. **Build** de imagen con etiquetas `latest` y `v1.0.0`.
  2. **Push** de ambas etiquetas a Docker Hub.
  3. Muestra las imágenes generadas localmente.
- Repositorios en Docker Hub:
  - https://hub.docker.com/r/javi3103/ms-clientes
  - https://hub.docker.com/r/javi3103/ms-tickets
  - https://hub.docker.com/r/javi3103/notificacion-service
  - https://hub.docker.com/r/javi3103/zone-core

---

## Conclusión
Se documentaron los 4 microservicios, sus Dockerfiles, los 10 manifiestos de Kubernetes y el flujo de publicación en Docker Hub. El despliegue queda listo para ejecutarse en un clúster con namespace dedicado y exposición vía Ingress.