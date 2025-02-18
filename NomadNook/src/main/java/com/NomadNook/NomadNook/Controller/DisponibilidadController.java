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


    // CREA una Disponibilidad
    @PostMapping("/guardar")
    public ResponseEntity<Disponibilidad> create(@RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(disponibilidadService.createDisponibilidad(disponibilidad));
    }

    // TRAE todas las Disponibilidades
    @GetMapping
    public ResponseEntity<List<Disponibilidad>> getAll() {
        return ResponseEntity.ok(disponibilidadService.listAllDisponibilidades());
    }

    // TRAE Disponibilidades por ID
    @GetMapping("/{id}")
    public ResponseEntity<Disponibilidad> getById(@PathVariable Long id) {
        return ResponseEntity.ok(disponibilidadService.getDisponibilidadById(id));
    }


    // ACTUALIZA Disponibilidades por ID
    @PutMapping("/{id}")
    public ResponseEntity<Disponibilidad> update(@PathVariable Long id, @RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(disponibilidadService.updateDisponibilidad(id, disponibilidad));
    }

    // ELIMINA Disponibilidades por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disponibilidadService.deleteDisponibilidad(id);
        return ResponseEntity.noContent().build();
    }

}
