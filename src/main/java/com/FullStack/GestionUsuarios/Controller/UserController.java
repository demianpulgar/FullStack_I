package com.FullStack.GestionUsuarios.Controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FullStack.GestionUsuarios.Model.User;
import com.FullStack.GestionUsuarios.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")
@RestController
@RequestMapping("/api/usuarios")
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    @GetMapping("/listar")
    public CollectionModel<EntityModel<User>> ListUser(){
        List<EntityModel<User>> users = userService.obtenerTodos().stream()
            .map(user -> EntityModel.of(user,
                linkTo(methodOn(UserController.class).obtenerUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).actualizarUser(user.getId(), user)).withRel("actualizar"),
                linkTo(methodOn(UserController.class).eliminarUser(user.getId())).withRel("eliminar")
            )).collect(Collectors.toList());
        return CollectionModel.of(users,
            linkTo(methodOn(UserController.class).ListUser()).withSelfRel(),
            linkTo(methodOn(UserController.class).crearUser(null)).withRel("crear")
        );
    }


    @Operation(summary = "Buscar usuario por ID", description = "Devuelve un usuario si existe el ID, o 404 si no existe.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/encontrar/{id}")
    public ResponseEntity<?> obtenerUser(
            @Parameter(description = "ID del usuario a buscar") @PathVariable Long id) {
        try {
            User user = userService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException());
            EntityModel<User> resource = EntityModel.of(user,
                linkTo(methodOn(UserController.class).obtenerUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).actualizarUser(id, user)).withRel("actualizar"),
                linkTo(methodOn(UserController.class).eliminarUser(id)).withRel("eliminar"),
                linkTo(methodOn(UserController.class).ListUser()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado con id: " + id);
        }
    }


    @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario creado"),
        @ApiResponse(responseCode = "400", description = "Email o teléfono ya registrado")
    })
    @PostMapping("/crear")
    public ResponseEntity<?> crearUser(@Valid @RequestBody User user) {
        try {
            User creado = userService.crearUsuario(user);
            EntityModel<User> resource = EntityModel.of(creado,
                linkTo(methodOn(UserController.class).obtenerUser(creado.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).ListUser()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            return ResponseEntity
                .badRequest()
                .body("El email o teléfono ya está registrado.");
        }
    }


    @Operation(summary = "Crear varios usuarios", description = "Crea varios usuarios a la vez.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios creados"),
        @ApiResponse(responseCode = "400", description = "Uno o más usuarios tienen email o teléfono ya registrado")
    })
    @PostMapping("/crear/varios")
    public ResponseEntity<?> crearVariosUser(@RequestBody List<@Valid User> users) {
        try {
            List<User> creados = userService.crearUsuarios(users);
            List<EntityModel<User>> resources = creados.stream()
                .map(u -> EntityModel.of(u,
                    linkTo(methodOn(UserController.class).obtenerUser(u.getId())).withSelfRel(),
                    linkTo(methodOn(UserController.class).ListUser()).withRel("usuarios")
                )).collect(Collectors.toList());
            return ResponseEntity.ok(CollectionModel.of(resources));
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            return ResponseEntity
                .badRequest()
                .body("Uno o más usuarios tienen email o teléfono ya registrado.");
        }
    }


    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado para actualizar")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarUser(@PathVariable Long id, @Valid @RequestBody User user){
        try {
            User actualizado = userService.actualizarUsuario(id, user);
            EntityModel<User> resource = EntityModel.of(actualizado,
                linkTo(methodOn(UserController.class).obtenerUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).ListUser()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado para actualizar.");
        }
    }


    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado para eliminar")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarUser(@PathVariable Long id){
        try {
            userService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado para eliminar.");
        }
    }


    @Operation(summary = "Suspender usuario", description = "Suspende (desactiva) un usuario por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario suspendido"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado para suspender")
    })
    @PutMapping("/suspender/{id}")
    public ResponseEntity<?> suspenderUser(@PathVariable Long id){
        try {
            User user = userService.suspenderUsuario(id);
            EntityModel<User> resource = EntityModel.of(user,
                linkTo(methodOn(UserController.class).obtenerUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).ListUser()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado para suspender.");
        }
    }

    @Operation(summary = "Activar usuario", description = "Activa un usuario previamente suspendido por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario activado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado para activar")
    })
    @PutMapping("/activar/{id}")
    public ResponseEntity<?> activarUser(@PathVariable Long id) {
        try {
            User user = userService.activarUsuario(id);
            EntityModel<User> resource = EntityModel.of(user,
                linkTo(methodOn(UserController.class).obtenerUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).ListUser()).withRel("usuarios")
            );
            return ResponseEntity.ok(resource);
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado para activar.");
        }
    }
}
