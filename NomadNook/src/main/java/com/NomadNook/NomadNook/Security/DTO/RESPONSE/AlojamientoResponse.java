package com.NomadNook.NomadNook.Security.DTO.RESPONSE;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Model.Usuario;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@Data

public class AlojamientoResponse {
    private String titulo;
    private String descripcion;
    private Alojamiento.TipoAlojamiento tipo;
    private Integer capacidad;
    private BigDecimal precioPorNoche;
    private String ubicacion;
    private String direccion;
    private Boolean disponible;
    private Usuario propietario;
    private List<Imagen> imagenes;
    public enum TipoAlojamiento {
        PLAYA,MONTANA,NEVADA,SELVA,BOSQUE,CAMPO
    }
}
