package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.REQUEST.FavoritoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.FavoritoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Service.Impl.FavoritoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;
    private final ModelMapper modelMapper;
    @Autowired
    public FavoritoController(FavoritoService favoritoService, ModelMapper modelMapper) {
        this.favoritoService = favoritoService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/marcar")
    public ResponseEntity<FavoritoResponse> marcarFavorito(@RequestBody FavoritoRequest request) {
        try {
            Alojamiento alojamiento = favoritoService.marcarFavorito(request.getUsuario_id(), request.getAlojamiento_id());

            // Convertimos Alojamiento a AlojamientoResponse
            AlojamientoResponse alojamientoResponse = new AlojamientoResponse(
                    alojamiento.getId(),
                    alojamiento.getTitulo(),
                    alojamiento.getDescripcion(),
                    alojamiento.getCapacidad(),
                    alojamiento.getPrecioPorNoche(),
                    alojamiento.getUbicacion(),
                    alojamiento.getDireccion(),
                    alojamiento.getDisponible(),
                    alojamiento.getPropietario().getId(),
                    alojamiento.getImagenes(),
                    alojamiento.getCategorias(),
                    alojamiento.getCaracteristicas()
            );

            FavoritoResponse response = new FavoritoResponse(request.getUsuario_id(), alojamientoResponse);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<List<FavoritoResponse>> obtenerFavoritos(@PathVariable Long usuarioId) {
        try {

            Usuario usuario = favoritoService.obtenerUsuarioConFavoritos(usuarioId);


            List<FavoritoResponse> response = usuario.getAlojamientosFavoritos().stream()
                    .map(alojamiento -> new FavoritoResponse(
                            usuario.getId(),
                            modelMapper.map(alojamiento, AlojamientoResponse.class)
                    ))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    }
