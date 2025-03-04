package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByNombre(String nombre);
}