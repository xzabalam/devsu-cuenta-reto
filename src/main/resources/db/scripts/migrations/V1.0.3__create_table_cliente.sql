DROP TABLE IF EXISTS cliente CASCADE;
CREATE TABLE cliente
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre              VARCHAR(255) NOT NULL CHECK (nombre IS NOT NULL AND LENGTH(nombre) > 0),
    genero              VARCHAR(1)   NOT NULL CHECK (genero IN ('M', 'F')),
    edad                INTEGER      NOT NULL CHECK (edad > 0),
    identificacion      VARCHAR(255) NOT NULL UNIQUE CHECK (identificacion IS NOT NULL AND LENGTH(identificacion) > 0),
    direccion           VARCHAR(255) NOT NULL CHECK (direccion IS NOT NULL AND LENGTH(direccion) > 0),
    telefono            VARCHAR(255) NOT NULL CHECK (telefono IS NOT NULL AND LENGTH(telefono) > 0),
    id_usuario          BIGINT       not null,
    fecha_crea          DATETIME     not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint       not null default '',
    id_usuario_modifica bigint       null,
    estado              varchar      not null default 'A',
    FOREIGN KEY (id_usuario) REFERENCES usuario (id)
);