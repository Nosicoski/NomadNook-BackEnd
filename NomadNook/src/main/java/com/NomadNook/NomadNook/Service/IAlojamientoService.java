package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Model.Categoria;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IAlojamientoService {


    AlojamientoResponse createAlojamiento(AlojamientoRequest requestDTO);

    AlojamientoResponse getAlojamientoById(Long id);
    List<AlojamientoResponse> listAllAlojamientos();
    List<AlojamientoResponse> listAllAlojamientosByPropietario(Long propietario_id);
    AlojamientoResponse updateAlojamiento(Long id, AlojamientoRequest alojamientoRequest);
    void deleteAlojamiento(Long id)throws ResourceNotFoundException;;
    void agregarCaracteristicaAlojamiento(Long alojamiento_id, Long caracteristica_id);
    void agregarCategoriaAlojamiento(Long alojamiento_id, Long categoria_id);

    void agregarCaracteristicasAlojamiento(Long alojamientoId, Set<Caracteristica> caracteristicas);

    void agregarCategoriasAlojamiento(Long alojamientoId, Set<Categoria> categorias);

    List<AlojamientoResponse> buscarAlojamientosDisponibles(LocalDate fechaInicio, LocalDate fechaFin);
}
