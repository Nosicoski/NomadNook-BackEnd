package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Disponibilidad;
import com.NomadNook.NomadNook.Repository.IDisponibilidadRepository;
import com.NomadNook.NomadNook.Service.IDisponibilidadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisponibilidadService implements IDisponibilidadService {  private final IDisponibilidadRepository disponibilidadRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(DisponibilidadService.class);

    public DisponibilidadService(IDisponibilidadRepository disponibilidadRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
    }

    @Override
    public Disponibilidad createDisponibilidad(Disponibilidad disponibilidad) {
        Disponibilidad saved = disponibilidadRepository.save(disponibilidad);
        LOGGER.info("Disponibilidad creada con id: {}", saved.getId());
        return saved;
    }

    @Override
    public Disponibilidad getDisponibilidadById(Long id) {
        return disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la disponibilidad con id: " + id));
    }

    @Override
    public List<Disponibilidad> listAllDisponibilidades() {
        return disponibilidadRepository.findAll();
    }

    @Override
    public Disponibilidad updateDisponibilidad(Long id, Disponibilidad disponibilidad) {
        Disponibilidad existente = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la disponibilidad con id: " + id));
        existente.setFechaDisponible(disponibilidad.getFechaDisponible());
        // Si existieran otros campos a actualizar, agrégalos aquí.
        Disponibilidad actualizado = disponibilidadRepository.save(existente);
        LOGGER.info("Disponibilidad actualizada con id: {}", actualizado.getId());
        return actualizado;
    }

    @Override
    public void deleteDisponibilidad(Long id) {
        Disponibilidad existente = disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la disponibilidad con id: " + id));
        disponibilidadRepository.delete(existente);
        LOGGER.info("Disponibilidad eliminada con id: {}", id);
    }
}