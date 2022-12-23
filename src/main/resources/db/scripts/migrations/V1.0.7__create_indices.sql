CREATE INDEX idx_movimientos_id_cuenta ON movimiento (id_cuenta);
CREATE INDEX idx_movimientos_fecha ON movimiento (fecha);

CREATE INDEX idx_cuentas_numero_cuenta ON cuenta (numero_cuenta);
CREATE INDEX idx_cuentas_id_cliente ON cuenta (id_cliente);

CREATE INDEX idx_cliente_identificacion ON cliente (identificacion);