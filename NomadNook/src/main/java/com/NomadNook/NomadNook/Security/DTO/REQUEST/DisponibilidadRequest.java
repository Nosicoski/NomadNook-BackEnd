package com.NomadNook.NomadNook.Security.DTO.REQUEST;

import com.NomadNook.NomadNook.Model.Alojamiento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@Data

public class DisponibilidadRequest {

    private Long id;
    private Alojamiento alojamiento;
    private LocalDate fechaDisponible;
    public enum EstadoDisponibilidad {
        DISPONIBLE, OCUPADA, MANTENIMIENTO
    }

}
