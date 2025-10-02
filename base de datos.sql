-- Crear usuario si no existe
DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'appuser') THEN
      CREATE ROLE appuser LOGIN PASSWORD '12345';
   END IF;
END
$$;

-- Dar permisos sobre la base
GRANT ALL PRIVILEGES ON DATABASE hilosdb TO appuser;
