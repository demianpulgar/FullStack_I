# Sistema de Gestión de Usuarios

API REST desarrollada con **Spring Boot** para la gestión de usuarios en una base de datos Oracle. Permite crear, listar, buscar, actualizar, eliminar, suspender y activar usuarios.

---

## 🗂️ Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Explicación de Componentes](#explicación-de-componentes)
  - [Modelo (Model)](#modelo-model)
  - [Repositorio (Repository)](#repositorio-repository)
  - [Servicio (Service)](#servicio-service)
  - [Controlador (Controller)](#controlador-controller)
  - [application.properties](#applicationproperties)
  - [pom.xml](#pomxml)
- [Endpoints y Funcionalidad](#endpoints-y-funcionalidad)
- [Control de Errores](#control-de-errores)
- [Dependencias y Librerías](#dependencias-y-librerías)
- [Ejemplo de Respuestas](#ejemplo-de-respuestas)

---

## 📋 Descripción General

El sistema permite gestionar usuarios a través de una API RESTful.  
Cada usuario tiene los siguientes atributos principales:

- **Nombre**
- **Email**
- **Teléfono**
- **Rol**
- **Ciudad**
- **Estado** (activo/suspendido)
- **Contraseña**

Incluye validación de datos y control de errores personalizado.

---

## 🏗️ Estructura del Proyecto

src/
└── main/
    ├── java/
    │   └── com/
    │       └── FullStack/
    │           └── GestionUsuarios/
    │               ├── Controller/
    │               │   └── UserController.java
    │               ├── Model/
    │               │   └── User.java
    │               ├── Repository/
    │               │   └── UserRepository.java
    │               ├── Service/
    │               │   └── UserService.java
    │               └── GestionUsuariosApplication.java
    └── resources/
        ├── application.properties
        └── ...
pom.xml


## 🧩 Explicación de Componentes

### Modelo (Model)

**`User.java`**
- Define la entidad `User` con anotaciones JPA y validaciones.
- **Campos:** `id`, `name`, `email`, `telefono`, `rol`, `ciudad`, `activo`, `userPassword`.
- **Validaciones:**  
  - `@NotBlank` para campos obligatorios  
  - `@Email` para el campo email  
  - `@Column(unique = true)` para email y teléfono únicos

---

### Repositorio (Repository)

**`UserRepository.java`**
- Extiende `JpaRepository<User, Long>`, proporcionando métodos CRUD automáticos.

---

### Servicio (Service)

**`UserService.java`**
- Lógica de negocio:  
  - Crear, listar, buscar, actualizar, eliminar, suspender y activar usuarios.
  - Validación de datos antes de guardar o actualizar.
  - Manejo de excepciones por unicidad o inexistencia.

---

### Controlador (Controller)

**`UserController.java`**
- Expone los endpoints REST.
- Valida solicitudes y errores.
- Usa `ResponseEntity` para mensajes personalizados.

---

### application.properties

- Configuración de la base de datos y logs:

```properties
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@<host>_high?TNS_ADMIN=<ruta_wallet>
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
logging.level.org.hibernate=DEBUG
logging.level.com.zaxxer.hikari=DEBUG
logging.level.java.sql=DEBUG
spring.output.ansi.enabled=ALWAYS

```

pom.xml

Incluye dependencias principales:

Spring Boot Starter Web
Spring Boot Starter Data JPA
Oracle JDBC
Lombok
Jakarta Validation
Spring Boot Starter Validation

| Método   | Endpoint                        | Descripción                                |
| -------- | ------------------------------- | ------------------------------------------ |
| `GET`    | `/api/usuarios/listar`          | 📄 Lista todos los usuarios                |
| `GET`    | `/api/usuarios/encontrar/{id}`  | 🔍 Busca un usuario por su ID              |
| `POST`   | `/api/usuarios/crear`           | ➕ Crea un usuario nuevo                    |
| `POST`   | `/api/usuarios/crear/varios`    | 🧩 Crea varios usuarios a la vez           |
| `PUT`    | `/api/usuarios/actualizar/{id}` | ✏️ Actualiza un usuario existente          |
| `DELETE` | `/api/usuarios/deleate/{id}`    | ❌ Elimina un usuario por su ID             |
| `PUT`    | `/api/usuarios/suspender/{id}`  | 🚫 Suspende (desactiva) un usuario         |
| `PUT`    | `/api/usuarios/activar/{id}`    | ✅ Activa un usuario previamente suspendido |


🚨 Control de Errores
Datos inválidos:
Error 400 y detalles de validación.
Email o teléfono repetido:
Error 400 con mensaje personalizado.
Usuario no encontrado:
Error 404 con mensaje personalizado.
Mensajes claros:
Todos los errores devuelven mensajes descriptivos para facilitar el consumo de la API.
📦 Dependencias y Librerías
spring-boot-starter-web
spring-boot-starter-data-jpa
oracle-ojdbc8
lombok
spring-boot-starter-validation
jakarta.validation

🧪 Ejemplo de Respuestas
Usuario encontrado:

{
  "id": 1,
  "name": "Juan",
  "email": "juan@mail.com",
  ...
}

Usuario no encontrado:
Usuario no encontrado con id: 99

Error de email/teléfono repetido:
El email o teléfono ya está registrado.
