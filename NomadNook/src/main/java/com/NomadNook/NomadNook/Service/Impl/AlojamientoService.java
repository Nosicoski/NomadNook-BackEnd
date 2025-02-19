package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import com.NomadNook.NomadNook.Service.IAlojamientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AlojamientoService implements IAlojamientoService {

    private final IUsuarioRepository usuarioRepository;
    private final IAlojamientoRepository alojamientoRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(AlojamientoService.class);

    public AlojamientoService(
            IAlojamientoRepository alojamientoRepository,
            IUsuarioRepository usuarioRepository) {
        this.alojamientoRepository = alojamientoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Alojamiento createAlojamiento(Alojamiento alojamiento) {

        Alojamiento savedAlojamiento = alojamientoRepository.save(alojamiento);
        LOGGER.info("Alojamiento creado con id: {}", savedAlojamiento.getId());
        return alojamiento;
    }

    @Override
    public Alojamiento getAlojamientoById(Long id) {
        return alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento con id: " + id));
    }

    @Override
    public List<Alojamiento> listAllAlojamientos() {
        return alojamientoRepository.findAll();
    }

    @Override
    public Alojamiento updateAlojamiento(Long id, Alojamiento alojamiento) {
        Alojamiento existingAlojamiento = alojamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento con id: " + id));

        existingAlojamiento.setTitulo(alojamiento.getTitulo());
        existingAlojamiento.setDescripcion(alojamiento.getDescripcion());
        existingAlojamiento.setTipo(alojamiento.getTipo());
        existingAlojamiento.setCapacidad(alojamiento.getCapacidad());
        existingAlojamiento.setPrecioPorNoche(alojamiento.getPrecioPorNoche());
        existingAlojamiento.setUbicacion(alojamiento.getUbicacion());
        existingAlojamiento.setDireccion(alojamiento.getDireccion());
        existingAlojamiento.setDisponible(alojamiento.getDisponible());
        // Si se requiere, aquí se puede actualizar la lista de imágenes u otros atributos relacionados.

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
}

