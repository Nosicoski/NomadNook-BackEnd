package com.NomadNook.NomadNook.Model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "disponibilidades")
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alojamiento_id", nullable = false)
    private Alojamiento alojamiento;

    private LocalDate fechaDisponible;


    public enum EstadoDisponibilidad {
        DISPONIBLE, OCUPADA, MANTENIMIENTO
    }

    }

