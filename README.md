Sistema de Gestión de Usuarios
Este proyecto es una API REST desarrollada con Spring Boot para la gestión de usuarios, que permite crear, listar, buscar, actualizar, eliminar, suspender y activar usuarios en una base de datos Oracle. El sistema implementa buenas prácticas de validación, control de errores y manejo de dependencias.

Tabla de Contenidos
Descripción General
Estructura del Proyecto
Explicación de Componentes
Modelo (Model)
Repositorio (Repository)
Servicio (Service)
Controlador (Controller)
application.properties
pom.xml
Endpoints y Funcionalidad
Control de Errores
Dependencias y Librerías

Descripción General
El sistema permite gestionar usuarios a través de una API RESTful. Cada usuario tiene atributos como nombre, email, teléfono, rol, ciudad, estado (activo/suspendido) y contraseña. El sistema valida los datos de entrada, asegura unicidad en email y teléfono, y responde con mensajes claros ante errores.

Estructura del Proyecto

src/
 └── main/
     ├── java/
     │    └── com/
     │         └── FullStack/
     │              └── GestionUsuarios/
     │                   ├── Controller/
     │                   │    └── UserController.java
     │                   ├── Model/
     │                   │    └── User.java
     │                   ├── Repository/
     │                   │    └── UserRepository.java
     │                   ├── Service/
     │                   │    └── UserService.java
     │                   └── GestionUsuariosApplication.java
     └── resources/
          ├── application.properties
          └── ...
pom.xml

Explicación de Componentes
Modelo (Model)
User.java
Define la entidad User con anotaciones JPA y validaciones.
Campos: id, name, email, telefono, rol, ciudad, activo, userPassword.
Validaciones:
@NotBlank para campos obligatorios.
@Email para formato de email.
@Column(unique = true) para email y teléfono únicos.

Repositorio (Repository)
UserRepository.java
Extiende JpaRepository<User, Long>, proporcionando métodos CRUD automáticos para la entidad User.

Servicio (Service)
UserService.java
Contiene la lógica de negocio:

Métodos para crear, listar, buscar, actualizar, eliminar, suspender y activar usuarios.
Valida los datos antes de guardar o actualizar.
Lanza excepciones si el usuario no existe o si hay violaciones de unicidad.
Controlador (Controller)
UserController.java
Expone los endpoints REST:

Recibe y valida las solicitudes.
Llama a los métodos del servicio.
Controla los errores y devuelve mensajes personalizados usando ResponseEntity.
application.properties
Configura la conexión a la base de datos y el comportamiento de la aplicación:

spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@<host>_high?TNS_ADMIN=<ruta_wallet>
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
logging.level.org.hibernate=DEBUG
logging.level.com.zaxxer.hikari=DEBUG
logging.level.java.sql=DEBUG
spring.output.ansi.enabled=ALWAYS

Datasource: Configura el acceso a Oracle.
Variables de entorno: Usa ${DB_USER} y ${DB_PASS} para mayor seguridad.
Logging: Niveles de log para depuración.
Colores: Habilita colores en la consola.
pom.xml
Define las dependencias del proyecto:

Spring Boot Starter Web: Para crear APIs REST.
Spring Boot Starter Data JPA: Para acceso a datos con JPA/Hibernate.
Oracle JDBC: Para conectarse a Oracle.
Lombok: Para reducir el código boilerplate.
Jakarta Validation: Para validaciones de datos.
Spring Boot Starter Validation: Para integrar validaciones en controladores.
Endpoints y Funcionalidad

Método	Endpoint	Descripción
GET	/api/usuarios/listar	Lista todos los usuarios
GET	/api/usuarios/encontrar/{id}	Busca un usuario por ID
POST	/api/usuarios/crear	Crea un usuario nuevo
POST	/api/usuarios/crear/varios	Crea varios usuarios a la vez
PUT	/api/usuarios/actualizar/{id}	Actualiza un usuario existente
DELETE	/api/usuarios/deleate/{id}	Elimina un usuario por ID
PUT	/api/usuarios/suspender/{id}	Suspende (desactiva) un usuario
PUT	/api/usuarios/activar/{id}	Activa un usuario previamente suspendido

Control de Errores
Datos inválidos:
Si los datos enviados no cumplen las validaciones, se devuelve un error 400 con detalles.
Email o teléfono repetido:
Si se intenta crear/actualizar un usuario con email o teléfono ya registrado, se devuelve un error 400 con mensaje personalizado.
Usuario no encontrado:
Si se intenta buscar, actualizar, eliminar, suspender o activar un usuario inexistente, se devuelve un error 404 con mensaje personalizado.
Mensajes personalizados:
Todos los errores relevantes devuelven un mensaje claro en el cuerpo de la respuesta, facilitando el consumo de la API.

Dependencias y Librerías
spring-boot-starter-web: Para exponer endpoints REST.
spring-boot-starter-data-jpa: Para persistencia y consultas a la base de datos.
oracle-ojdbc8: Driver JDBC para Oracle.
lombok: Anotaciones para reducir código repetitivo (getters, setters, constructores, etc.).
spring-boot-starter-validation: Validación de datos en los controladores.
jakarta.validation: Anotaciones de validación como @NotBlank, @Email, etc.

Ejemplo de Respuestas
Usuario encontrado:

{
  "id": 1,
  "name": "Juan",
  "email": "juan@mail.com",
  ...
}

Usuario no encontrado:

"Usuario no encontrado con id: 99"

Error de email/telefono repetido:

"El email o teléfono ya está registrado."















