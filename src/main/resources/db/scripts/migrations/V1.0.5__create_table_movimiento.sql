DROP TABLE IF EXISTS movimiento CASCADE;
CREATE TABLE movimiento
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_cuenta           BIGINT         NOT NULL,
    tipo_movimiento     VARCHAR(10)    NOT NULL CHECK (tipo_movimiento IN ('DEPOSITO', 'RETIRO')),
    saldo_inicial       DECIMAL(10, 2) NOT NULL CHECK (saldo_inicial >= 0),
    movimiento          DECIMAL(10, 2) NOT NULL CHECK (movimiento >= 0),
    saldo_disponible    DECIMAL(10, 2) NOT NULL CHECK (saldo_disponible >= 0),
    fecha               DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_crea          DATETIME       not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint         not null default '',
    id_usuario_modifica bigint         null,
    estado              varchar        not null default 'A',
    FOREIGN KEY (id_cuenta) REFERENCES cuenta (id)
);