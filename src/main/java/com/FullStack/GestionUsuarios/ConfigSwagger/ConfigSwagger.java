package com.FullStack.GestionUsuarios.ConfigSwagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class ConfigSwagger {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestión de Usuarios")
                        .version("1.0")
                        .description("Documentación de la API para el sistema de gestión de usuarios."));
    }
}
