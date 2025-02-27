package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.DTO.REQUEST.CaracteristicaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Repository.ICaracteristicaRepository;
import com.NomadNook.NomadNook.Service.ICaracteristicaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaracteristicaService implements ICaracteristicaService {

    private final ICaracteristicaRepository caractereisticaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CaracteristicaService.class);
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
        Caracteristica caracteristica = caractereisticaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la característica con id: " + id));

        return createCaracteristicaResponse(caracteristica);
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
        Caracteristica existingCaracteristica = caractereisticaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la característica con id: " + id));

        if(!existingCaracteristica.getNombre().equals(request.getNombre())
                && caractereisticaRepository.existsByNombre(request.getNombre())) {
            throw new IllegalArgumentException("Ya existe una caracteristica con el mismo nombre");
        }

        modelMapper.map(request, existingCaracteristica);

        Caracteristica updatedCaracteristica = caractereisticaRepository.save(existingCaracteristica);
        LOGGER.info("Caracteristica actualizada con id: {}", updatedCaracteristica.getId());
        return createCaracteristicaResponse(updatedCaracteristica);
    }

    @Override
    public void deleteCaracteristica(Long id) {

    }
}

