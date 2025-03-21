package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Model.Categoria;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Service.IAlojamientoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/alojamientos")

public class AlojamientoController {

    private final IAlojamientoService alojamientoService;

    public AlojamientoController(IAlojamientoService alojamientoService) {
        this.alojamientoService = alojamientoService;
    }
// TRAE todos los alojamientos

    @GetMapping ("/listarTodos")
    public ResponseEntity<List<AlojamientoResponse>> getAll() {
        return ResponseEntity.ok(alojamientoService.listAllAlojamientos());
    }
    // TRAE un alojamiento por ID

    @GetMapping("/buscar/{id}")
    public ResponseEntity<AlojamientoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(alojamientoService.getAlojamientoById(id));
    }

    // CREA un alojamiento
    //Recibe como parametros los atributos de Alojamiento Request, es lo que va entre parentesis
    @PostMapping("/guardar")
    public ResponseEntity<AlojamientoResponse> create(@Valid @RequestBody AlojamientoRequest alojamiento) {
        return ResponseEntity.ok(alojamientoService.createAlojamiento(alojamiento));
    }
    // ACTUALIZA un alojamiento (hay que pasarle un ID)

    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<AlojamientoResponse> update(@PathVariable Long id, @Valid @RequestBody AlojamientoRequest alojamiento) {
        return ResponseEntity.ok(alojamientoService.updateAlojamiento(id, alojamiento));
    }
    // ELIMINA un alojamiento (hay que pasarle un ID)

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alojamientoService.deleteAlojamiento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/usuario/{id}")
    public ResponseEntity<List<AlojamientoResponse>> getByPropietarioId(@PathVariable Long id) {
        return ResponseEntity.ok(alojamientoService.listAllAlojamientosByPropietario(id));
    }

    @PostMapping("/{alojamiento_id}/caracteristicas/{caracteristica_id}")
    public void addCaracteristicaToAlojamiento(
            @PathVariable Long alojamiento_id,
            @PathVariable Long caracteristica_id
    ) {
        alojamientoService.agregarCaracteristicaAlojamiento(alojamiento_id, caracteristica_id);
    }

    @PostMapping("/{alojamiento_id}/caracteristicas")
    public void addListaCaracteristicasToAlojamiento(
            @PathVariable Long alojamiento_id,
            @RequestBody Set<Caracteristica> caracteristicas
    ) {
        alojamientoService.agregarCaracteristicasAlojamiento(alojamiento_id, caracteristicas);
    }

    @PostMapping("/{alojamiento_id}/categorias/{categoria_id}")
    public void addCategoriaToAlojamiento(
            @PathVariable Long alojamiento_id,
            @PathVariable Long categoria_id
    ) {
        alojamientoService.agregarCategoriaAlojamiento(alojamiento_id, categoria_id);
    }

    @PostMapping("/{alojamiento_id}/categorias")
    public void addListaCategoriasToAlojamiento(
            @PathVariable Long alojamiento_id,
            @RequestBody Set<Categoria> categorias
    ) {
        alojamientoService.agregarCategoriasAlojamiento(alojamiento_id, categorias);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<AlojamientoResponse>> getAvailableAlojamientos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        try {
            List<AlojamientoResponse> alojamientosDisponibles =
                    alojamientoService.buscarAlojamientosDisponibles(fechaInicio, fechaFin);
            return ResponseEntity.ok(alojamientosDisponibles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
