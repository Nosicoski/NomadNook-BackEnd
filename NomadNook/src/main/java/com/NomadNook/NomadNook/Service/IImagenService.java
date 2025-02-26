package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.RESPONSE.ImagenResponse;
import com.NomadNook.NomadNook.Model.Imagen;

import java.util.List;

public interface IImagenService {
    ImagenResponse createImagen(Imagen usuario);
    Imagen getImagenById(Long id);
    List<ImagenResponse> listAllImagen();
    Imagen updateImagen(Long id, Imagen usuario);
    void deleteImagen(Long id);
}
