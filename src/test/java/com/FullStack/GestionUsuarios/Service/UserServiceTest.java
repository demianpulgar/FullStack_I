package com.FullStack.GestionUsuarios.Service;

import com.FullStack.GestionUsuarios.Model.User;
import com.FullStack.GestionUsuarios.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testObtenerPorId_UsuarioExiste() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testObtenerPorId_UsuarioNoExiste() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<User> result = userService.obtenerPorId(999L);

        assertTrue(result.isEmpty());
    }
}
