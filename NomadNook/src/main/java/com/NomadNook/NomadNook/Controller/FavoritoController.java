package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Service.Impl.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {
    private final FavoritoService favoritoService;

    @PostMapping("/marcar")
    public ResponseEntity<String> marcarFavorito(@RequestParam Long usuarioId, @RequestParam Long alojamientoId) {
        favoritoService.marcarFavorito(usuarioId, alojamientoId);
        return ResponseEntity.ok("Alojamiento agregado a favoritos.");
    }

    @DeleteMapping("/quitar")
    public ResponseEntity<String> quitarFavorito(@RequestParam Long usuarioId, @RequestParam Long alojamientoId) {
        favoritoService.quitarFavorito(usuarioId, alojamientoId);
        return ResponseEntity.ok("Alojamiento eliminado de favoritos.");
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Alojamiento>> obtenerFavoritos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(favoritoService.obtenerFavoritos(usuarioId));
    }
}