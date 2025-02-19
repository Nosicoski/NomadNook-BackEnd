package com.NomadNook.NomadNook.Model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "imagenes")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "La URL de la imagen no puede estar vacía.")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*$", message = "La URL debe ser válida.")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alojamiento_id", nullable = false)
    @JsonBackReference
    private Alojamiento alojamiento;

}
