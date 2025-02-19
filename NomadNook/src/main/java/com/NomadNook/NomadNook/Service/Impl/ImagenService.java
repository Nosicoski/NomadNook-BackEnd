package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Repository.IImagenRepository;
import com.NomadNook.NomadNook.Service.IImagenService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenService implements IImagenService {

    private final IImagenRepository imagenRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ImagenService.class);  // Corregido
    private final Validator validator;

    // Inyección del Validator mediante el constructor
    @Autowired
    public ImagenService(IImagenRepository imagenRepository, Validator validator) {
        this.imagenRepository = imagenRepository;
        this.validator = validator;
    }

    @Override
    public Imagen createImagen(Imagen imagen) {
        // Validar antes de guardar la imagen
        var violations = validator.validate(imagen);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Imagen savedImagen = imagenRepository.save(imagen);
        LOGGER.info("Imagen creada con id: {}", savedImagen.getId());
        return savedImagen;
    }

    @Override
    public Imagen getImagenById(Long id) {
        return imagenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la imagen con id: " + id));
    }

    @Override
    public List<Imagen> listAllImagen() {
        return imagenRepository.findAll();
    }

    @Override
    public Imagen updateImagen(Long id, Imagen imagen) {
        // Validación previa
        var violations = validator.validate(imagen);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Verificar si la imagen existe antes de actualizar
        Imagen existingImagen = imagenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la imagen con id: " + id));

        // Actualizar los atributos de la imagen
        existingImagen.setAlojamiento(imagen.getAlojamiento());
        existingImagen.setUrl(imagen.getUrl());

        Imagen updatedImagen = imagenRepository.save(existingImagen);
        LOGGER.info("Imagen actualizada con id: {}", updatedImagen.getId());
        return updatedImagen;
    }

    @Override
    public void deleteImagen(Long id) {
        // Verificar si la imagen existe antes de eliminar
        Imagen existingImagen = imagenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la imagen a eliminar con id: " + id));
        imagenRepository.delete(existingImagen);
        LOGGER.info("Imagen eliminada con id: {}", id);
    }
}

