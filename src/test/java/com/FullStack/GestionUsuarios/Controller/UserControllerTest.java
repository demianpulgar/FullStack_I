package com.FullStack.GestionUsuarios.Controller;

import com.FullStack.GestionUsuarios.Model.User;
import com.FullStack.GestionUsuarios.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("GET /api/usuarios/encontrar/1 - Found")
    void getUser_found() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Juan");
        user.setEmail("juan@email.com");
        when(userService.obtenerPorId(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/usuarios/encontrar/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@email.com"));
    }

    @Test
    @DisplayName("GET /api/usuarios/encontrar/2 - Not Found")
    void getUser_notFound() throws Exception {
        when(userService.obtenerPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/encontrar/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
