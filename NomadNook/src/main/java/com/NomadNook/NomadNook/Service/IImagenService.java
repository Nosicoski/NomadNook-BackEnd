package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Imagen;

import java.util.List;

public interface IImagenService {
    Imagen createImagen(Imagen usuario);
    Imagen getImagenById(Long id);
    List<Imagen> listAllImagen();
    Imagen updateImagen(Long id, Imagen usuario);
    void deleteImagen(Long id);
}
