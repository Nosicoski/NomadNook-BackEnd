package com.NomadNook.NomadNook.Model;



import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "alojamientos")
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoAlojamiento tipo;

    private Integer capacidad;

    private BigDecimal precioPorNoche;

    private String ubicacion;
    private String direccion;

    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name = "propietario_id", nullable = false)
    private Usuario propietario;


    @OneToMany
    @JoinColumn(name = "alojamiento_id")
    private List<Imagen> imagenes;

    public enum TipoAlojamiento {
        CABAÑA, DEPARTAMENTO, CASA
    }
}
