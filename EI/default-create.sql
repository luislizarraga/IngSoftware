create table alumno (
  id                        integer auto_increment not null,
  nombre                    varchar(255),
  activo                    tinyint(1) default 0,
  apellido_paterno          varchar(255),
  apellido_materno          varchar(255),
  correo_electronico        varchar(255),
  contrasena                varchar(255),
  constraint pk_alumno primary key (id))
;

create table profesor (
  id                        integer auto_increment not null,
  activo                    tinyint(1) default 0,
  nombre                    varchar(255),
  apellido_paterno          varchar(255),
  apellido_materno          varchar(255),
  correo_electronico        varchar(255),
  contrasena                varchar(255),
  constraint pk_profesor primary key (id))
;



