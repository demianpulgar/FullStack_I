package com.FullStack.GestionUsuarios.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/listar")
    public List<User> ListUser(){
        return userService.obtenerTodos();
    }
    @GetMapping("/encontrar/{id}")
    public User obtenerUser(@PathVariable Long id){
        return userService.obtenerPorId(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    @PostMapping("/crear")
    public User crearUser(@RequestBody User user){
        return userService.crearUsuario(user);
    }
    @PostMapping("/crear/varios")
    public List<User> crearVariosUser(@RequestBody List<User> users){
        return userService.crearUsuarios(users);
    }
    @PutMapping("/actualizar/{id}")
    public User actualizarUser(@PathVariable Long id, @RequestBody User user){
        return userService.actualizarUsuario(id, user);
    }
    @DeleteMapping("/deleate/{id}")
    public void eliminarUser(@PathVariable Long id){
        userService.eliminarUsuario(id);
    }
    @PutMapping("/suspender/{id}")
    public User suspenderUser(@PathVariable Long id){
        return userService.suspenderUsuario(id);
    }
    @PutMapping("/activar/{id}")
    public User activarUser(@PathVariable Long id) {
        return userService.activarUsuario(id);
    }
}
