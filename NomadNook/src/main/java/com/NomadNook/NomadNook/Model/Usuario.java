package com.NomadNook.NomadNook.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Usuario")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío.")
    private String nombre;

    @NotEmpty(message = "El apellido no puede estar vacío.")
    private String apellido;

    @Email(message = "El correo electrónico debe tener un formato válido.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "El rol del usuario es obligatorio.")
    private Rol rol;

    @Pattern(regexp = "^[+]?[0-9]{1,3}?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{4,6}$", message = "El teléfono debe tener un formato válido.")
    private String telefono;

    @NotNull(message = "La fecha de registro no puede ser nula.")
    private LocalDateTime fechaRegistro;

    public enum Rol {
        ADMIN, CLIENTE
    }

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Alojamiento> alojamientos;
//    private Set<String> permisos = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "alojamiento_id")
    )
    private List<Alojamiento> alojamientosFavoritos;
}