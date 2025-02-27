package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAlojamientoRepository extends JpaRepository<Alojamiento, Long> {

    List<Alojamiento> findByTituloContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String titulo, String descripcion);
    // Encuentra todos los alojamientos disponibles
    List<Alojamiento> findByDisponibleTrue();

    // Encuentra todos los alojamientos de un propietario específico
    List<Alojamiento> findAllByPropietarioId(Long id);

    // Verifica si existe un alojamiento con el mismo nombre
    boolean existsByTitulo(String titulo);
}
