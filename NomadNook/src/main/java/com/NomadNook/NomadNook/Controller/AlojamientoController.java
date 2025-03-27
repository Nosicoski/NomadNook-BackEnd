package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Model.Categoria;
import com.NomadNook.NomadNook.Service.IAlojamientoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/alojamientos")

public class AlojamientoController {

    private final IAlojamientoService alojamientoService;

    public AlojamientoController(IAlojamientoService alojamientoService) {
        this.alojamientoService = alojamientoService;
    }





    @GetMapping("/buscar/{id}")
    public ResponseEntity<AlojamientoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(alojamientoService.getAlojamientoById(id));
    }



    @PostMapping("/guardar")
    public ResponseEntity<AlojamientoResponse> create(@Valid @RequestBody AlojamientoRequest alojamiento) {
        return ResponseEntity.ok(alojamientoService.createAlojamiento(alojamiento));
    }


    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<AlojamientoResponse> update(@PathVariable Long id, @Valid @RequestBody AlojamientoRequest alojamiento) {
        return ResponseEntity.ok(alojamientoService.updateAlojamiento(id, alojamiento));
    }


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


    @GetMapping("/listarTodos")
    public ResponseEntity<List<AlojamientoResponse>> getAll(
            @RequestParam(name = "fechaInicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(name = "fechaFin", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<AlojamientoResponse> responses = alojamientoService.listAllAlojamientos();

        if(fechaInicio != null && fechaFin != null) {
            for (AlojamientoResponse resp : responses) {
                resp.setFechaReservaInicio(fechaInicio);
                resp.setFechaReservaFin(fechaFin);
                boolean disponible = alojamientoService.isAlojamientoDisponible(resp.getId(), fechaInicio, fechaFin);
                resp.setDisponible(disponible);
            }
        }

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{alojamientoId}/disponibilidad")
    public ResponseEntity<Map<String, Boolean>> checkDisponibilidad(
            @PathVariable Long alojamientoId,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        boolean disponible = alojamientoService.isAlojamientoDisponible(alojamientoId, fechaInicio, fechaFin);

        Map<String, Boolean> response = new HashMap<>();
        response.put("disponible", disponible);

        return ResponseEntity.ok(response);
    }


}
