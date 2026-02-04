# Microservicio de Clientes - API REST

API REST para gestión de personas (naturales y jurídicas) y sus vehículos (motos y automóviles).

## 🚀 Tecnologías

- **Java 21**
- **Spring Boot 3.x**
- **PostgreSQL**
- **Maven 3.9.6**
- **Hibernate/JPA**

## 📋 Requisitos Previos

- Java 21 instalado
- PostgreSQL 18 corriendo
- Maven 3.9.6+
- Puerto 8081 disponible

## ⚙️ Configuración

### Base de Datos

Configuración en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_parkin_users
spring.datasource.username=postgres
spring.datasource.password=1234
server.port=8081
```

### Ejecutar el Proyecto

```bash
mvn spring-boot:run
```

O con Java 21 específico:

```bash
export JAVA_HOME=/ruta/a/java21
mvn spring-boot:run
```

## 📡 API Endpoints

### PERSONAS

#### 1. Listar Todas las Personas
```http
GET /api/personas
```

**Respuesta:** `200 OK`
```json
[
  {
    "id": "uuid",
    "identificacion": "1234567890",
    "nombre": "Juan",
    "email": "juan@example.com",
    ...
  }
]
```

---

#### 2. Buscar Persona por Identificación
```http
GET /api/personas/identificacion/{identificacion}
```

**Parámetros:**
- `identificacion` (path): Número de cédula o RUC

**Respuesta:** `200 OK`

---

#### 3. Crear Persona Natural
```http
POST /api/personas/natural
Content-Type: application/json
```

**Body:**
```json
{
  "identificacion": "1234567890",
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@example.com",
  "telefono": "0987654321",
  "direccion": "Av. Amazonas y Naciones Unidas, Quito",
  "fechaNacimiento": "1990-05-15T00:00:00",
  "genero": "MASCULINO"
}
```

**Respuesta:** `201 Created`

---

#### 4. Crear Persona Jurídica
```http
POST /api/personas/juridica
Content-Type: application/json
```

**Body:**
```json
{
  "identificacion": "1234567890001",
  "nombre": "TechCorp S.A.",
  "email": "contacto@techcorp.com",
  "telefono": "0987654322",
  "direccion": "Av. República y Amazonas, Quito",
  "razonSocial": "TechCorp S.A.",
  "representanteLegal": "María González",
  "actividadEconomica": "Desarrollo de software"
}
```

**Respuesta:** `201 Created`

---

#### 5. Actualizar Persona Natural
```http
PUT /api/personas/natural/{id}
Content-Type: application/json
```

**Parámetros:**
- `id` (path): UUID de la persona

**Body:** Mismo formato que crear

**Respuesta:** `200 OK`

---

#### 6. Actualizar Persona Jurídica
```http
PUT /api/personas/juridica/{id}
Content-Type: application/json
```

**Parámetros:**
- `id` (path): UUID de la persona

**Body:** Mismo formato que crear

**Respuesta:** `200 OK`

---

#### 7. Eliminar Persona (Borrado Lógico)
```http
DELETE /api/personas/{id}
```

**Parámetros:**
- `id` (path): UUID de la persona

**Respuesta:** `204 No Content`

---

### VEHÍCULOS

#### 1. Listar Todos los Vehículos
```http
GET /api/vehiculos
```

**Respuesta:** `200 OK`

---

#### 2. Obtener Vehículo por ID
```http
GET /api/vehiculos/{id}
```

**Parámetros:**
- `id` (path): UUID del vehículo

**Respuesta:** `200 OK`

---

#### 3. Buscar Vehículo por Placa
```http
GET /api/vehiculos/placa/{placa}
```

**Parámetros:**
- `placa` (path): Placa del vehículo (ej: ABC123)

**Respuesta:** `200 OK`

---

#### 4. Buscar Vehículos por Propietario
```http
GET /api/vehiculos/propietario/{idPropietario}
```

**Parámetros:**
- `idPropietario` (path): UUID de la persona propietaria

**Respuesta:** `200 OK`

---

#### 5. Buscar Vehículos por Marca
```http
GET /api/vehiculos/marca/{marca}
```

**Parámetros:**
- `marca` (path): Nombre de la marca (ej: Toyota)

**Respuesta:** `200 OK`

---

#### 6. Listar Vehículos Activos
```http
GET /api/vehiculos/activos
```

**Respuesta:** `200 OK`

---

#### 7. Crear Moto
```http
POST /api/vehiculos/moto
Content-Type: application/json
```

**Body:**
```json
{
  "placa": "ABC123",
  "marca": "Yamaha",
  "modelo": "YZF-R3",
  "color": "Azul",
  "anioFabricacion": 2023,
  "idPropietario": "uuid-persona",
  "tieneCasco": true,
  "cilindraje": 321,
  "tipo": "DEPORTIVA",
  "tipoCombustible": "GASOLINA",
  "tieneABS": true,
  "numeroRuedas": 2,
  "capacidadTanque": 14.0,
  "tipoFrenos": "Disco"
}
```

**Tipos de Moto:**
- `DEPORTIVA`
- `CRUCERO`
- `TOURING`
- `SCOOTER`
- `ENDURO`

**Tipos de Combustible:**
- `GASOLINA`
- `DIESEL`
- `ELECTRICO`
- `HIBRIDO`
- `GAS`

**Respuesta:** `201 Created`

---

#### 8. Crear Automóvil
```http
POST /api/vehiculos/automovil
Content-Type: application/json
```

**Body:**
```json
{
  "placa": "XYZ789",
  "marca": "Toyota",
  "modelo": "Corolla",
  "color": "Gris",
  "anioFabricacion": 2024,
  "idPropietario": "uuid-persona",
  "tipoAutomovil": "SEDAN",
  "tipoCombustible": "HIBRIDO",
  "cilindraje": 1.8,
  "suspensionDeportiva": false,
  "traccion": "DELANTERA",
  "tieneBalde": false,
  "numeroPuertas": 4,
  "numeroAsientos": 5,
  "capacidadTanque": 50.0
}
```

**Tipos de Automóvil:**
- `SEDAN`
- `SUV`
- `HATCHBACK`
- `COUPE`
- `PICKUP`
- `VAN`

**Tipos de Tracción:**
- `DELANTERA`
- `TRASERA`
- `4X4`
- `AWD`

**Respuesta:** `201 Created`

---

#### 9. Actualizar Moto
```http
PUT /api/vehiculos/moto/{id}
Content-Type: application/json
```

**Parámetros:**
- `id` (path): UUID del vehículo

**Body:** Mismo formato que crear

**Respuesta:** `200 OK`

---

#### 10. Actualizar Automóvil
```http
PUT /api/vehiculos/automovil/{id}
Content-Type: application/json
```

**Parámetros:**
- `id` (path): UUID del vehículo

**Body:** Mismo formato que crear

**Respuesta:** `200 OK`

---

#### 11. Eliminar Vehículo (Borrado Lógico)
```http
DELETE /api/vehiculos/{id}
```

**Parámetros:**
- `id` (path): UUID del vehículo

**Respuesta:** `204 No Content`

---

## 📦 Colección de Postman

Importa la colección `MS_Clientes_Collection.postman_collection.json` en Postman para probar todos los endpoints.

### Variables de Entorno
- `{{personaId}}` - UUID de una persona
- `{{vehiculoId}}` - UUID de un vehículo

## 🗂️ Estructura del Proyecto

```
ms-clientes/
├── src/main/java/ec/edu/espe/ms_clientes/
│   ├── controllers/          # Endpoints REST
│   │   ├── PersonaController.java
│   │   └── VehiculoController.java
│   ├── services/             # Lógica de negocio
│   │   ├── PersonaService.java
│   │   ├── VehiculoService.java
│   │   └── impl/
│   ├── repositories/         # Acceso a datos
│   │   ├── PersonaRepository.java
│   │   └── VehiculoRepository.java
│   ├── models/               # Entidades JPA
│   │   ├── Persona.java
│   │   ├── PersonaNatural.java
│   │   ├── PersonaJuridica.java
│   │   ├── Vehiculo.java
│   │   ├── Moto.java
│   │   └── Automovil.java
│   └── dto/                  # Data Transfer Objects
│       ├── requests/
│       └── responses/
└── src/main/resources/
    └── application.properties
