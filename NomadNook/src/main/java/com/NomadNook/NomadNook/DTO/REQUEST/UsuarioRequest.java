package com.NomadNook.NomadNook.DTO.REQUEST;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Data

public class UsuarioRequest {


    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Usuario.Rol rol;
    private String telefono;
    private LocalDateTime fechaRegistro;
    public enum Rol {
        ADMIN, CLIENTE
    }
    private List<Alojamiento> alojamientos;

    private Set<String> permisos = new HashSet<>();
}
