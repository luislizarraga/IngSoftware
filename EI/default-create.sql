create table alumno (
  id                        integer not null,
  activo                    tinyint(1) default 1,
  nombre                    varchar(255) not null,
  apellido_paterno          varchar(255) not null,
  apellido_materno          varchar(255),
  correo_electronico        varchar(255) not null,
  contrasena                varchar(255) not null,
  constraint uq_alumno_correo_electronico unique (correo_electronico),
  constraint pk_alumno primary key (id))
;

create table curso (
  id                        integer not null,
  aprobado                  boolean,
  constancia                varchar(255),
  calificacion              integer,
  nivel                     varchar(255),
  horario                   timestamp,
  alumno_id                 integer,
  profesor_id               integer,
  constraint pk_curso primary key (id))
;

create table profesor (
  id                        integer not null,
  activo                    tinyint(1) default 1,
  nombre                    varchar(255) not null,
  apellido_paterno          varchar(255) not null,
  apellido_materno          varchar(255),
  correo_electronico        varchar(255) not null,
  contrasena                varchar(255) not null,
  video                     varchar(255),
  constancia                varchar(255),
  constraint uq_profesor_correo_electronico unique (correo_electronico),
  constraint pk_profesor primary key (id))
;

create sequence alumno_seq;

create sequence curso_seq;

create sequence profesor_seq;

alter table curso add constraint fk_curso_alumno_1 foreign key (alumno_id) references alumno (id) on delete restrict on update restrict;
create index ix_curso_alumno_1 on curso (alumno_id);
alter table curso add constraint fk_curso_profesor_2 foreign key (profesor_id) references profesor (id) on delete restrict on update restrict;
create index ix_curso_profesor_2 on curso (profesor_id);


