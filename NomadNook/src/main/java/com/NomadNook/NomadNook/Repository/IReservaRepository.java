package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    // Buscar reservas por cliente
    List<Reserva> findByClienteId(Long clienteId); // Consulta personalizada por ID de cliente
}
