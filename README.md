# Sistema de Gestión de Usuarios - Informe de Microservicios

API REST desarrollada con **Spring Boot** y arquitectura basada en microservicios, para la gestión de usuarios en una base de datos Oracle. Incluye operaciones CRUD, pruebas unitarias, documentación interactiva, HATEOAS, y comunicación REST entre servicios, todo desplegado en la nube usando AWS y Docker Compose.

---

## 1. Estrategia de Microservicios

Se optó por una **estrategia de microservicio por dominio**, donde cada componente (usuarios, cursos, etc.) es independiente y se comunica mediante APIs REST. Esto permite escalabilidad y despliegues independientes, facilitando el mantenimiento y la evolución del sistema.

---

## 2. Enfoques Éticos en la Implementación

- **Privacidad de datos:**  
  Los datos sensibles (como contraseñas) se almacenan cifrados y nunca se exponen en respuestas. El acceso a endpoints que modifican datos está protegido y requiere validaciones estrictas.
- **Otras consideraciones éticas:**  
  - Cumplimiento de normativas de protección de datos.
  - Documentación clara sobre qué datos se almacenan y cómo se usan.

---

## 3. Desarrollo de Componentes Backend

### 1. Buenas Prácticas de Diseño y Arquitectura

- El código sigue principios SOLID y separación por capas (`Model`, `Repository`, `Service`, `Controller`).
- Se usan anotaciones de validación y manejo de errores personalizado para robustez.

### 2. Uso de Herramientas y Frameworks

- **Spring Boot** y **Maven** para gestión y construcción del proyecto.
- Dependencias principales:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `oracle-ojdbc`
  - `lombok`
  - `spring-boot-starter-validation`
  - `springdoc-openapi-starter-webmvc-ui` (Swagger/OpenAPI)
  - `spring-boot-starter-hateoas`
  - `mockito-core` y `spring-boot-starter-test` (para pruebas unitarias)
- El archivo `pom.xml` refleja estas dependencias, entre otras necesarias para la funcionalidad del sistema.

### 3. Trabajo Colaborativo

- Control de versiones con **Git** y **GitHub**.
- El historial de commits y merges asegura trazabilidad y control de cambios.

---

## 4.  Explicación de Componentes

### Modelo (Model) — `User.java`

- Define la entidad **User** usando anotaciones JPA y validaciones.
- **Campos:** `id`, `name`, `email`, `telefono`, `rol`, `ciudad`, `activo`, `userPassword`.
- **Validaciones:**
  - `@NotBlank` para campos obligatorios.
  - `@Email` para el campo email.
  - `@Column(unique = true)` para asegurar unicidad en email y teléfono.
- Permite garantizar integridad y consistencia directamente a nivel de modelo.

### Repositorio (Repository) — `UserRepository.java`

- Extiende `JpaRepository<User, Long>`, proporcionando métodos CRUD automáticos y consultas personalizadas si se requieren.
- Permite operaciones como guardar, buscar, actualizar y eliminar usuarios de manera eficiente.

### Servicio (Service) — `UserService.java`

- Implementa la lógica de negocio:
  - Crear, listar, buscar, actualizar, eliminar, suspender y activar usuarios.
  - Validación de datos antes de cualquier operación de persistencia.
  - Manejo de excepciones por violación de unicidad o inexistencia.
- Centraliza las reglas de negocio y la validación, facilitando el mantenimiento y la escalabilidad.

### Controlador (Controller) — `UserController.java`

- Expone los endpoints REST para interactuar con el sistema de usuarios.
- Valida solicitudes recibidas y maneja posibles errores.
- Utiliza `ResponseEntity` para construir respuestas personalizadas (mensajes, códigos HTTP y datos).
- Implementa el soporte para HATEOAS, agregando enlaces navegables en las respuestas.

---

## 5. Operaciones CRUD, HATEOAS y Documentación Interactiva

- Las operaciones CRUD están implementadas en el controlador y el servicio:
  - Crear usuario: `POST /api/usuarios/crear`
  - Listar usuarios: `GET /api/usuarios/listar`
  - Buscar por ID: `GET /api/usuarios/encontrar/{id}`
  - Actualizar: `PUT /api/usuarios/actualizar/{id}`
  - Eliminar: `DELETE /api/usuarios/deleate/{id}`
  - Suspender usuario: `PUT /api/usuarios/suspender/{id}`
  - Activar usuario: `PUT /api/usuarios/activar/{id}`

- **Postman** y **Swagger** se utilizaron para probar exhaustivamente todos los endpoints.  
  - *Postman* facilitó la validación de respuestas, pruebas de error y éxito, y la automatización de escenarios de integración.
  - *Swagger* (OpenAPI) expone la documentación interactiva de la API en tiempo real, permitiendo probar todos los endpoints desde el navegador y ver la estructura de las peticiones y respuestas, así como los posibles códigos de error.  
    - La documentación Swagger está disponible en:  
      ```
      http://localhost:8080/swagger-ui/index.html
      ```
    - Esto permite a cualquier desarrollador o tester explorar y validar el funcionamiento de la API de forma visual e interactiva.

