package com.NomadNook.NomadNook.Controller;


import com.NomadNook.NomadNook.DTO.REQUEST.UsuarioRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Service.Impl.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Para Cargas masivas de Usuario, Alojamiento e Imagenes
    @PostMapping("/guardar")
    public ResponseEntity<Void> guardarUser(@RequestBody List<Usuario> usuarios) {
        usuarioService.createUser(usuarios);
        return ResponseEntity.noContent().build();
    }
    // TRAE todos los Usuarios
    @GetMapping ("/listarTodos")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listAllUsers());
    }
    // TRAE un Usuario por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioResponse> buscarUser(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUserById(id));
    }

    // ACTUALIZA un Usuario por ID
    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUser(@PathVariable Long id, @RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.ok(usuarioService.updateUser(id, usuarioRequest));

    }
    // ELIMINA un Usuario por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/asignar-admin")
    public ResponseEntity<UsuarioResponse> asignarRolAdmin(@PathVariable Long id) {
        UsuarioResponse updatedUser = usuarioService.asignarRolAdmin(id);
        return ResponseEntity.ok(updatedUser);
    }



}

