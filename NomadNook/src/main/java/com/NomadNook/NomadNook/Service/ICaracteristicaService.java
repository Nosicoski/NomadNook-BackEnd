package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Caracteristica;

import java.util.List;

public interface ICaracteristicaService {

    Caracteristica createCaracteristica(Caracteristica caracteristica);
    Caracteristica getCaracteristicaById(Long id);
    List<Caracteristica> listAllCaracteristicas();
    Caracteristica updateCaracteristica(Long id, Caracteristica caracteristica);
    void deleteCaracteristica(Long id);
}
