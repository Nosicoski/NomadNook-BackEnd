package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.DTO.REQUEST.CaracteristicaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Repository.ICaracteristicaRepository;
import com.NomadNook.NomadNook.Service.ICaracteristicaService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaracteristicaService implements ICaracteristicaService {

    private final ICaracteristicaRepository caractereisticaRepository;
    private final ModelMapper modelMapper;

    public CaracteristicaService(ICaracteristicaRepository caractereisticaRepository, ModelMapper modelMapper) {
        this.caractereisticaRepository = caractereisticaRepository;
        this.modelMapper = modelMapper;
    }

    private CaracteristicaResponse createCaracteristicaResponse(Caracteristica caracteristica) {
        CaracteristicaResponse response = new CaracteristicaResponse();
        response.setId(caracteristica.getId());
        response.setIcono(caracteristica.getIcono());
        response.setNombre(caracteristica.getNombre());
        return response;
    }

    @Override
    public CaracteristicaResponse createCaracteristica(CaracteristicaRequest request) {
        Caracteristica caracteristica = modelMapper.map(request, Caracteristica.class);

        Caracteristica caracteristicaGuardada = caractereisticaRepository.save(caracteristica);
        return createCaracteristicaResponse(caracteristicaGuardada);
    }

    @Override
    public CaracteristicaResponse getCaracteristicaById(Long id) {
        return null;
    }

    @Override
    public List<CaracteristicaResponse> listAllCaracteristicas() {
        List<Caracteristica> caracteristicas = caractereisticaRepository.findAll();
        List<CaracteristicaResponse> responses = new ArrayList<>();
        for(Caracteristica caracteristica: caracteristicas) {
            CaracteristicaResponse response = createCaracteristicaResponse(caracteristica);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public CaracteristicaResponse updateCaracteristica(Long id, CaracteristicaRequest request) {
        return null;
    }

    @Override
    public void deleteCaracteristica(Long id) {

    }
}

