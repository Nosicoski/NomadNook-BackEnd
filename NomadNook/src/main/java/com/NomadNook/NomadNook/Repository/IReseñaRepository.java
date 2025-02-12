package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Model.Reseña;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReseñaRepository extends JpaRepository<Reseña, Long> {
}
