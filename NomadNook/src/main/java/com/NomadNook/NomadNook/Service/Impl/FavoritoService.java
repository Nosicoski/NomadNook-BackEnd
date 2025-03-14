package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Favorito;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.FavoritoRepository;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritoService {
    private final FavoritoRepository favoritoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IAlojamientoRepository alojamientoRepository;

    public void marcarFavorito(Long usuarioId, Long alojamientoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new EntityNotFoundException("Alojamiento no encontrado"));

        favoritoRepository.findByUsuarioAndAlojamiento(usuario, alojamiento)
                .ifPresent(f -> { throw new IllegalStateException("Este alojamiento ya está en favoritos."); });

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setAlojamiento(alojamiento);
        favoritoRepository.save(favorito);
    }

    public void quitarFavorito(Long usuarioId, Long alojamientoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new EntityNotFoundException("Alojamiento no encontrado"));

        favoritoRepository.findByUsuarioAndAlojamiento(usuario, alojamiento)
                .ifPresentOrElse(
                        favoritoRepository::delete,
                        () -> { throw new IllegalStateException("El alojamiento no está en favoritos."); }
                );
    }

    public List<Alojamiento> obtenerFavoritos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return favoritoRepository.findByUsuario(usuario).stream()
                .map(Favorito::getAlojamiento)
                .toList();
    }
}
