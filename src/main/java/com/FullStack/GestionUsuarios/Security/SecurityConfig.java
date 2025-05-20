package com.FullStack.GestionUsuarios.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/usuarios/listar", "/api/usuarios/encontrar/**")
                    .hasAnyRole("ADMIN", "PROFESOR")
                .requestMatchers("/api/usuarios/**")
                    .hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> httpBasic
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(401);
                    response.getWriter().write("Unauthorized");
                })
            );
        return http.build();
    }
    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService users() {
        var uds = new org.springframework.security.provisioning.InMemoryUserDetailsManager();

        uds.createUser(org.springframework.security.core.userdetails.User
            .withUsername("admin")
            .password("{noop}admin123")
            .roles("ADMIN")
            .build());

        uds.createUser(org.springframework.security.core.userdetails.User
            .withUsername("profesor")
            .password("{noop}profesor123")
            .roles("PROFESOR")
            .build());

        uds.createUser(org.springframework.security.core.userdetails.User
            .withUsername("estudiante")
            .password("{noop}estudiante123")
            .roles("STUDENT")
            .build());
        return uds;
    }
}
