package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // Método para buscar un usuario por email.

    boolean existsByEmail(String email); // Método para verificar si el email ya está registrado.
}
