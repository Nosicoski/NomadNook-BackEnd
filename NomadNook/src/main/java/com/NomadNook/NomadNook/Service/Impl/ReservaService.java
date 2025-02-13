package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Repository.IReservaRepository;

import com.NomadNook.NomadNook.Service.IReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReservaService implements IReservaService {
    private final IReservaRepository reservaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);

    public ReservaService(IReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Reserva createReserva(Reserva reserva) {
        Reserva savedReserva = reservaRepository.save(reserva);
        LOGGER.info("Reserva creada con id: {}", savedReserva.getId());
        return savedReserva;
    }

    @Override
    public Reserva getReservaById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reserva con id: " + id));
    }

    @Override
    public List<Reserva> listReservasByCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    @Override
    public Reserva updateReserva(Long id, Reserva reserva) {
        Reserva existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reserva con id: " + id));

        existingReserva.setFechaInicio(reserva.getFechaInicio());
        existingReserva.setFechaFin(reserva.getFechaFin());
        existingReserva.setTotal(reserva.getTotal());
        existingReserva.setEstado(reserva.getEstado());
        // Se pueden actualizar otros atributos relacionados si es necesario.

        Reserva updatedReserva = reservaRepository.save(existingReserva);
        LOGGER.info("Reserva actualizada con id: {}", updatedReserva.getId());
        return updatedReserva;
    }

    @Override
    public void deleteReserva(Long id) {
        Reserva existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reserva a eliminar con id: " + id));
        reservaRepository.delete(existingReserva);
        LOGGER.info("Reserva eliminada con id: {}", id);
    }
}

