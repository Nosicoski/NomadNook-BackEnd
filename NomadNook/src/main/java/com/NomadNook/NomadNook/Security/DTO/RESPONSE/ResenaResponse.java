package com.NomadNook.NomadNook.Security.DTO.RESPONSE;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data

public class ResenaResponse {
    private Long id;
    private Usuario cliente;
    private Alojamiento alojamiento;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fechaResena;
}
