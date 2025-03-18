package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Disponibilidad;
import com.NomadNook.NomadNook.Service.IDisponibilidadService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disponibilidades")
public class  DisponibilidadController {  private final IDisponibilidadService disponibilidadService;

    public DisponibilidadController(IDisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }


    // CREA una Disponibilidad
    @PostMapping("/guardar")
    public ResponseEntity<Disponibilidad> create(@RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(disponibilidadService.createDisponibilidad(disponibilidad));
    }

    // TRAE todas las Disponibilidades

    @GetMapping ("/listarTodos")
    public ResponseEntity<List<Disponibilidad>> getAll() {
        return ResponseEntity.ok(disponibilidadService.listAllDisponibilidades());
    }

    // TRAE Disponibilidades por ID

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Disponibilidad> getById(@PathVariable Long id) {
        return ResponseEntity.ok(disponibilidadService.getDisponibilidadById(id));
    }


    // ACTUALIZA Disponibilidades por ID

    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<Disponibilidad> update(@PathVariable Long id, @RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(disponibilidadService.updateDisponibilidad(id, disponibilidad));
    }

    // ELIMINA Disponibilidades por ID

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disponibilidadService.deleteDisponibilidad(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{alojamientoId}")
    public ResponseEntity<Map<String, Object>> obtenerDisponibilidad(
            @PathVariable Long alojamientoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<LocalDate> diasNoDisponibles = disponibilidadService.obtenerDiasNoDisponibles(alojamientoId, fechaInicio, fechaFin);

        Map<String, Object> response = new HashMap<>();
        response.put("alojamientoId", alojamientoId);
        response.put("diasNoDisponibles", diasNoDisponibles);

        return ResponseEntity.ok(response);
    }

}
