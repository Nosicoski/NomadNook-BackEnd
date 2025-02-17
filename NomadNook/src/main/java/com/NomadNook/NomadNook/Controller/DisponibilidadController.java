package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Disponibilidad;
import com.NomadNook.NomadNook.Service.IDisponibilidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {  private final IDisponibilidadService disponibilidadService;

    public DisponibilidadController(IDisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    @GetMapping
    public ResponseEntity<List<Disponibilidad>> getAll() {
        return ResponseEntity.ok(disponibilidadService.listAllDisponibilidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disponibilidad> getById(@PathVariable Long id) {
        return ResponseEntity.ok(disponibilidadService.getDisponibilidadById(id));
    }

    @PostMapping
    public ResponseEntity<Disponibilidad> create(@RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(disponibilidadService.createDisponibilidad(disponibilidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disponibilidad> update(@PathVariable Long id, @RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(disponibilidadService.updateDisponibilidad(id, disponibilidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disponibilidadService.deleteDisponibilidad(id);
        return ResponseEntity.noContent().build();
    }
}
