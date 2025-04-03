package com.NomadNook.NomadNook.Model;



import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alojamientos")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "El título no puede estar vacío") // Valida que el campo no sea nulo ni vacío
    private String titulo;


    @NotBlank(message = "La descripción no puede estar vacía") // Valida que el campo no sea nulo ni vacío
    private String descripcion;




    @Min(value = 1, message = "La capacidad debe ser al menos 1")  // Valida que la capacidad sea al menos 1
    @Max(value = 100, message = "La capacidad no puede superar los 100") // Valida que la capacidad no sea mayor a 100
    private Integer capacidad;


    @DecimalMin(value = "0.0", inclusive = false, message = "El precio por noche debe ser mayor que 0") // Valida que el precio sea mayor que 0
    private BigDecimal precioPorNoche;


    @NotBlank(message = "La ubicación no puede estar vacía") // Valida que el campo no sea nulo ni vacío
    private String ubicacion;


    @NotBlank(message = "La dirección no puede estar vacía") // Valida que el campo no sea nulo ni vacío
    private String direccion;


    @NotNull(message = "El campo de disponibilidad es obligatorio") // Valida que el campo no sea nulo
    private Boolean disponible;


    @ManyToOne
    @JoinColumn(name = "propietario_id", nullable = false)
    @JsonBackReference
    private Usuario propietario;


    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
//    @NotEmpty(message = "Debe haber al menos una imagen asociada al alojamiento")
    private List<Imagen> imagenes;


    @ManyToMany
    @JoinTable(
            name = "alojamiento_categoria",
            joinColumns = @JoinColumn(name = "alojamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias;

    @ManyToMany
    @JoinTable(
            name = "alojamiento_caracteristicas",
            joinColumns = @JoinColumn(name = "alojamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id")
    )
    private Set<Caracteristica> caracteristicas;

    @Transient
    private LocalDate fechaReservaInicio;

    @Transient
    private LocalDate fechaReservaFin;

    @ManyToMany
    @JoinTable(
            name = "favoritos",
            joinColumns = @JoinColumn(name = "alojamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuariosFavoritos;
}
