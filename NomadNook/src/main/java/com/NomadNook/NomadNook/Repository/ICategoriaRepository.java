package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNombre(String nombre);
    @Query(value = "SELECT c.* FROM categoria c " +
            "INNER JOIN alojamiento_categoria ac ON c.id = ac.categoria_id " +
            "WHERE ac.alojamiento_id = :alojamientoId", nativeQuery = true)
    List<Categoria> findCategoriaByAlojamientoId(@Param("alojamientoId") Long alojamientoId);
}