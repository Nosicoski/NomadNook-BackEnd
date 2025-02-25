package com.NomadNook.NomadNook.DTO.REQUEST;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data

public class ResenaRequest {


    private Long id;
    private Usuario cliente;
    private Alojamiento alojamiento;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fechaResena;
}
