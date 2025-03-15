package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.REQUEST.FavoritoRequest;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Service.Impl.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;

    @Autowired
    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    // Marcar un alojamiento como favorito
    @PostMapping("/marcar")
    public ResponseEntity<Void> marcarFavorito(@RequestBody FavoritoRequest request) {
        try {
            favoritoService.marcarFavorito(request.getUsuario_id(), request.getAlojamiento_id());
            return new ResponseEntity<>(HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    // Quitar un alojamiento de favoritos
    @DeleteMapping("/quitar")
    public ResponseEntity<Void> quitarFavorito(@RequestBody FavoritoRequest request) {
        try {
            favoritoService.quitarFavorito(request.getUsuario_id(), request.getAlojamiento_id());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    // Obtener todos los favoritos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Alojamiento>> obtenerFavoritos(@PathVariable Long usuarioId) {
        try {
            List<Alojamiento> favoritos = favoritoService.obtenerFavoritos(usuarioId);
            return new ResponseEntity<>(favoritos, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }
}