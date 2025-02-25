package com.NomadNook.NomadNook.DTO.RESPONSE;

import com.NomadNook.NomadNook.Model.Pago;
import com.NomadNook.NomadNook.Model.Reserva;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data

public class PagoResponse {
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
