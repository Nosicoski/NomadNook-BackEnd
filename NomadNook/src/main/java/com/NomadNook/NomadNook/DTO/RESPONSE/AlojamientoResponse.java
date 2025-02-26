package com.NomadNook.NomadNook.DTO.RESPONSE;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Model.Usuario;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlojamientoResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Alojamiento.TipoAlojamiento tipo;
    private Integer capacidad;
    private BigDecimal precioPorNoche;
    private String ubicacion;
    private String direccion;
    private Boolean disponible;
    private Long propietario_id;
    private String imagenes;
    public enum TipoAlojamiento {
        PLAYA,MONTANA,NEVADA,SELVA,BOSQUE,CAMPO
    }


}
