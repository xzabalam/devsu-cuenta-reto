DROP TABLE IF EXISTS cuenta CASCADE;
CREATE TABLE cuenta
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    numero_cuenta       VARCHAR(255)   NOT NULL UNIQUE CHECK (numero_cuenta IS NOT NULL AND LENGTH(numero_cuenta) > 0),
    tipo_cuenta         VARCHAR(10)    NOT NULL CHECK (tipo_cuenta IN ('AHORRO', 'CORRIENTE')),
    saldo_inicial       DECIMAL(10, 2) NOT NULL CHECK (saldo_inicial >= 0),
    id_cliente          bigint         NOT NULL,
    fecha_crea          DATETIME       not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint         not null default '',
    id_usuario_modifica bigint         null,
    estado              varchar        not null default 'A',
    FOREIGN KEY (id_cliente) REFERENCES cliente (id)
);