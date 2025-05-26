package com.FullStack.GestionUsuarios.Model;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;
    private String email;
    private String telefono;
    private String rol;
    private String ciudad;
    @Builder.Default
    private boolean activo = true;
    @Column(name = "USERPASSWORD")   
    private String UserPassword;
}
