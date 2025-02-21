package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Resena;

import java.util.List;

public interface IResenaService {
    Resena createResena(Resena resena);
    Resena getResenaById(Long id);
    List<Resena> listAllResenas();
    Resena updateResena(Long id, Resena resena);
    void deleteResena(Long id);
}
