package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Security.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.Security.DTO.RESPONSE.AlojamientoResponse;

import java.util.List;

public interface IAlojamientoService {


    AlojamientoResponse createAlojamiento(AlojamientoRequest requestDTO);

    Alojamiento getAlojamientoById(Long id);
    List<Alojamiento> listAllAlojamientos();
    Alojamiento updateAlojamiento(Long id, Alojamiento alojamiento);
    void deleteAlojamiento(Long id);
}
