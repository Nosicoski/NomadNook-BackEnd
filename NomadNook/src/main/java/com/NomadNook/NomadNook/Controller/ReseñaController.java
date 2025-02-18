package com.NomadNook.NomadNook.Controller;


import com.NomadNook.NomadNook.Model.Reseña;
import com.NomadNook.NomadNook.Service.IReseñaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ReseñaController { private final IReseñaService resenaService;

    public ReseñaController(IReseñaService resenaService) {
        this.resenaService = resenaService;
    }


    // CREA una Reseña
    @PostMapping("/guardar")
    public ResponseEntity<Reseña> create(@RequestBody Reseña resena) {
        return ResponseEntity.ok(resenaService.createResena(resena));
    }


    // TRAE todas las Reseñas
    @GetMapping
    public ResponseEntity<List<Reseña>> getAll() {
        return ResponseEntity.ok(resenaService.listAllResenas());
    }


    // TRAE una Reseña por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reseña> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.getResenaById(id));
    }


    // ACTUALIZA una Reseña por ID
    @PutMapping("/{id}")
    public ResponseEntity<Reseña> update(@PathVariable Long id, @RequestBody Reseña resena) {
        return ResponseEntity.ok(resenaService.updateResena(id, resena));
    }
    // ELIMINA una Reseña por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build();
    }
}
