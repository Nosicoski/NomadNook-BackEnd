package com.NomadNook.NomadNook.Model;

import jakarta.persistence.*;
import lombok.Data;// Nueva entidad Caracteristica
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "caracteristicas")
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private String icono; // opcional, para guardar el nombre del icono o URL
}
