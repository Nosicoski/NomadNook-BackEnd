package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImagenRepository extends JpaRepository<Imagen, Long> {

    List<Imagen> findAllByAlojamientoId(Long id);
}
