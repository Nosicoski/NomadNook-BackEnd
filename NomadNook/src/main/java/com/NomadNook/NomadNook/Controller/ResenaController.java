package com.NomadNook.NomadNook.Controller;


import com.NomadNook.NomadNook.Model.Resena;
import com.NomadNook.NomadNook.Service.IResenaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController { private final IResenaService resenaService;

    public ResenaController(IResenaService resenaService) {
        this.resenaService = resenaService;
    }


    // CREA una Reseña
    @PostMapping("/guardar")
    public ResponseEntity<Resena> create(@RequestBody Resena resena) {
        return ResponseEntity.ok(resenaService.createResena(resena));
    }


    // TRAE todas las Reseñas

    @GetMapping ("/listarTodos")
    public ResponseEntity<List<Resena>> getAll() {
        return ResponseEntity.ok(resenaService.listAllResenas());
    }


    // TRAE una Reseña por ID

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Resena> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.getResenaById(id));
    }


    // ACTUALIZA una Reseña por ID

    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<Resena> update(@PathVariable Long id, @RequestBody Resena resena) {
        return ResponseEntity.ok(resenaService.updateResena(id, resena));
    }
    // ELIMINA una Reseña por ID

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build();
    }
}
