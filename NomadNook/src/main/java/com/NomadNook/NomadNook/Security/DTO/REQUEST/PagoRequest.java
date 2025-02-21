package com.NomadNook.NomadNook.Security.DTO.REQUEST;

import com.NomadNook.NomadNook.Model.Pago;
import com.NomadNook.NomadNook.Model.Reserva;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data

public class PagoRequest {

    private Long id;
    private Reserva reserva;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private Pago.MetodoPago metodoPago;
    private Pago.EstadoPago estado;
    public enum MetodoPago {
        TARJETA_CREDITO, TARJETA_DEBITO, PAYPAL, TRANSFERENCIA
    }
    public enum EstadoPago {
        PENDIENTE, APROBADO, RECHAZADO
    }

}
