package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Repository.IReservaRepository;
import com.NomadNook.NomadNook.Service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private IReservaRepository iReservaRepository;

    @Override
    public Reserva createReserva(Reserva reserva) {
        return iReservaRepository.save(reserva); // Guarda la nueva reserva
    }

    @Override
    public Optional<Reserva> getReservaById(Long id) {
        return iReservaRepository.findById(id); // Busca una reserva por ID
    }

    @Override
    public List<Reserva> listReservas() {
        return iReservaRepository.findAll(); // Obtiene todas las reservas
    }

    @Override
    public List<Reserva> listReservasByCliente(Long clienteId) {
        return iReservaRepository.findByClienteId(clienteId); // Obtiene todas las reservas de un cliente
    }

    @Override
    public Reserva updateReserva(Long id, Reserva reserva) {
        if (!iReservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada"); // Verifica si la reserva existe
        }
        reserva.setId(id); // Asigna el ID correcto
        return iReservaRepository.save(reserva); // Guarda la reserva actualizada
    }

    @Override
    public boolean deleteReserva(Long id) {
        if (iReservaRepository.existsById(id)) {
            iReservaRepository.deleteById(id); // Elimina la reserva
            return true;
        }
        return false;
    }
}
