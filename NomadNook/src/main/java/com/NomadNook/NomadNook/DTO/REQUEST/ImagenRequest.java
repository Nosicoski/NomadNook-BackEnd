package com.NomadNook.NomadNook.DTO.REQUEST;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@Data

public class ImagenRequest {

    private Long id;
    private String url;
    private Alojamiento alojamiento;
}
