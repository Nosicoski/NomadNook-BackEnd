package com.NomadNook.NomadNook.Model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "resenias")
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "alojamiento_id", nullable = false)
    private Alojamiento alojamiento;

    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fechaResena;


}
