DROP TABLE IF EXISTS regiones;

CREATE TABLE regiones (      id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                             nombre VARCHAR(50) NOT NULL);


DROP TABLE IF EXISTS clientes;

CREATE TABLE clientes (     id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                              identificacion VARCHAR(10) NOT NULL,
                              nombre VARCHAR(50) NOT NULL,
                              apellido VARCHAR(50) NOT NULL,
                              correo VARCHAR(100) NOT NULL,
                              foto_url VARCHAR(250),
                              estado VARCHAR(15) NOT NULL,
                              id_region BIGINT);