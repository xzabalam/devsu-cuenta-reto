-- usuarios
insert into USUARIO( username, password, fecha_crea, id_usuario_crea, estado) values( 'admin', '$2a$12$GZ37Dny5bRUIPhQD.n5RkOMOO/rfxvwo8PUHoeigMDzKscR1t7jcC', current_timestamp, 0, 'A');
insert into USUARIO( username, password, fecha_crea, id_usuario_crea, estado) values( 'usuario1', '$2a$12$7pUPlE8B35CyHb9Gp1RbhuhEx/slJBP50UyjECHIztaUYb27qxZC2', current_timestamp, 0, 'A');
insert into USUARIO( username, password, fecha_crea, id_usuario_crea, estado) values( 'usuario2', '$2a$12$sWyfDRknT2zyfY7Rt/a4nu.iuRfjnTeCBbkFzPYSzdPYvSwC7ktXi', current_timestamp, 0, 'A');
insert into USUARIO( username, password, fecha_crea, id_usuario_crea, estado) values( 'usuario3', '$2a$12$8cF5xhuM9WRveOiIWHMJDOZOI49UcnTf6A16T8Jfht1YI4ygJMHne', current_timestamp, 0, 'A');

-- roles
insert into ROL(nombre, fecha_crea, id_usuario_crea, estado) values( 'ADMINISTRADOR', current_timestamp, 0, 'A');
insert into ROL(nombre, fecha_crea, id_usuario_crea, estado) values( 'USUARIO', current_timestamp, 0, 'A');

-- usuario_rol
insert into PERMISO ( id_usuario, id_rol, fecha_crea, id_usuario_crea, estado) values( 1, 1, current_timestamp, 0, 'A');
insert into PERMISO ( id_usuario, id_rol, fecha_crea, id_usuario_crea, estado) values( 2, 2, current_timestamp, 0, 'A');
insert into PERMISO ( id_usuario, id_rol, fecha_crea, id_usuario_crea, estado) values( 3, 2, current_timestamp, 0, 'A');
insert into PERMISO ( id_usuario, id_rol, fecha_crea, id_usuario_crea, estado) values( 4, 2, current_timestamp, 0, 'A');