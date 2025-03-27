package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.REQUEST.FavoritoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.FavoritoResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Service.Impl.FavoritoService;
import jakarta.persistence.EntityNotFoundException;
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
                    alojamiento.getCaracteristicas(),
                    alojamiento.getFechaReservaInicio(),
                    alojamiento.getFechaReservaFin()
            );

            FavoritoResponse response = new FavoritoResponse(request.getUsuario_id(), alojamientoResponse);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

   @DeleteMapping("/quitar")
    public ResponseEntity<String> quitarFavorito(@RequestBody FavoritoRequest request) {
        try {
            favoritoService.quitarFavorito(request.getUsuario_id(), request.getAlojamiento_id());
            return new ResponseEntity<>("Favorito eliminado correctamente", HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AlojamientoResponse>> obtenerFavoritos(@PathVariable Long usuarioId) {
        try {
            Usuario usuario = favoritoService.obtenerUsuarioConFavoritos(usuarioId); // Ahora sí recibe un Usuario

            List<AlojamientoResponse> response = usuario.getAlojamientosFavoritos().stream()
                    .map(alojamiento -> modelMapper.map(alojamiento, AlojamientoResponse.class))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    }

