package com.NomadNook.NomadNook.Controller;


import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Service.Impl.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



    // CREA un Usuario
    @PostMapping("/guardar")
    public ResponseEntity<Usuario> guardarUser(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.createUser(usuario));
    }
    // TRAE todos los Usuarios
    @GetMapping ("/listarTodos")
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listAllUsers());
    }
    // TRAE un Usuario por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Usuario> buscarUser(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUserById(id));
    }

    // ACTUALIZA un Usuario por ID
    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<Usuario> actualizarUser(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.updateUser(id, usuario));

    }
    // ELIMINA un Usuario por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}

