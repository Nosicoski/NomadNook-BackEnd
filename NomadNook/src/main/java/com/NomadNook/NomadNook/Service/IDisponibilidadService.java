package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Disponibilidad;

import java.util.List;

public interface IDisponibilidadService {
    Disponibilidad createDisponibilidad(Disponibilidad disponibilidad);
    Disponibilidad getDisponibilidadById(Long id);
    List<Disponibilidad> listAllDisponibilidades();
    Disponibilidad updateDisponibilidad(Long id, Disponibilidad disponibilidad);
    void deleteDisponibilidad(Long id);
}
