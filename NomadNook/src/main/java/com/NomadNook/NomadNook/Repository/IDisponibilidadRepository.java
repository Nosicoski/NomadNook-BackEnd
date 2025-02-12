package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Disponibilidad;
import com.NomadNook.NomadNook.Model.Reseña;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {
}
