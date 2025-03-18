package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.REQUEST.FavoritoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.FavoritoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
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


    @PostMapping("/marcar")
    public ResponseEntity<FavoritoResponse> marcarFavorito(@RequestBody FavoritoRequest request) {
        try {
            favoritoService.marcarFavorito(request.getUsuario_id(), request.getAlojamiento_id());

            // Crear la respuesta con el usuario_id y alojamiento_id
            FavoritoResponse response = new FavoritoResponse(request.getUsuario_id(), request.getAlojamiento_id());

            // Devolver la respuesta con el código 201 Created
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }


    @DeleteMapping("/quitar")
    public ResponseEntity<Void> quitarFavorito(@RequestBody FavoritoRequest request) {
        try {
            favoritoService.quitarFavorito(request.getUsuario_id(), request.getAlojamiento_id());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<UsuarioResponse> obtenerFavoritos(@PathVariable Long usuarioId) {
        try {

            Usuario usuario = favoritoService.obtenerUsuarioConFavoritos(usuarioId);

            // Crear la respuesta usando UsuarioResponse
            UsuarioResponse response = UsuarioResponse.builder()
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .apellido(usuario.getApellido())
                    .email(usuario.getEmail())
                    .rol(usuario.getRol())


                    .alojamientosFavoritos(usuario.getAlojamientosFavoritos())
                    .build();

            // Devolver la respuesta con el código 200 OK
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }
    }
