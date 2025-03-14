package com.NomadNook.NomadNook.Service.Impl;


import com.NomadNook.NomadNook.DTO.REQUEST.CategoriaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.CaracteristicaResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.CategoriaResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Model.Categoria;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.ICategoriaRepository;
import com.NomadNook.NomadNook.Service.ICategoriaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class CategoriaService implements ICategoriaService {

    private final ICategoriaRepository categoriaRepository;
    private final IAlojamientoRepository alojamientoRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private final ModelMapper modelMapper;

    public CategoriaService(ICategoriaRepository categoriaRepository, IAlojamientoRepository alojamientoRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.alojamientoRepository = alojamientoRepository;
        this.modelMapper = modelMapper;
    }


    private CategoriaResponse createCategoriaResponse(Categoria categoria) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(categoria.getId());
        response.setIcono(categoria.getIcono());
        response.setDescripcion(categoria.getDescripcion());
        response.setNombre(categoria.getNombre());
        return response;
    }


    @Override
    public CategoriaResponse createCategoria(CategoriaRequest request) {
        Categoria categoria = modelMapper.map(request, Categoria.class);

        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return createCategoriaResponse(categoriaGuardada);
    }

    @Override
    public CategoriaResponse getCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la característica con id: " + id));

        return createCategoriaResponse(categoria);
    }

    @Override
    public List<CategoriaResponse> listAllCategoria() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaResponse> responses = new ArrayList<>();
        for(Categoria categoria: categorias) {
            CategoriaResponse response = createCategoriaResponse(categoria);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<CategoriaResponse> listAllCategoriaByAlojamiento(Long id) {
        List<Categoria> categorias = categoriaRepository.findCategoriaByAlojamientoId(id);
        List<CategoriaResponse> responses = new ArrayList<>();
        for(Categoria categoria: categorias) {
            CategoriaResponse response = createCategoriaResponse(categoria);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public CategoriaResponse updateCategoria(Long id, CategoriaRequest request) {
        Categoria existingCategoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la característica con id: " + id));

        if(!existingCategoria.getNombre().equals(request.getNombre())
                && categoriaRepository.existsByNombre(request.getNombre())) {
            throw new IllegalArgumentException("Ya existe una caracteristica con el mismo nombre");
        }

        modelMapper.map(request, existingCategoria);

        Categoria updatedCategoria = categoriaRepository.save(existingCategoria);
        LOGGER.info("Caracteristica actualizada con id: {}", updatedCategoria.getId());
        return createCategoriaResponse(updatedCategoria);
    }

    @Override
    public void deleteCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria not found"));

        List<Alojamiento> associatedAlojamientos = alojamientoRepository
                .findByCategorias(categoria);

        for (Alojamiento alojamiento : associatedAlojamientos) {
            alojamiento.getCategorias().remove(categoria);
            alojamientoRepository.save(alojamiento);
        }

        categoriaRepository.delete(categoria);
        LOGGER.info("Categoria eliminada con id: {}", id);
    }
}
