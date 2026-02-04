# Etapa 1: Build
FROM node:18-alpine AS builder

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar dependencias
RUN npm ci

# Copiar el código fuente
COPY . .

# Compilar TypeScript a JavaScript
RUN npm run build

# Etapa 2: Producción
FROM node:18-alpine

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar solo dependencias de producción
RUN npm ci --only=production

# Copiar código compilado desde la etapa de build
COPY --from=builder /app/dist ./dist

# Exponer el puerto (por defecto Apollo Server usa 4000)
EXPOSE 4000

# Variables de entorno por defecto (pueden ser sobrescritas)
ENV NODE_ENV=production

# Comando para ejecutar la aplicación
CMD ["node", "dist/index.js"]
