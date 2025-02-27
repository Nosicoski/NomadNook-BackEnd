package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ICaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    boolean existsByNombre(String nombre);
    @Query(value = "SELECT c.* FROM caracteristicas c " +
            "INNER JOIN alojamiento_caracteristicas ac ON c.id = ac.caracteristica_id " +
            "WHERE ac.alojamiento_id = :alojamientoId", nativeQuery = true)
    List<Caracteristica> findCaracteristicasByAlojamientoId(@Param("alojamientoId") Long alojamientoId);
}
