package io.github.jiangdequan;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity  // Marca esta clase como una entidad JPA.
@Table(name= "EVALUACION")  // Especifica el nombre de la tabla en la base de datos.
@Data  // Genera automáticamente getters, setters, equals, hashCode y toString.
@NoArgsConstructor  // Genera un constructor sin argumentos.
@AllArgsConstructor
public class Evaluacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String nombreEvaluacion;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String descripcion;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String fechaCreacion;

    @Column(nullable=false)  // Esta columna no puede ser nula. 
    private String fechaLimite;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private Integer duracion; // Duración en minutos
}

// Nueva clase Pregunta
@Entity
class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id")
    private List<Alternativa> alternativas;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String respuestaCorrecta;

}

    