DROP TABLE IF EXISTS limite_diario CASCADE;
CREATE TABLE limite_diario
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_cuenta           BIGINT         NOT NULL,
    limite_diario     DECIMAL(10, 2) NOT NULL CHECK (limite_diario >= 0),
    valor_tope       DECIMAL(10, 2) NOT NULL CHECK (valor_tope >= 0),
    fecha_crea          DATETIME       not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint         not null default '',
    id_usuario_modifica bigint         null,
    estado              varchar        not null default 'A',
    FOREIGN KEY (id_cuenta) REFERENCES cuenta (id)
);