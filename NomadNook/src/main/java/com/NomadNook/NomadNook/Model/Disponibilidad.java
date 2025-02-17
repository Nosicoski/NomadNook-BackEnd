package com.NomadNook.NomadNook.Model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    public LocalDate getFechaDisponible() {
        return fechaDisponible;
    }

    public void setFechaDisponible(LocalDate fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }
}
