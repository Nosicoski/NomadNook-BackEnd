package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Repository.IImagenRepository;
import com.NomadNook.NomadNook.Service.IImagenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenService implements IImagenService {

    private final IImagenRepository imagenRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    public ImagenService(IImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }


    @Override
    public Imagen createImagen(Imagen imagen) {
        Imagen savedImagen = imagenRepository.save(imagen);
        LOGGER.info("Imagen creado con id: {}", savedImagen.getId());
        return savedImagen;
    }

    @Override
    public Imagen getImagenById(Long id) {
        return imagenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró la imagen con id: " + id));
    }

    @Override
    public List<Imagen> listAllImagen() {
        return imagenRepository.findAll();
    }

    @Override
    public Imagen updateImagen(Long id, Imagen imagen) {
        Imagen existingImagen = imagenRepository.findById(id)  // Cambiado de User a Usuario
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la imagen con id: " + id));
        existingImagen.setAlojamiento(imagen.getAlojamiento());
        existingImagen.setUrl(imagen.getUrl());
        Imagen updatedImagen = imagenRepository.save(existingImagen);
        LOGGER.info("Imagen actualizada con id: {}", updatedImagen.getId());
        return updatedImagen;
    }

    @Override
    public void deleteImagen(Long id) {
        Imagen existingImagen = imagenRepository.findById(id)  // Cambiado de User a Usuario
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la imagen a eliminar con id: " + id));
        imagenRepository.delete(existingImagen);
        LOGGER.info("Imagen eliminada con id: {}", id);
    }
}
