package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAlojamientoRepository extends JpaRepository<Alojamiento, Long> {
    List<Alojamiento> findByDisponibleTrue();
}
