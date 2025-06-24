package com.FullStack.GestionUsuarios;

import com.FullStack.GestionUsuarios.Model.User;
import com.FullStack.GestionUsuarios.Repository.UserRepository;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Value("${USERS_TO_CREATE:250}")
    private int usersToCreate;

    @Override
    public void run(String... args) {
        Faker faker = new Faker(new Locale("es"));

        for (int i = 0; i < usersToCreate; i++) { // Inserta 20 usuarios ficticios
            User usuario = new User();
            usuario.setName(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setTelefono(faker.phoneNumber().cellPhone());
            usuario.setRol(faker.options().option("ADMIN", "USER", "READER"));
            usuario.setCiudad(faker.address().city());
            usuario.setActivo(true); // o faker.bool().bool()
            usuario.setUserPassword(faker.internet().password(8, 16));

            userRepository.save(usuario);
        }

        System.out.println("✔ Usuarios ficticios cargados correctamente.");
    }
}

