package com.NomadNook.NomadNook.Model;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    private BigDecimal monto;
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    private EstadoPago estado;

    public enum MetodoPago {
        TARJETA_CREDITO, TARJETA_DEBITO, PAYPAL, TRANSFERENCIA
    }

    public enum EstadoPago {
        PENDIENTE, APROBADO, RECHAZADO
    }


}
