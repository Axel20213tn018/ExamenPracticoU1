create schema curpDB;
use curpDB;
create table persona(
	id int primary key not null auto_increment,
    nombre varchar (25) not null,
    apellidoP varchar (25) not null,
    apellidoM varchar (25) not null,
    sexo varchar (2) not null,
    estado varchar (25) not null,
    fechanac date not null,
    curp varchar(45) not null
);