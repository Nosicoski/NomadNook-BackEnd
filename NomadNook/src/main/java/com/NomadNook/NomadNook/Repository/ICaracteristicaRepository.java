package com.NomadNook.NomadNook.Repository;

import com.NomadNook.NomadNook.Model.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
}