```

## 🔒 Validaciones

Todas las peticiones POST y PUT validan:
- Campos requeridos no nulos
- Formatos de email
- Rangos numéricos válidos
- Longitudes de cadenas
- Formatos de fecha

## 🐛 Manejo de Errores

La API retorna los siguientes códigos de estado:

- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado
- `204 No Content` - Eliminación exitosa
- `400 Bad Request` - Datos inválidos
- `404 Not Found` - Recurso no encontrado
- `500 Internal Server Error` - Error del servidor

## 👨‍💻 Desarrollo

### Compilar el proyecto
```bash
mvn clean compile
```

### Ejecutar tests
```bash
mvn test
```

### Generar JAR
```bash
mvn clean package
```

## 📝 Notas

- Los IDs son UUIDs generados automáticamente
- Las eliminaciones son lógicas (soft delete)
- Todas las fechas usan formato ISO-8601
- La base de datos usa PostgreSQL 18

## 🆘 Solución de Problemas

### Error: "release version 21 not supported"
Asegúrate de tener Java 21 instalado y configurado en `JAVA_HOME`.

### Error: "Connection refused"
Verifica que PostgreSQL esté corriendo en el puerto 5432.

### Error: "authentication failed"
Revisa las credenciales en `application.properties`.

## 📄 Licencia

Este proyecto es parte del curso de Aplicaciones Distribuidas - ESPE.
