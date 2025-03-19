package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Disponibilidad;
import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Repository.IDisponibilidadRepository;
import com.NomadNook.NomadNook.Repository.IReservaRepository;
import com.NomadNook.NomadNook.Service.IDisponibilidadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DisponibilidadService implements IDisponibilidadService {  private final IDisponibilidadRepository disponibilidadRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(DisponibilidadService.class);
    private final IReservaRepository reservaRepository;

    public DisponibilidadService(IDisponibilidadRepository disponibilidadRepository, IReservaRepository reservaRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Disponibilidad createDisponibilidad(Disponibilidad disponibilidad) {
        Disponibilidad saved = disponibilidadRepository.save(disponibilidad);
        LOGGER.info("Disponibilidad creada con id: {}", saved.getId());
        return saved;
    }

    @Override
    public Disponibilidad getDisponibilidadById(Long id) {
        return disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la disponibilidad con id: " + id));
    }

    @Override
    public List<Disponibilidad> listAllDisponibilidades() {
        return disponibilidadRepository.findAll();
    }

    @Override
    public Disponibilidad updateDisponibilidad(Long id, Disponibilidad disponibilidad) {
        Disponibilidad existente = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la disponibilidad con id: " + id));
        existente.setFechaDisponible(disponibilidad.getFechaDisponible());
        // Si existieran otros campos a actualizar, agrégalos aquí.
        Disponibilidad actualizado = disponibilidadRepository.save(existente);
        LOGGER.info("Disponibilidad actualizada con id: {}", actualizado.getId());
        return actualizado;
    }

    @Override
    public void deleteDisponibilidad(Long id) {
        Disponibilidad existente = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la disponibilidad con id: " + id));
        disponibilidadRepository.delete(existente);
        LOGGER.info("Disponibilidad eliminada con id: {}", id);
    }

    public List<LocalDate> obtenerDiasNoDisponibles(Long alojamientoId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener todas las reservas confirmadas o pendientes para el alojamiento en el rango de fechas
        List<Reserva> reservas = reservaRepository.findByAlojamientoIdAndEstadoInAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                alojamientoId,
                Arrays.asList(Reserva.EstadoReserva.CONFIRMADA, Reserva.EstadoReserva.PENDIENTE),
                fechaFin,
                fechaInicio
        );

        List<LocalDate> diasNoDisponibles = new ArrayList<>();

        // Para cada reserva, añadir todos los días entre fechaInicio y fechaFin
        for (Reserva reserva : reservas) {
            LocalDate fecha = reserva.getFechaInicio();
            while (!fecha.isAfter(reserva.getFechaFin())) {
                diasNoDisponibles.add(fecha);
                fecha = fecha.plusDays(1);
            }
        }

        return diasNoDisponibles;
    }
}