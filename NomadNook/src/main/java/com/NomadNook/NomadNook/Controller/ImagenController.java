package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Service.IImagenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/imagenes")
public class ImagenController {

    private final IImagenService imagenService;

    public ImagenController(IImagenService imagenService) {
        this.imagenService = imagenService;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Imagen>> getAll() {
        return ResponseEntity.ok(imagenService.listAllImagen());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Imagen> getById(@PathVariable Long id) {
        return ResponseEntity.ok(imagenService.getImagenById(id));
    }

    @PostMapping("/guardar")
    public ResponseEntity<Imagen> create(@RequestBody Imagen imagen) {
        return ResponseEntity.ok(imagenService.createImagen(imagen));
    }

    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<Imagen> update(@PathVariable Long id, @RequestBody Imagen imagen) {
        return ResponseEntity.ok(imagenService.updateImagen(id, imagen));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imagenService.deleteImagen(id);
        return ResponseEntity.noContent().build();
    }
}
