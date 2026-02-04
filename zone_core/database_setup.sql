-- Script para crear la base de datos PostgreSQL
-- Ejecutar desde psql o pgAdmin

-- Crear la base de datos
CREATE DATABASE db_parkin_zone;

-- Crear el usuario admin si no existe
-- Si ya tienes el usuario admin, puedes omitir estas líneas
-- CREATE USER admin WITH PASSWORD 'kausa';

-- Otorgar permisos al usuario
GRANT ALL PRIVILEGES ON DATABASE db_parkin_zone TO admin;

-- Conectar a la base de datos
\c db_parkin_zone;

-- Otorgar permisos en el esquema public
GRANT ALL ON SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;

-- Verificar la conexión
SELECT 'Database setup completed successfully!' as status;