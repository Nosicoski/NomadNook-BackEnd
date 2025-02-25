package com.NomadNook.NomadNook.DTO.RESPONSE;

import com.NomadNook.NomadNook.Model.Alojamiento;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Data

public class DisponibilidadResponse {

    private Long id;
    private Alojamiento alojamiento;
    private LocalDate fechaDisponible;
    public enum EstadoDisponibilidad {
        DISPONIBLE, OCUPADA, MANTENIMIENTO
    }
}
