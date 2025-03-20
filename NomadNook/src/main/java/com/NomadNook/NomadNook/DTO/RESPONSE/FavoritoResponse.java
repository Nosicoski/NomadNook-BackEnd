package com.NomadNook.NomadNook.DTO.RESPONSE;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoritoResponse {
    private Long usuario_id;
    private AlojamientoResponse alojamiento; // Cambiado de alojamiento_id a objeto completo

    // Constructor
    public FavoritoResponse(Long usuario_id, AlojamientoResponse alojamiento) {
        this.usuario_id = usuario_id;
        this.alojamiento = alojamiento;
    }

    // Getters y Setters
    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public AlojamientoResponse getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(AlojamientoResponse alojamiento) {
        this.alojamiento = alojamiento;
    }
}
