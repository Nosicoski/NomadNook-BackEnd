package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Service.IAlojamientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/alojamientos")

public class AlojamientoController { private final IAlojamientoService alojamientoService;

    public AlojamientoController(IAlojamientoService alojamientoService) {
        this.alojamientoService = alojamientoService;
    }

    @GetMapping
    public ResponseEntity<List<Alojamiento>> getAll() {
        return ResponseEntity.ok(alojamientoService.listAllAlojamientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alojamiento> getById(@PathVariable Long id) {
        return ResponseEntity.ok(alojamientoService.getAlojamientoById(id));
    }

    @PostMapping
    public ResponseEntity<Alojamiento> create(@RequestBody Alojamiento alojamiento) {
        return ResponseEntity.ok(alojamientoService.createAlojamiento(alojamiento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alojamiento> update(@PathVariable Long id, @RequestBody Alojamiento alojamiento) {
        return ResponseEntity.ok(alojamientoService.updateAlojamiento(id, alojamiento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alojamientoService.deleteAlojamiento(id);
        return ResponseEntity.noContent().build();
    }
}
