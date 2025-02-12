package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Reserva;

import java.util.List;

public interface IReservaService {
    Reserva createReserva(Reserva reserva);
    Reserva getReservaById(Long id);
    List<Reserva> listReservasByCliente(Long clienteId);
    Reserva updateReserva(Long id, Reserva reserva);
    void deleteReserva(Long id);
}
