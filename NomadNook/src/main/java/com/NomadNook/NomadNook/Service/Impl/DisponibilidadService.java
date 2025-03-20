package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoReducidoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.ReservaRangoResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Disponibilidad;
import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
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
    private final IAlojamientoRepository alojamientoRepository;

    public DisponibilidadService(IDisponibilidadRepository disponibilidadRepository, IReservaRepository reservaRepository, IAlojamientoRepository alojamientoRespository) {
        this.disponibilidadRepository = disponibilidadRepository;
        this.reservaRepository = reservaRepository;
        this.alojamientoRepository = alojamientoRespository;
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

    //Cuando haya tiempo, refactorizar
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

    public List<LocalDate> obtenerDiasDisponibles(Long alojamientoId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener todas las reservas confirmadas o pendientes para el alojamiento en el rango de fechas
        List<Reserva> reservas = reservaRepository.findByAlojamientoIdAndEstadoInAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                alojamientoId,
                Arrays.asList(Reserva.EstadoReserva.CONFIRMADA, Reserva.EstadoReserva.PENDIENTE),
                fechaFin,
                fechaInicio
        );

        // Crear lista de todos los días en el rango
        List<LocalDate> todosLosDias = new ArrayList<>();
        LocalDate fecha = fechaInicio;
        while (!fecha.isAfter(fechaFin)) {
            todosLosDias.add(fecha);
            fecha = fecha.plusDays(1);
        }

        // Crear lista de días no disponibles
        List<LocalDate> diasNoDisponibles = new ArrayList<>();
        for (Reserva reserva : reservas) {
            LocalDate fechaReserva = reserva.getFechaInicio();
            while (!fechaReserva.isAfter(reserva.getFechaFin())) {
                diasNoDisponibles.add(fechaReserva);
                fechaReserva = fechaReserva.plusDays(1);
            }
        }

        // Eliminar días no disponibles de la lista de todos los días
        todosLosDias.removeAll(diasNoDisponibles);

        return todosLosDias;
    }

    public List<ReservaRangoResponse> obtenerRangosNoDisponibles(Long alojamientoId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener todas las reservas confirmadas o pendientes para el alojamiento en el rango de fechas
        List<Reserva> reservas = reservaRepository.findByAlojamientoIdAndEstadoInAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                alojamientoId,
                Arrays.asList(Reserva.EstadoReserva.CONFIRMADA, Reserva.EstadoReserva.PENDIENTE),
                fechaFin,
                fechaInicio
        );

        List<ReservaRangoResponse> rangosNoDisponibles = new ArrayList<>();

        // Para cada reserva, añadir todos los días entre fechaInicio y fechaFin
        for (Reserva reserva : reservas) {
            ReservaRangoResponse rango = new ReservaRangoResponse();
            rango.setFechaInicio(reserva.getFechaInicio());
            rango.setFechaFin(reserva.getFechaFin());
            rangosNoDisponibles.add(rango);
        }

        return rangosNoDisponibles;
    }

    public List<AlojamientoReducidoResponse> buscarAlojamientosDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener todos los alojamientos
        List<Alojamiento> todosAlojamientos = alojamientoRepository.findAll();

        List<AlojamientoReducidoResponse> alojamientosDisponibles = new ArrayList<>();

        for (Alojamiento alojamiento : todosAlojamientos) {
            List<Reserva> reservasExistentes = reservaRepository.findByAlojamientoIdAndEstadoInAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                    alojamiento.getId(),
                    Arrays.asList(Reserva.EstadoReserva.CONFIRMADA, Reserva.EstadoReserva.PENDIENTE),
                    fechaFin,
                    fechaInicio
            );

            if (reservasExistentes.isEmpty()) {
                AlojamientoReducidoResponse response = new AlojamientoReducidoResponse();
                response.setId(alojamiento.getId());
                response.setTitulo(alojamiento.getTitulo());
                response.setDescripcion(alojamiento.getDescripcion());

                alojamientosDisponibles.add(response);
            }
        }

        return alojamientosDisponibles;
    }
}