package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
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

    public Alojamiento marcarFavorito(Long usuarioId, Long alojamientoId) {
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
        return alojamiento;
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

    public Usuario obtenerUsuarioConFavoritos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        // Forzar la carga de la relación (si es Lazy)
        usuario.getAlojamientosFavoritos().size(); // Truco para inicializar la colección

        return usuario;
    }
}
