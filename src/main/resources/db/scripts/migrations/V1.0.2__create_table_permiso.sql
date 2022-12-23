DROP TABLE IF EXISTS permiso CASCADE;
CREATE TABLE permiso
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario          bigint   NOT NULL,
    id_rol              bigint   NOT NULL,
    fecha_crea          DATETIME not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint   not null default '',
    id_usuario_modifica bigint   null,
    estado              varchar  not null default 'A',
    FOREIGN KEY (id_usuario) REFERENCES usuario (id),
    FOREIGN KEY (id_rol) REFERENCES rol (id)
);