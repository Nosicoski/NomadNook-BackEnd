package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;

import java.util.List;

public interface IAlojamientoService {


    AlojamientoResponse createAlojamiento(AlojamientoRequest requestDTO);

    AlojamientoResponse getAlojamientoById(Long id);
    List<AlojamientoResponse> listAllAlojamientos();
    List<AlojamientoResponse> listAllAlojamientosByPropietario(Long propietario_id);
    AlojamientoResponse updateAlojamiento(Long id, AlojamientoRequest alojamientoRequest);
    void deleteAlojamiento(Long id)throws ResourceNotFoundException;;
    void agregarCaracteristicaAlojamiento(Long alojamiento_id, Long caracteristica_id);
}
