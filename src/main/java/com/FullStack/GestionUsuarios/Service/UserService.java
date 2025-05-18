package com.FullStack.GestionUsuarios.Service;


import com.FullStack.GestionUsuarios.Model.User;
import com.FullStack.GestionUsuarios.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> obtenerTodos() {
        return userRepository.findAll();
    }

    public Optional<User> obtenerPorId(Long id) {
        return userRepository.findById(id);
    }

    public User crearUsuario(User usuario) {
        return userRepository.save(usuario);
    }

    public User actualizarUsuario(Long id, User usuarioActualizado) {
        return userRepository.findById(id).map(usuario -> {
            usuario.setName(usuarioActualizado.getName());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setActivo(usuarioActualizado.isActivo());             
            return userRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminarUsuario(Long id) {
        userRepository.deleteById(id);
    }

    public User suspenderUsuario(Long id) {
        return userRepository.findById(id).map(user-> {
            user.setActivo(false);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    public User activarUsuario(Long id) {
        return userRepository.findById(id).map(user-> {
            user.setActivo(true);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
 
}