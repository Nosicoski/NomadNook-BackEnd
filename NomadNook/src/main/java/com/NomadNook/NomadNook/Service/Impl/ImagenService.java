package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.ImagenResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Repository.IImagenRepository;
import com.NomadNook.NomadNook.Service.IImagenService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagenService implements IImagenService {

    private final IImagenRepository imagenRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ImagenService.class);  // Corregido
    private final Validator validator;
    private ModelMapper modelMapper;

    // Inyección del Validator mediante el constructor
    @Autowired
    public ImagenService(IImagenRepository imagenRepository, Validator validator, ModelMapper modelMapper) {
        this.imagenRepository = imagenRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    private ImagenResponse createImagenResponse(Imagen imagen) {
        ImagenResponse imagenResponse = new ImagenResponse();
        imagenResponse.setId(imagen.getId());
        imagenResponse.setUrl(imagen.getUrl());
        imagenResponse.setAlojamiento_id(imagen.getAlojamiento().getId());

        return imagenResponse;
    }

    @Override
    public ImagenResponse createImagen(Imagen imagen) {
        // Validar antes de guardar la imagen
        var violations = validator.validate(imagen);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Imagen savedImagen = imagenRepository.save(imagen);

        LOGGER.info("Imagen creada con id: {}", savedImagen.getId());
        return createImagenResponse(savedImagen);
    }

    @Override
    public ImagenResponse getImagenById(Long id) {
        Imagen imagen =  imagenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la imagen con id: " + id));
        return createImagenResponse(imagen);
    }

    @Override
    public List<ImagenResponse> listAllImagen() {

        List<Imagen> imagenes = imagenRepository.findAll();
        List<ImagenResponse> responses = new ArrayList<>();
        for(Imagen imagen: imagenes) {
            ImagenResponse response = createImagenResponse(imagen);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public ImagenResponse updateImagen(Long id, Imagen imagen) {
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
        return createImagenResponse(updatedImagen);
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

