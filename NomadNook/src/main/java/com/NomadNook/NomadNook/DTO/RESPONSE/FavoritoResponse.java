package com.NomadNook.NomadNook.DTO.RESPONSE;

public class FavoritoResponse {
    private Long usuario_id;
    private Long alojamiento_id;

    // Constructor
    public FavoritoResponse(Long usuario_id, Long alojamiento_id) {
        this.usuario_id = usuario_id;
        this.alojamiento_id = alojamiento_id;
    }

    // Getters y Setters
    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Long getAlojamiento_id() {
        return alojamiento_id;
    }

    public void setAlojamiento_id(Long alojamiento_id) {
        this.alojamiento_id = alojamiento_id;
    }
}