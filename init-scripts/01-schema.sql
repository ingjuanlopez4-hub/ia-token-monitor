-- 1. Asegurarnos de que la extensión de TimescaleDB esté activa
CREATE EXTENSION IF NOT EXISTS timescaledb;

-- 2. Crear la tabla base
CREATE TABLE IF NOT EXISTS token_usage_logs (
    time TIMESTAMPTZ NOT NULL,
    provider_id VARCHAR(50) NOT NULL,
    model_name VARCHAR(100) NOT NULL,
    input_tokens INT NOT NULL,
    output_tokens INT NOT NULL,
    total_cost_usd DOUBLE PRECISION NOT NULL
);

-- 3. Convertir la tabla normal en una Hypertable particionada por tiempo
-- (Esto es lo que le da el "superpoder" a TimescaleDB)
SELECT create_hypertable('token_usage_logs', 'time', if_not_exists => TRUE);

-- 4. Crear un índice para búsquedas rápidas por proveedor
CREATE INDEX ix_provider_time ON token_usage_logs (provider_id, time DESC);
