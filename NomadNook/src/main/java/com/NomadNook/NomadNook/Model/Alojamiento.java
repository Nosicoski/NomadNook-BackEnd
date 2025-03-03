package com.NomadNook.NomadNook.Model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alojamientos")
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "El título no puede estar vacío") // Valida que el campo no sea nulo ni vacío
    private String titulo;


    @NotBlank(message = "La descripción no puede estar vacía") // Valida que el campo no sea nulo ni vacío
    private String descripcion;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de alojamiento es obligatorio") // Valida que el tipo no sea nulo
    private TipoAlojamiento tipo;


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
    private List<Imagen> imagenes;


    public enum TipoAlojamiento {
        PLAYA,MONTANA,NEVADA,SELVA,BOSQUE,CAMPO
    }

    @ManyToMany
    @JoinTable(
            name = "alojamiento_caracteristicas",
            joinColumns = @JoinColumn(name = "alojamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id")
    )
    private Set<Caracteristica> caracteristicas = new HashSet<>();

}
