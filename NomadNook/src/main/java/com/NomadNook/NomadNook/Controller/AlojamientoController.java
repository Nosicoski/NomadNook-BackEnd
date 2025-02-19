package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Service.IAlojamientoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/alojamientos")

public class AlojamientoController {

    private final IAlojamientoService alojamientoService;

    public AlojamientoController(IAlojamientoService alojamientoService) {
        this.alojamientoService = alojamientoService;
    }
// TRAE todos los alojamientos

    @GetMapping ("/listarTodos")
    public ResponseEntity<List<Alojamiento>> getAll() {
        return ResponseEntity.ok(alojamientoService.listAllAlojamientos());
    }
    // TRAE un alojamiento por ID

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Alojamiento> getById(@PathVariable Long id) {
        return ResponseEntity.ok(alojamientoService.getAlojamientoById(id));
    }

    // CREA un alojamiento
    @PostMapping("/guardar")
    public ResponseEntity<Alojamiento> create(@Valid @RequestBody Alojamiento alojamiento) {
        return ResponseEntity.ok(alojamientoService.createAlojamiento(alojamiento));
    }
    // ACTUALIZA un alojamiento (hay que pasarle un ID)

    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<Alojamiento> update(@PathVariable Long id, @Valid @RequestBody Alojamiento alojamiento) {
        return ResponseEntity.ok(alojamientoService.updateAlojamiento(id, alojamiento));
    }
    // ELIMINA un alojamiento (hay que pasarle un ID)

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alojamientoService.deleteAlojamiento(id);
        return ResponseEntity.noContent().build();
    }
}
