package com.FullStack.GestionUsuarios;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.FullStack.GestionUsuarios.Model.User;
import com.FullStack.GestionUsuarios.Repository.UserRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Value("${USERS_TO_CREATE:250}")
    private int usersToCreate;

    @Autowired
    private Environment env;

    @Override
    public void run(String... args) {
        String[] profiles = env.getActiveProfiles();
        boolean isDev = Arrays.asList(profiles).contains("dev");
        if (!isDev) {
            System.out.println("Perfil no es 'dev', no se cargan usuarios ficticios.");
            return;
        }

        Faker faker = new Faker(Locale.forLanguageTag("es"));
        int creados = 0;

        while (creados < usersToCreate) {
            String email = "usuario" + creados + "@ejemplo.com";
            String telefono = String.format("600%07d", creados); // Ejemplo: 6000000000, 6000000001, ...

            User usuario = new User();
            usuario.setName(faker.name().fullName());
            usuario.setEmail(email);
            usuario.setTelefono(telefono);
            usuario.setRol(faker.options().option("ADMIN", "USER", "READER"));
            usuario.setCiudad(faker.address().city());
            usuario.setActivo(true);
            usuario.setUserPassword(faker.internet().password(8, 16));

            userRepository.save(usuario);
            creados++;
        }

        System.out.println("✔ Usuarios ficticios cargados correctamente: " + creados);
    }
}

