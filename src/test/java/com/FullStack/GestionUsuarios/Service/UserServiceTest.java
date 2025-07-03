package com.FullStack.GestionUsuarios.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.FullStack.GestionUsuarios.Model.User;
import com.FullStack.GestionUsuarios.Repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("obtenerPorId devuelve usuario si existe")
    void testObtenerPorId_UsuarioExiste() {
        User user = new User();
        user.setId(1L);
        user.setName("Juan");
        user.setEmail("juan@email.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Juan", result.get().getName());
        assertEquals("juan@email.com", result.get().getEmail());
    }

    @Test
    @DisplayName("obtenerPorId devuelve vacío si no existe")
    void testObtenerPorId_UsuarioNoExiste() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<User> result = userService.obtenerPorId(999L);

        assertTrue(result.isEmpty());
    }
}
