INSERT INTO clientes (rut, nombre, apellido_paterno, apellido_materno, direccion, telefono, email, create_at, activo_sistema) VALUES (16147138,'Rodolfo','Quezada','Sierra', 'Av. Siempre viva', 987654321, 'rodolfo.quezada.s@gmail.com','2018-05-05', true);
INSERT INTO clientes (rut, nombre, apellido_paterno, apellido_materno, direccion, telefono, email, create_at, activo_sistema) VALUES (19123123,'Alejandra','Ojeda','Machuca', 'Av. Siempre viva', 912345678, 'mailen.ojedam@gmail.com','2018-05-05', true);
INSERT INTO clientes (rut, nombre, apellido_paterno, apellido_materno, direccion, telefono, email, create_at, activo_sistema) VALUES (14123123,'Jose','Maza','Sancho', 'Santiago', 912345678, 'mailen.ojedam@gmail.com','2018-05-05', true);
INSERT INTO clientes (rut, nombre, apellido_paterno, apellido_materno, direccion, telefono, email, create_at, activo_sistema) VALUES (20321321,'Elon','Musk','', 'Gringolandia', 911111111, 'elonmusk@gmail.com','2018-05-05', true);
INSERT INTO clientes (rut, nombre, apellido_paterno, apellido_materno, direccion, telefono, email, create_at, activo_sistema) VALUES (10123123,'Albert','Einstein','Einstein', 'Alemania', 987654321, 'albert@gmail.com','2018-05-05', true);

INSERT INTO especies(nombre) VALUES ('Canino');
INSERT INTO especies(nombre) VALUES ('Felino');
INSERT INTO especies(nombre) VALUES ('Aviar');
INSERT INTO especies(nombre) VALUES ('Reptil');

insert into razas (color, color_secundario, especie_id, nombre) values ('Amarillo','Negro', 1, 'Pug');
insert into razas (color, color_secundario, especie_id, nombre) values ('Negro','Amarillo', 1, 'Pastor Aleman');
insert into razas (color, color_secundario, especie_id, nombre) values ('Blanco','', 1, 'Poodle');
insert into razas (color, color_secundario, especie_id, nombre) values ('Negro','', 1, 'Poodle');
insert into razas (color, color_secundario, especie_id, nombre) values ('Amarillo','Blanco', 1, 'Schnauzer');

insert into razas (color, color_secundario, especie_id, nombre) values ('Amarillo','Negro', 2, 'Gato uno');
insert into razas (color, color_secundario, especie_id, nombre) values ('Negro','Amarillo', 2, 'Gato dos');
insert into razas (color, color_secundario, especie_id, nombre) values ('Blanco','', 2, 'Gato tres');
insert into razas (color, color_secundario, especie_id, nombre) values ('Negro','', 2, 'Gato cuatro');

insert into tipos_usuarios(nombre) values ('Medico Cirujano');
insert into tipos_usuarios(nombre) values ('Anestesista');
insert into tipos_usuarios(nombre) values ('Secretario');


insert into usuarios (apellido_materno, apellido_paterno, nombre, tipo_usuario_id) values ('Bailly', 'Figueroa', 'Carolina', 1);