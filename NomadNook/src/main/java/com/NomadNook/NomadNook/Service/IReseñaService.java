package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Reseña;

import java.util.List;

public interface IReseñaService {
    Reseña createResena(Reseña resena);
    Reseña getResenaById(Long id);
    List<Reseña> listAllResenas();
    Reseña updateResena(Long id, Reseña resena);
    void deleteResena(Long id);
}
