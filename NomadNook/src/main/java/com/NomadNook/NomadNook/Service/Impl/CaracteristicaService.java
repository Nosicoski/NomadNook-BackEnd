package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Service.ICaracteristicaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaracteristicaService implements ICaracteristicaService {
    @Override
    public Caracteristica createCaracteristica(Caracteristica caracteristica) {
        return null;
    }

    @Override
    public Caracteristica getCaracteristicaById(Long id) {
        return null;
    }

    @Override
    public List<Caracteristica> listAllCaracteristicas() {
        return List.of();
    }

    @Override
    public Caracteristica updateCaracteristica(Long id, Caracteristica caracteristica) {
        return null;
    }

    @Override
    public void deleteCaracteristica(Long id) {

    }
}
