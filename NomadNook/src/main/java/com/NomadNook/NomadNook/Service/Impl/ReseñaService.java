package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Reseña;
import com.NomadNook.NomadNook.Repository.IReseñaRepository;
import com.NomadNook.NomadNook.Service.IReservaService;
import com.NomadNook.NomadNook.Service.IReseñaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReseñaService implements IReseñaService {   private final IReseñaRepository resenaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ReseñaService.class);

    public ReseñaService(IReseñaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    @Override
    public Reseña createResena(Reseña resena) {
        Reseña savedResena = resenaRepository.save(resena);
        LOGGER.info("Reseña creada con id: {}", savedResena.getId());
        return savedResena;
    }

    @Override
    public Reseña getResenaById(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reseña con id: " + id));
    }

    @Override
    public List<Reseña> listAllResenas() {
        return resenaRepository.findAll();
    }

    @Override
    public Reseña updateResena(Long id, Reseña resena) {
        Reseña existingResena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reseña con id: " + id));

        existingResena.setPuntuacion(resena.getPuntuacion());
        existingResena.setComentario(resena.getComentario());
        // Se pueden actualizar relaciones (cliente o alojamiento) si fuera necesario

        Reseña updatedResena = resenaRepository.save(existingResena);
        LOGGER.info("Reseña actualizada con id: {}", updatedResena.getId());
        return updatedResena;
    }

    @Override
    public void deleteResena(Long id) {
        Reseña existingResena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la reseña a eliminar con id: " + id));
        resenaRepository.delete(existingResena);
        LOGGER.info("Reseña eliminada con id: {}", id);
    }
}
