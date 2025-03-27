package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    // Buscar reservas por cliente
    List<Reserva> findByClienteId(Long clienteId); // Consulta personalizada por ID de cliente
    List<Reserva> findByAlojamientoIdAndEstadoInAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long alojamientoId,
            List<Reserva.EstadoReserva> estados,
            LocalDate fechaMaxima,
            LocalDate fechaMinima
    );

}
