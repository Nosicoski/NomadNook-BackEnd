package com.NomadNook.NomadNook.Security.DTO.RESPONSE;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Model.Usuario;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data

public class ReservaResponse {
    private Long id;
    private Usuario cliente;
    private Alojamiento alojamiento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal total;
    private Reserva.EstadoReserva estado;
    private LocalDateTime fechaReserva;
    public enum EstadoReserva {
        PENDIENTE, CONFIRMADA, CANCELADA, FINALIZADA
    }
}
