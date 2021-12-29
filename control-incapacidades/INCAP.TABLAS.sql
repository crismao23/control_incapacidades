

ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;
CREATE USER INCAP IDENTIFIED BY CIRILA;

GRANT ALL PRIVILEGES TO INCAP;

--CREACION DE LAS TABLAS REQUERIDAS EN LA BASE DE DATOS

create table INCAPACIDAD (  
  nro_incapacidad   number,  
  fecha_inicio      date not null,  
  cantidad_dias     number not null,  
  cedula            varchar2(10),  
  constraint pk_nro_incapacidad primary key (nro_incapacidad),  
  constraint fk_persona_cedula foreign key (cedula) 
      references PERSONA (cedula)
);

create table PERSONA (
    cedula          varchar2(20),
    nombre          varchar2(100) not null,
    coddependencia  varchar2(20),
    constraint pk_cedula primary key (cedula)
);

create table DEPENDENCIA (
    coddependencia         varchar2(20),
    nombre_dependencia     varchar2(100) not null,
    constraint pk_coddependencia primary key (coddependencia)
);

create table DIAGNOSTICO (
    coddiagnostico        varchar2(4),
    nombre                varchar2(200) not null,
    constraint pk_nrodiagnostico primary key (coddiagnostico)
);

create table PRESCRIPCION (
    nro_incapacidad        number,
    codDiagnostico       varchar2(20),
    constraint pk_prescripcion primary key (nro_incapacidad, coddiagnostico)
);
alter table prescripcion
    add constraint fk_prescripcion foreign key (coddiagnostico)
    references diagnostico (coddiagnostico);
    
alter table prescripcion
    add constraint fk_prescripcion1 foreign key (nro_incapacidad)
    references incapacidad (nro_incapacidad);
    
create table LICENCIA (  
  IDLICENCIA             number,  
  FECHAINICIO              date,  
  FECHAFIN                 date,  
  CEDULA                   varchar(20),
  OBSERVACIONES            varchar(400),
  constraint PK_LICENCIA primary key (IDLICENCIA),  
  constraint FK_LICENCIA_PERSONA foreign key (CEDULA) references PERSONA(CEDULA)
);
    
--SECUENCIA PARA LAS LICENCIAS

CREATE SEQUENCE SEQLIC
START WITH 1
INCREMENT BY 1;

--TRIGGER PARA AGREGAR VALOR CRECIENTE EN ID LICENCIA

CREATE TRIGGER TRISEQLIC
BEFORE INSERT ON LICENCIA
FOR EACH ROW
BEGIN
SELECT SEQLIC.NEXTVAL INTO :NEW.IDLICENCIA FROM DUAL;
END;

--SECUENCIA PARA LAS INCAPACIDADES

CREATE SEQUENCE SEQINC
START WITH 224
INCREMENT BY 1;

--TRIGGER PARA AGREGAR VALOR CRECIENTE EN NRO INCAPACIDAD

CREATE TRIGGER TRISEQINC
BEFORE INSERT ON INCAPACIDAD
FOR EACH ROW
BEGIN
SELECT SEQINC.NEXTVAL INTO :NEW.NRO_INCAPACIDAD FROM DUAL;
END;

