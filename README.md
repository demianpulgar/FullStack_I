        Documentación del Proyecto: Gestión de Usuarios
1. Estructura del Proyecto
Model
Define las entidades que representan las tablas de la base de datos.

Ejemplo:

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "NAME")
    private String name;

    // ...otros campos...
}

Define la estructura de los datos que se almacenan en la base de datos.

Repository
Interfaces para acceder y consultar los datos.

Ejemplo:

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findByName(String name);
}

Permite acceder y consultar los datos de los usuarios en la base de datos.

Service
Contiene la lógica de negocio y reglas de la aplicación.

Ejemplo:
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User crearUsuario(User usuario) {
        return userRepository.save(usuario);
    }
    // ...otros métodos...
}

Contiene la lógica de negocio, como crear, actualizar o eliminar usuarios.

Controller
Expone los endpoints REST para interactuar con la API.

Ejemplo:
@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/crear")
    public User crearUser(@Valid @RequestBody User user){
        return userService.crearUsuario(user);
    }
    // ...otros endpoints...
}

Expone los endpoints para que los clientes puedan interactuar con la API.

2. application.properties
Archivo de configuración donde se definen:

Parámetros de conexión a la base de datos.
Configuración de JPA/Hibernate.
Otros parámetros globales de la aplicación.
Ejemplo:
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=admin
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3. Dependencias
El archivo pom.xml contiene las librerías necesarias para:

Crear APIs REST (spring-boot-starter-web)
Acceso a datos con JPA (spring-boot-starter-data-jpa)
Validaciones (hibernate-validator, jakarta.validation-api)
Conexión a Oracle (ojdbc8)
Utilidades de Java (lombok)
Ejemplo:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- ...otras dependencias... -->

4. Endpoints
       
| Método HTTP | Endpoint                        | Descripción                    |
| ----------- | ------------------------------- | ------------------------------ |
| GET         | `/api/usuarios/listar`          | Lista todos los usuarios       |
| GET         | `/api/usuarios/encontrar/{id}`  | Buscar usuario por ID          |
| POST        | `/api/usuarios/crear`           | Crea un nuevo usuario          |
| POST        | `/api/usuarios/crear/varios`    | Crea varios usuarios           |
| PUT         | `/api/usuarios/actualizar/{id}` | Actualiza un usuario existente |
| DELETE      | `/api/usuarios/deleate/{id}`    | Elimina un usuario             |
| PUT         | `/api/usuarios/suspender/{id}`  | Suspende un usuario            |
| PUT         | `/api/usuarios/activar/{id}`    | Activa un usuario              |