- **HATEOAS:**  
  Todos los endpoints CRUD devuelven respuestas enriquecidas con enlaces (usando `EntityModel` y `CollectionModel` de Spring HATEOAS). Esto permite a los clientes navegar entre recursos relacionados a través de los links proporcionados en las respuestas JSON, por ejemplo:
  - Al listar usuarios, cada usuario incluye enlaces para "actualizar", "eliminar", o ver detalles.
  - Esto cumple el principio de "Hypermedia as the Engine of Application State" (HATEOAS), facilitando la auto-descubribilidad de la API y la construcción de clientes desacoplados y robustos.
  - Ejemplo de respuesta HATEOAS:
    ```json
    {
      "id": 1,
      "name": "Juan",
      "_links": {
        "self": { "href": "/api/usuarios/encontrar/1" },
        "actualizar": { "href": "/api/usuarios/actualizar/1" },
        "eliminar": { "href": "/api/usuarios/deleate/1" },
        "usuarios": { "href": "/api/usuarios/listar" }
      }
    }
    ```

- **Cómo funciona HATEOAS:**  
  HATEOAS permite que cada respuesta de la API incluya enlaces a operaciones relacionadas, haciendo que los clientes puedan descubrir funcionalidades adicionales sin conocer de antemano la estructura completa de la API.

---

## 6. Comunicación RESTful entre Microservicios

- El microservicio de usuarios se comunica con otros microservicios (como el de cursos) usando clientes REST (`RestTemplate` y servicios como `CursoClienteService`).
- Los endpoints REST están claramente definidos y documentados.
- Las pruebas de integración entre servicios se realizaron utilizando tanto **Postman** como la interfaz interactiva de **Swagger**.

---

## 7. Pruebas Unitarias

- Las pruebas unitarias se desarrollaron usando **JUnit** y **Mockito**.
- Se pueden ejecutar desde la terminal con:
  ```bash
  mvn test
  ```
  o
  ```bash
  mvnd test
  ```
- Se incluyen dos clases principales de pruebas:
  - **UserServiceTest:**  
    - Prueba la lógica de negocio, incluyendo casos donde el usuario existe (con datos reales) y donde no existe (usando `Optional.empty()` para simular ausencia).  
    - Se testea el manejo de errores y la respuesta para ambos casos, validando la robustez del servicio.
  - **UserControllerTest:**  
    - Prueba el endpoint de búsqueda de usuario por ID, asegurando la correcta respuesta HTTP y el formato esperado.
- Esto garantiza que tanto la lógica de backend como la capa de presentación funcionen correctamente bajo diferentes escenarios.

---

## 8. Despliegue en la Nube

El despliegue en la nube se realizó con AWS. Se utilizó una instancia Amazon Linux (t3.micro) gestionada vía SSH desde Ubuntu, con puertos 22 (SSH) y 8080-8081 (servicios) habilitados. Para la transferencia de archivos se empleó WinSCP. Java 17 (corretto) se instaló mediante terminal, y el proyecto se compiló a .jar usando `mvnd package`. Se empleó Docker y Docker Compose para contenerizar y orquestar ambos servicios.

**Dentro de la instancia está organizado de la siguiente manera:**

```
Proyecto-EduTech/
├── gestion-usuarios/
│   ├── gestion-usuarios.jar
│   ├── Dockerfile
│   ├── .env
│   └── Wallet/         # Archivos de conexión Oracle Cloud (wallet de base de datos)
├── gestion-cursos/
│   ├── gestion-cursos.jar
│   ├── Dockerfile
│   └── .env
└── docker-compose.yml   # Orquestación de ambos servicios y sus variables de entorno
```
> *Dentro de cada carpeta se encuentran los archivos necesarios para la funcionalidad de cada microservicio.*

### Comandos principales en terminal

```bash
# Acceder a un contenedor para pruebas internas (por ejemplo, gestion-usuarios)
sudo docker exec -it proyecto-edutech-gestion-usuarios-1 sh

# Probar comunicación entre servicios desde dentro del contenedor
curl http://gestion-cursos:8081/api/cursos
```

De esta manera se logra una organización clara y un funcionamiento robusto de los microservicios desplegados en la nube.

---

## Referencias y Archivos Relevantes

- CRUD y HATEOAS: Ver métodos en `UserController.java`
- Pruebas unitarias:  
  - `/test/java/com/FullStack/GestionUsuarios/Service/UserServiceTest.java`  
  - `/test/java/com/FullStack/GestionUsuarios/Controller/UserControllerTest.java`
- Comunicación REST entre servicios: `CursoClienteService.java` y `AppConfig.java`
- Documentación Swagger: [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- Estructura del proyecto en `/src`

---

