package com.NomadNook.NomadNook.Security.Dto;

import com.NomadNook.NomadNook.Model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private Usuario.Rol rol;
    private String mensaje;
}