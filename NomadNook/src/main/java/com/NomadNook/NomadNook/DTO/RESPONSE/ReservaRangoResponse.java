package com.NomadNook.NomadNook.DTO.RESPONSE;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRangoResponse {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
