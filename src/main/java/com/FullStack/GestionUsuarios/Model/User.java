package com.FullStack.GestionUsuarios.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Column(unique = true, nullable= false)
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(unique = true, nullable= false)
    private String telefono;

    @NotBlank(message = "El rol no puede estar vacío")
    private String rol;

    @NotBlank(message = "La ciudad no puede estar vacía")
    private String ciudad;

    @Builder.Default
    private boolean activo = true;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(name = "USERPASSWORD")
    private String userPassword;
}
