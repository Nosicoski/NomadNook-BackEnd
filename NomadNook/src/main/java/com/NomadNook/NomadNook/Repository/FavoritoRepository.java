package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Favorito;
import com.NomadNook.NomadNook.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    Optional<Favorito> findByUsuarioAndAlojamiento(Usuario usuario, Alojamiento alojamiento);
    List<Favorito> findByUsuario(Usuario usuario);
    void deleteByUsuarioAndAlojamiento(Usuario usuario, Alojamiento alojamiento);
}
