package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoReducidoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.ReservaRangoResponse;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Disponibilidad;

import java.time.LocalDate;
import java.util.List;

public interface IDisponibilidadService {
    Disponibilidad createDisponibilidad(Disponibilidad disponibilidad);
    Disponibilidad getDisponibilidadById(Long id);
    List<Disponibilidad> listAllDisponibilidades();
    Disponibilidad updateDisponibilidad(Long id, Disponibilidad disponibilidad);
    void deleteDisponibilidad(Long id);
    List<LocalDate> obtenerDiasNoDisponibles(Long alojamientoId, LocalDate fechaInicio, LocalDate fechaFin);
    List<LocalDate> obtenerDiasDisponibles(Long alojamientoId, LocalDate fechaInicio, LocalDate fechaFin);

    List<ReservaRangoResponse> obtenerRangosNoDisponibles(Long alojamientoId, LocalDate fechaInicio, LocalDate fechaFin);

    List<AlojamientoReducidoResponse> buscarAlojamientosDisponibles(LocalDate fechaInicio, LocalDate fechaFin);
}
