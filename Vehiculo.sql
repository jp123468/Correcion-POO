CREATE DATABASE vehiculo;

USE vehiculo;

CREATE TABLE IF NOT EXISTS vehiculo 
(  id numeric(5) not null,    
 marca varchar(50) not null,     
 modelo varchar(7) not null,      
 anio numeric(4) not null,     
 tipo_combustible varchar(50) not null,     
 estado varchar(50) );
 
INSERT INTO vehiculo(id, marca, modelo, anio, tipo_combustible, estado)
VALUES (1,'Mazda','BT-50','2020','Diesel','Disponible'),     
(2,'Chevrolet','Sail','2022','Gasolina','Ocupado');

SELECT * FROM vehiculo


