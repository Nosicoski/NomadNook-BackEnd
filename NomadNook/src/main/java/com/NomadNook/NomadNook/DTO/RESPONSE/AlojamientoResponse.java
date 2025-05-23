
package com.NomadNook.NomadNook.DTO.RESPONSE;

import com.NomadNook.NomadNook.Model.Categoria;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Model.Imagen;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlojamientoResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Integer capacidad;
    private BigDecimal precioPorNoche;
    private String ubicacion;
    private String direccion;
    private Boolean disponible;
    private Long propietario_id;
    private List<Imagen> imagenes;
    private Set<Categoria> categorias;
    private Set<Caracteristica> caracteristicas;


    private LocalDate fechaReservaInicio;
    private LocalDate fechaReservaFin;

    private int cantidadFavoritos;
}
