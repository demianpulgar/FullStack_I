package com.FullStack.GestionUsuarios.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/listar")
    public List<User> ListUser(){
        return userService.obtenerTodos();
    }

    @GetMapping("/encontrar/{id}")
    public ResponseEntity<?> obtenerUser(@PathVariable Long id) {
        try {
            User user = userService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException());
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado con id: " + id);
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUser(@Valid @RequestBody User user) {
        try {
            User creado = userService.crearUsuario(user);
            return ResponseEntity.ok(creado);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            return ResponseEntity
                .badRequest()
                .body("El email o teléfono ya está registrado.");
        }
    }

    @PostMapping("/crear/varios")
    public ResponseEntity<?> crearVariosUser(@RequestBody List<@Valid User> users) {
        try {
            List<User> creados = userService.crearUsuarios(users);
            return ResponseEntity.ok(creados);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            return ResponseEntity
                .badRequest()
                .body("Uno o más usuarios tienen email o teléfono ya registrado.");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarUser(@PathVariable Long id, @Valid @RequestBody User user){
        try {
            User actualizado = userService.actualizarUsuario(id, user);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado para actualizar.");
        }
    }

    @DeleteMapping("/deleate/{id}")
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

    @PutMapping("/suspender/{id}")
    public ResponseEntity<?> suspenderUser(@PathVariable Long id){
        try {
            User user = userService.suspenderUsuario(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado para suspender.");
        }
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<?> activarUser(@PathVariable Long id) {
        try {
            User user = userService.activarUsuario(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado para activar.");
        }
    }
}
