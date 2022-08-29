# vaccination-core

Proyecto para el manejo de vacunas de los empleados

**Requerimientos**

Para poder ejecutar la aplicación es necesario:
> Java 17
>
> Maven 3.
> 
> PostgresSQL 14.

**Ejecutar la aplicación localmente**

Para poder ejecutar la aplicación localmente es necesario seguir los siguientes pasos:

**Crear el usuario de base de datos para conectar la aplicación**

```
CREATE ROLE kruger WITH
	LOGIN
	SUPERUSER
	CREATEDB
	CREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'regurk';
```
**Crear la base de datos con el usuario creado como propietario**

```
CREATE DATABASE vacunacion
    WITH
    OWNER = kruger
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
```
**Crear el esquema para OAUTH2**

```
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token BYTEA,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token BYTEA,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication BYTEA,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token BYTEA,
  authentication BYTEA
);

create table oauth_code (
  code VARCHAR(256), authentication BYTEA
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);
```

**Crear los esquemas que maneja la aplicación**

```
CREATE SCHEMA kru_common
    AUTHORIZATION kruger;
	
CREATE SCHEMA kru_company
    AUTHORIZATION kruger;
	
CREATE SCHEMA kru_person
    AUTHORIZATION kruger;
	
CREATE SCHEMA kru_security
    AUTHORIZATION kruger;
```
**Ejecución de la aplicación**

Para correr la aplicación Spring Boot en tu máquina local.  Una forma de hacerlo es ejecutando el método `main` que se encuentra en la clase `ec.kruger.vaccination.VaccinationCoreApplication`.

**Generación de información inicial**

Se debe ejecutar la aplicación para ejecutar las siguientes sentencias.

Tipos de Vacuna

```
INSERT INTO kru_common.tipo_vacuna(
	id, nombre)
	VALUES (1, 'Sputnik');
INSERT INTO kru_common.tipo_vacuna(
	id, nombre)
	VALUES (2, 'AstraZeneca');
INSERT INTO kru_common.tipo_vacuna(
	id, nombre)
	VALUES (3, 'Pfizer');
INSERT INTO kru_common.tipo_vacuna(
	id, nombre)
	VALUES (4, 'Jhonson&Jhonson');
```

Roles

```
INSERT INTO kru_security.rol(
	id, nombre)
	VALUES (1, 'ADMIN');
INSERT INTO kru_security.rol(
	id, nombre)
	VALUES (2, 'EMPLOYEE');
```
Usuario Administrador (la contraseña es "admin")

```
INSERT INTO kru_security.usuario(
	id, activo, contrasena, usuario, id_employee, id_rol)
	VALUES (0, true, '$2y$10$6YgFqQSTP6JMNBg.G7tQpu4tRUrZglzCHiB9ewtWOEv95tBvTjxsy', 'admin', NULL, 1);
```

Cliente OAUTH
```
INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('client-vaccination', 'vaccination-service', '$2y$10$5qOofKyk/tJEnpxLiT3au.kuXa8Ht.O1U8g1KudLlxhKjaa.WHXui', 'vaccination-service,password,authorization_code,refresh_token', 'password', null, null, 3600, 3600, null, true);
```

**Documentacion**

La documentación de las api's se encuentra en [Links](http://localhost:9090/vaccination-service/swagger-ui/).

**Postman Service**

El archivo `vaccination-core.postman_collection.json` contiene los servicios para ser consumidos desde Postman.

**Modelo de datos**

El modelo de datos se encuentra en el archivo `data_model.pdf`. 

**Copyright**

Realeased under the Apache Lincese 2.0. See the [Links](https://www.apache.org/licenses/LICENSE-2.0) file.
