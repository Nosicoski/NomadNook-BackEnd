package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IAlojamientoRepository extends JpaRepository<Alojamiento, Long> {

    // Encuentra todos los alojamientos disponibles
    List<Alojamiento> findByDisponibleTrue();

    // Encuentra todos los alojamientos de un propietario específico
    List<Alojamiento> findAllByPropietarioId(Long id);

    // Verifica si existe un alojamiento con el mismo nombre
    boolean existsByTitulo(String titulo);

    // Busca todos los alojamientos que tengan la caracteristica
    List<Alojamiento> findByCaracteristicas(Caracteristica caracteristica);

    // Busca todos los alojamientos que tengan la categoria
    List<Alojamiento> findByCategorias(Categoria categoria);

    @Query("SELECT DISTINCT a FROM Alojamiento a " +
            "WHERE a.disponible = true " +
            "AND a.id NOT IN (" +
            "    SELECT r.alojamiento.id FROM Reserva r " +
            "    WHERE (" +
            "        (r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaFin) " +  // Operadores estrictos
            "        OR (r.fechaInicio <= :fechaInicio AND r.fechaFin >= :fechaInicio)) " +
            "    AND r.estado IN ('PENDIENTE', 'CONFIRMADA')" +
            ")")
    List<Alojamiento> findAvailableAlojamientosInDateRange(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}
