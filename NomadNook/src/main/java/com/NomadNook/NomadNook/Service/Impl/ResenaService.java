package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Resena;
import com.NomadNook.NomadNook.Repository.IReseñaRepository;
import com.NomadNook.NomadNook.Service.IResenaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ResenaService implements IResenaService {   private final IReseñaRepository resenaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ResenaService.class);

    public ResenaService(IReseñaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    @Override
    public Resena createResena(Resena resena) {
        Resena savedResena = resenaRepository.save(resena);
        LOGGER.info("Reseña creada con id: {}", savedResena.getId());
        return savedResena;
    }

    @Override
    public Resena getResenaById(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reseña con id: " + id));
    }

    @Override
    public List<Resena> listAllResenas() {
        return resenaRepository.findAll();
    }

    @Override
    public Resena updateResena(Long id, Resena resena) {
        Resena existingResena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reseña con id: " + id));

        existingResena.setPuntuacion(resena.getPuntuacion());
        existingResena.setComentario(resena.getComentario());
        // Se pueden actualizar relaciones (cliente o alojamiento) si fuera necesario

        Resena updatedResena = resenaRepository.save(existingResena);
        LOGGER.info("Reseña actualizada con id: {}", updatedResena.getId());
        return updatedResena;
    }

    @Override
    public void deleteResena(Long id) {
        Resena existingResena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reseña a eliminar con id: " + id));
        resenaRepository.delete(existingResena);
        LOGGER.info("Reseña eliminada con id: {}", id);
    }
}
