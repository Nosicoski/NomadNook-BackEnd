package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.DTO.REQUEST.CaracteristicaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.ICaracteristicaRepository;
import com.NomadNook.NomadNook.Service.ICaracteristicaService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaracteristicaService implements ICaracteristicaService {

    private final ICaracteristicaRepository caracteristicaRepository;
    private final IAlojamientoRepository alojamientoRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CaracteristicaService.class);
    private final ModelMapper modelMapper;

    public CaracteristicaService(ICaracteristicaRepository caractereisticaRepository, IAlojamientoRepository alojamientoRepository, ModelMapper modelMapper) {
        this.caracteristicaRepository = caractereisticaRepository;
        this.alojamientoRepository = alojamientoRepository;
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

        Caracteristica caracteristicaGuardada = caracteristicaRepository.save(caracteristica);
        return createCaracteristicaResponse(caracteristicaGuardada);
    }

    @Override
    public CaracteristicaResponse getCaracteristicaById(Long id) {
        Caracteristica caracteristica = caracteristicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la característica con id: " + id));

        return createCaracteristicaResponse(caracteristica);
    }

    @Override
    public List<CaracteristicaResponse> listAllCaracteristicas() {
        List<Caracteristica> caracteristicas = caracteristicaRepository.findAll();
        List<CaracteristicaResponse> responses = new ArrayList<>();
        for(Caracteristica caracteristica: caracteristicas) {
            CaracteristicaResponse response = createCaracteristicaResponse(caracteristica);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<CaracteristicaResponse> listAllCaracteristicasByAlojamiento(Long id) {
        List<Caracteristica> caracteristicas = caracteristicaRepository.findCaracteristicasByAlojamientoId(id);
        List<CaracteristicaResponse> responses = new ArrayList<>();
        for(Caracteristica caracteristica: caracteristicas) {
            CaracteristicaResponse response = createCaracteristicaResponse(caracteristica);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public CaracteristicaResponse updateCaracteristica(Long id, CaracteristicaRequest request) {
        Caracteristica existingCaracteristica = caracteristicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la característica con id: " + id));

        if(!existingCaracteristica.getNombre().equals(request.getNombre())
                && caracteristicaRepository.existsByNombre(request.getNombre())) {
            throw new IllegalArgumentException("Ya existe una caracteristica con el mismo nombre");
        }

        modelMapper.map(request, existingCaracteristica);

        Caracteristica updatedCaracteristica = caracteristicaRepository.save(existingCaracteristica);
        LOGGER.info("Caracteristica actualizada con id: {}", updatedCaracteristica.getId());
        return createCaracteristicaResponse(updatedCaracteristica);
    }

    @Override
    public void deleteCaracteristica(Long id) {
        Caracteristica caracteristica = caracteristicaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caracteristica not found"));

        List<Alojamiento> associatedAlojamientos = alojamientoRepository
                .findByCaracteristicas(caracteristica);

        for (Alojamiento alojamiento : associatedAlojamientos) {
            alojamiento.getCaracteristicas().remove(caracteristica);
            alojamientoRepository.save(alojamiento);
        }

        caracteristicaRepository.delete(caracteristica);
        LOGGER.info("Caracteristica eliminada con id: {}", id);
    }
}

