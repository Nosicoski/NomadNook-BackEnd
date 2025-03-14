package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.DTO.RESPONSE.ReservaResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Repository.IReservaRepository;

import com.NomadNook.NomadNook.Service.IReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ReservaService implements IReservaService {
    private final IReservaRepository reservaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);

    public ReservaService(IReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    private ReservaResponse createReservaResponse(Reserva reserva) {

        ReservaResponse reservaResponse = new ReservaResponse();
        reservaResponse.setId(reserva.getId());
        reservaResponse.setFechaReserva(reserva.getFechaReserva());
        reservaResponse.setAlojamiento(reserva.getAlojamiento().getId());
        reservaResponse.setCliente(reserva.getCliente().getId());
        reservaResponse.setEstado(reserva.getEstado());
        reservaResponse.setFechaInicio(reserva.getFechaInicio());
        reservaResponse.setFechaFin(reserva.getFechaFin());
        return reservaResponse;
    }

    @Override
    public ReservaResponse createReserva(Reserva reserva) {
        Reserva savedReserva = reservaRepository.save(reserva);
        LOGGER.info("Reserva creada con id: {}", savedReserva.getId());
        return createReservaResponse(savedReserva);
    }



    @Override
    public ReservaResponse getReservaById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reserva con id: " + id));
        return createReservaResponse(reserva);


    }

    @Override
    public List<ReservaResponse> listAllReservas() {
        List<Reserva> reservas = reservaRepository.findAll();
        List<ReservaResponse> responses = new ArrayList<>();
        for(Reserva reserva : reservas) {
            ReservaResponse response = createReservaResponse(reserva);
            responses.add(response);
        }
        return responses;
    }


    @Override
    public ReservaResponse updateReserva(Long id, Reserva reserva) {
        Reserva existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reserva con id: " + id));

        existingReserva.setFechaInicio(reserva.getFechaInicio());
        existingReserva.setFechaFin(reserva.getFechaFin());
        existingReserva.setTotal(reserva.getTotal());
        existingReserva.setEstado(reserva.getEstado());
        // Se pueden actualizar otros atributos relacionados si es necesario.

        Reserva updatedReserva = reservaRepository.save(existingReserva);
        LOGGER.info("Reserva actualizada con id: {}", updatedReserva.getId());
        return createReservaResponse(updatedReserva);
    }

    @Override
    public void deleteReserva(Long id) {
        Reserva existingReserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reserva a eliminar con id: " + id));
        reservaRepository.delete(existingReserva);
        LOGGER.info("Reserva eliminada con id: {}", id);
    }
}

