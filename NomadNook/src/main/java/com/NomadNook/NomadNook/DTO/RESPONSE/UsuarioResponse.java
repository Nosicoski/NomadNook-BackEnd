package com.NomadNook.NomadNook.DTO.RESPONSE;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
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
    private String alojamientos;

    private List<Alojamiento> alojamientosFavoritos;


}
