package com.NomadNook.NomadNook.Model;



import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String descripcion;

    private String icono;

    @ManyToMany(mappedBy = "categorias")
    private List<Alojamiento> alojamientos;
}
