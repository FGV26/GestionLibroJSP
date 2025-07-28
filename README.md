DATA BASE 

CREATE DATABASE libro; 
USE libro; 

CREATE TABLE libro (
	id_libro VARCHAR(100) primary key,
    titulo VARCHAR(100) NOT NULL, 
    autor VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
    
create table socio(
	id_socio varchar(100) primary key,
    nombre varchar(100) not null,
    correo varchar(100) not null
    );

create table prestamo(
	id_prestamo varchar(100) primary key,
    id_libro varchar(100),
    id_socio varchar(100),
    fecha_prestamo  DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    fecha_retorno DATE NOT NULL,
    FOREIGN KEY(id_libro) REFERENCES libro(id_libro),
    FOREIGN KEY (id_socio) references socio(id_socio)
    );
