package com.NomadNook.NomadNook.DTO.REQUEST;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Categoria;
import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Model.Usuario;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@Data

public class AlojamientoRequest {

    private String titulo;
    private String descripcion;
    private Integer capacidad;
    private BigDecimal precioPorNoche;
    private String ubicacion;
    private String direccion;
    private Boolean disponible;
    private Usuario propietario;
    private List<Imagen> imagenes;
    private List<Categoria> categorias;


    public AlojamientoRequest(String titulo, String descripcion, Integer capacidad, BigDecimal precioPorNoche, String ubicacion, String direccion, Boolean disponible, Usuario propietario, List<Imagen> imagenes, List<Categoria> categorias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.precioPorNoche = precioPorNoche;
        this.ubicacion = ubicacion;
        this.direccion = direccion;
        this.disponible = disponible;
        this.propietario = propietario;
        this.imagenes = imagenes;
        this.categorias = categorias;
    }
}
