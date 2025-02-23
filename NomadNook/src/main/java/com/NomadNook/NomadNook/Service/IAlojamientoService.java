package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Security.DTO.REQUEST.AlojamientoRequest;

import java.util.List;

public interface IAlojamientoService {


    Alojamiento createAlojamiento(AlojamientoRequest requestDTO);

    Alojamiento getAlojamientoById(Long id);
    List<Alojamiento> listAllAlojamientos();
    Alojamiento updateAlojamiento(Long id, Alojamiento alojamiento);
    void deleteAlojamiento(Long id);
}
