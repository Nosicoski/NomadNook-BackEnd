package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.REQUEST.CaracteristicaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.Model.Caracteristica;

import java.util.List;

public interface ICaracteristicaService {

    CaracteristicaResponse createCaracteristica(CaracteristicaRequest request);
    CaracteristicaResponse getCaracteristicaById(Long id);
    List<CaracteristicaResponse> listAllCaracteristicas();
    CaracteristicaResponse updateCaracteristica(Long id, CaracteristicaRequest request);
    void deleteCaracteristica(Long id);
}
