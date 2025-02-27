package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import com.NomadNook.NomadNook.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.Service.IAlojamientoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class AlojamientoService implements IAlojamientoService {

    private final IUsuarioRepository usuarioRepository;
    private final IAlojamientoRepository alojamientoRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(AlojamientoService.class);
    private ModelMapper modelMapper;

    @Value("${api.path}")
    private String API_PATH;

    @Autowired
    public AlojamientoService(ModelMapper modelMapper, IAlojamientoRepository alojamientoRepository, IUsuarioRepository usuarioRepository) {
        this.modelMapper = modelMapper;
        this.alojamientoRepository = alojamientoRepository;
        this.usuarioRepository = usuarioRepository;
    }




    @Override
    public AlojamientoResponse createAlojamiento(AlojamientoRequest requestDTO) {
        // Mapear el request DTO a la entidad
        Alojamiento alojamiento = modelMapper.map(requestDTO, Alojamiento.class);

        // Guardar la entidad
        Alojamiento alojamientoGuardado = alojamientoRepository.save(alojamiento);

        // Mapear la entidad persistida a un DTO de respuesta
        return modelMapper.map(alojamientoGuardado, AlojamientoResponse.class);
    }


    @Override
    public Alojamiento getAlojamientoById(Long id) {
        return alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento con id: " + id));
    }

    @Override
    public List<AlojamientoResponse> listAllAlojamientos() {
        List<Alojamiento> alojamientos = alojamientoRepository.findAll();
        List<AlojamientoResponse> listRespAlojamientos = new ArrayList<>();
        for(Alojamiento alojamiento: alojamientos) {
            AlojamientoResponse alojamientoResponse = new AlojamientoResponse();
            alojamientoResponse.setTitulo(alojamiento.getTitulo());
            alojamientoResponse.setDescripcion(alojamiento.getDescripcion());
            alojamientoResponse.setTipo(alojamiento.getTipo());
            alojamientoResponse.setCapacidad(alojamiento.getCapacidad());
            alojamientoResponse.setPrecioPorNoche(alojamiento.getPrecioPorNoche());
            alojamientoResponse.setUbicacion(alojamiento.getUbicacion());
            alojamientoResponse.setDireccion(alojamiento.getDireccion());
            alojamientoResponse.setDisponible(alojamiento.getDisponible());
            alojamientoResponse.setPropietario_id(alojamiento.getPropietario().getId());
            alojamientoResponse.setImagenes(API_PATH + "terminar de crear la ruta");
            listRespAlojamientos.add(alojamientoResponse);
        }
        return listRespAlojamientos;
    }

    @Override
    public Alojamiento updateAlojamiento(Long id, AlojamientoRequest request) {
        // Verificar si el alojamiento con el id existe
        Alojamiento existingAlojamiento = alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento con id: " + id));

        // Verificar si el nombre ha cambiado y si ya existe otro alojamiento con ese nombre
        if (!existingAlojamiento.getTitulo().equals(request.getTitulo()) &&
                alojamientoRepository.existsByTitulo(request.getTitulo())) {
            throw new IllegalArgumentException("Ya existe un alojamiento con el mismo nombre");
        }

        // Usar ModelMapper para actualizar la entidad existente con los valores del request DTO.
        // Esto copia las propiedades con nombres y tipos coincidentes.
        modelMapper.map(request, existingAlojamiento);

        // Guardar el alojamiento actualizado
        Alojamiento updatedAlojamiento = alojamientoRepository.save(existingAlojamiento);
        LOGGER.info("Alojamiento actualizado con id: {}", updatedAlojamiento.getId());
        return updatedAlojamiento;
    }


    @Override
    public void deleteAlojamiento(Long id) {
        Alojamiento existingAlojamiento = alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento a eliminar con id: " + id));
        alojamientoRepository.delete(existingAlojamiento);
        LOGGER.info("Alojamiento eliminado con id: {}", id);
    }


    @Override
    public List<AlojamientoResponse> searchAlojamientos(String query) {
        // Buscar alojamientos cuyo título o descripción contenga la consulta
        List<Alojamiento> alojamientos = alojamientoRepository
                .findByTituloContainingIgnoreCaseOrDescripcionContainingIgnoreCase(query, query);

        // Convertir cada entidad a su DTO de respuesta utilizando ModelMapper
        return alojamientos.stream()
                .map(alojamiento -> modelMapper.map(alojamiento, AlojamientoResponse.class))
                .toList();
    }

}

