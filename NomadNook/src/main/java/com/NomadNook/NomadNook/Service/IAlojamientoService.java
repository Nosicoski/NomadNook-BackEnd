package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Alojamiento;

import java.util.List;

public interface IAlojamientoService {
    Alojamiento createAlojamiento(Alojamiento alojamiento);
    Alojamiento getAlojamientoById(Long id);
    List<Alojamiento> listAllAlojamientos();
    Alojamiento updateAlojamiento(Long id, Alojamiento alojamiento);
    void deleteAlojamiento(Long id);
}
