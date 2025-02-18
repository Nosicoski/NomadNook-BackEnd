package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Reserva;
import java.util.List;
import java.util.Optional;

public interface IReservaService {
    // Crear una nueva reserva
    Reserva createReserva(Reserva reserva);

    // Obtener una reserva por su ID
    Optional<Reserva> getReservaById(Long id);

    // Obtener todas las reservas
    List<Reserva> listReservas();

    // Obtener reservas por cliente (por su ID)
    List<Reserva> listReservasByCliente(Long clienteId);

    // Actualizar una reserva existente
    Reserva updateReserva(Long id, Reserva reserva);

    // Eliminar una reserva por su ID
    boolean deleteReserva(Long id);
}
