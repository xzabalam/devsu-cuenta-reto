DROP TABLE IF EXISTS usuario CASCADE;
CREATE TABLE usuario
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    username            VARCHAR(255) NOT NULL CHECK (username IS NOT NULL AND LENGTH(username) > 0),
    password            VARCHAR(60)  NOT NULL CHECK (password IS NOT NULL AND LENGTH(password) > 0),
    fecha_crea          DATETIME     not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint       not null default '',
    id_usuario_modifica bigint       null,
    estado              varchar      not null default 'A'
);