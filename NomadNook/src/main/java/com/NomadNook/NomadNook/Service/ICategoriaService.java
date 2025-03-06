package com.NomadNook.NomadNook.Service;


import com.NomadNook.NomadNook.DTO.REQUEST.CategoriaRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.CategoriaResponse;

import java.util.List;

public interface ICategoriaService {
    CategoriaResponse createCategoria(CategoriaRequest request);
    CategoriaResponse getCategoriaById(Long id);
    List<CategoriaResponse> listAllCategoria();
    List<CategoriaResponse> listAllCategoriaByAlojamiento(Long id);
    CategoriaResponse updateCategoria(Long id, CategoriaRequest request);
    void deleteCategoria(Long id);
}
