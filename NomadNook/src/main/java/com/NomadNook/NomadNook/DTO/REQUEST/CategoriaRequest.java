package com.NomadNook.NomadNook.DTO.REQUEST;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {

    private String nombre;
    private String descripcion;
    private String icono;
}
