DROP TABLE IF EXISTS rol CASCADE;
CREATE TABLE rol
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre              VARCHAR(255) NOT NULL CHECK (nombre IS NOT NULL AND LENGTH(nombre) > 0),
    fecha_crea          DATETIME     not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint       not null default '',
    id_usuario_modifica bigint       null,
    estado              varchar      not null default 'A'
);