package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.DTO.REQUEST.CaracteristicaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.ImagenResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.ICaracteristicaRepository;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import com.NomadNook.NomadNook.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.Service.IAlojamientoService;
import com.NomadNook.NomadNook.Service.ICaracteristicaService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AlojamientoService implements IAlojamientoService {

    private final IAlojamientoRepository alojamientoRepository;
    private final ICaracteristicaRepository caracteristicaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(AlojamientoService.class);
    private ModelMapper modelMapper;

    @Value("${api.path}")
    private String API_PATH;

    @Autowired
    public AlojamientoService(
            ModelMapper modelMapper,
            IAlojamientoRepository alojamientoRepository,
            ICaracteristicaRepository caracteristicaRepository) {
        this.modelMapper = modelMapper;
        this.alojamientoRepository = alojamientoRepository;
        this.caracteristicaRepository = caracteristicaRepository;
    }

    private AlojamientoResponse createAlojamientoResponse(Alojamiento alojamiento) {

        AlojamientoResponse alojamientoResponse = new AlojamientoResponse();
        alojamientoResponse.setId(alojamiento.getId());
        alojamientoResponse.setTitulo(alojamiento.getTitulo());
        alojamientoResponse.setDescripcion(alojamiento.getDescripcion());
        alojamientoResponse.setCategoria(alojamiento.getCategorias());
        alojamientoResponse.setCapacidad(alojamiento.getCapacidad());
        alojamientoResponse.setPrecioPorNoche(alojamiento.getPrecioPorNoche());
        alojamientoResponse.setUbicacion(alojamiento.getUbicacion());
        alojamientoResponse.setDireccion(alojamiento.getDireccion());
        alojamientoResponse.setDisponible(alojamiento.getDisponible());
        alojamientoResponse.setPropietario_id(alojamiento.getPropietario().getId());
        alojamientoResponse.setImagenes(alojamiento.getImagenes());
        alojamientoResponse.setCaracteristicas(API_PATH + "caracteristicas/buscar/alojamiento/" + alojamientoResponse.getId());
        return alojamientoResponse;
    }

    @Override
    public AlojamientoResponse createAlojamiento(AlojamientoRequest requestDTO) {
        // Mapear el request DTO a la entidad
        Alojamiento alojamiento = modelMapper.map(requestDTO, Alojamiento.class);

        // Guardar la entidad
        Alojamiento alojamientoGuardado = alojamientoRepository.save(alojamiento);

        // Mapear la entidad persistida a un DTO de respuesta
        return createAlojamientoResponse(alojamientoGuardado);
    }


    @Override
    public AlojamientoResponse getAlojamientoById(Long id) {
        Alojamiento alojamiento = alojamientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento con id: " + id));
        return createAlojamientoResponse(alojamiento);
    }

    @Override
    public List<AlojamientoResponse> listAllAlojamientos() {
        List<Alojamiento> alojamientos = alojamientoRepository.findAll();
        List<AlojamientoResponse> responses = new ArrayList<>();
        for (Alojamiento alojamiento : alojamientos) {
            AlojamientoResponse alojamientoResponse = createAlojamientoResponse(alojamiento);
            responses.add(alojamientoResponse);
        }
        return responses;
    }

    @Override
    public AlojamientoResponse updateAlojamiento(Long id, AlojamientoRequest request) {
        // Verificar si el alojamiento con el id existe
        Alojamiento existingAlojamiento = alojamientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento con id: " + id));

        // Verificar si el nombre ha cambiado y si ya existe otro alojamiento con ese nombre
        if (!existingAlojamiento.getTitulo().equals(request.getTitulo()) && alojamientoRepository.existsByTitulo(request.getTitulo())) {
            throw new IllegalArgumentException("Ya existe un alojamiento con el mismo nombre");
        }

        // Usar ModelMapper para actualizar la entidad existente con los valores del request DTO.
        // Esto copia las propiedades con nombres y tipos coincidentes.
        modelMapper.map(request, existingAlojamiento);

        // Guardar el alojamiento actualizado
        Alojamiento updatedAlojamiento = alojamientoRepository.save(existingAlojamiento);
        LOGGER.info("Alojamiento actualizado con id: {}", updatedAlojamiento.getId());
        return createAlojamientoResponse(updatedAlojamiento);
    }


    @Override
    public void deleteAlojamiento(Long id) {
        Alojamiento existingAlojamiento = alojamientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento a eliminar con id: " + id));
        alojamientoRepository.delete(existingAlojamiento);
        LOGGER.info("Alojamiento eliminado con id: {}", id);
    }

    @Override
    public List<AlojamientoResponse> listAllAlojamientosByPropietario(Long propietario_id) {
        //log.info("Entre al service");
        List<Alojamiento> alojamientos = alojamientoRepository.findAllByPropietarioId(propietario_id);
        List<AlojamientoResponse> responses = new ArrayList<>();
        for (Alojamiento alojamiento : alojamientos) {
            AlojamientoResponse alojamientoResponse = createAlojamientoResponse(alojamiento);
            responses.add(alojamientoResponse);
        }
        return responses;
    }

    @Override
    public void agregarCaracteristicaAlojamiento(Long alojamientoId, Long caracteristicaId) {
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado"));

        Caracteristica caracteristica = caracteristicaRepository.findById(caracteristicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Característica no encontrada"));

        alojamiento.getCaracteristicas().add(caracteristica);
        alojamientoRepository.save(alojamiento);
    }


}

