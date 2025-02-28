package com.NomadNook.NomadNook.Controller;


import com.NomadNook.NomadNook.DTO.REQUEST.UsuarioRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Service.Impl.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            // Llamamos al servicio para registrar al usuario
            UsuarioResponse nuevoUsuario = usuarioService.registrarUsuario(usuarioRequest);

            // Si el registro fue exitoso, devolvemos el usuario creado con estado 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (IllegalArgumentException e) {
            // Si el email ya está registrado, devolvemos estado 400 (BAD_REQUEST)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Para cualquier otro error, devolvemos estado 500 (INTERNAL_SERVER_ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Solo el ADMIN realiza cargas masivas de Usuario, Alojamiento e Imagenes
    @PostMapping("/guardar")
    public ResponseEntity<Void> guardarUser(@RequestBody List<Usuario> usuarios) {
        usuarioService.createUser(usuarios);
        return ResponseEntity.noContent().build();
    }

    // Solo el ADMIN TRAE todos los Usuarios
    @GetMapping ("/listarTodos")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listAllUsers());
    }

    // Solo el ADMIN TRAE un Usuario por ID
    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioResponse> buscarUser(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUserById(id));
    }

    // Solo el ADMIN actualiza id
    @PutMapping ("/actualizar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioResponse> actualizarUser(@PathVariable Long id, @RequestBody UsuarioRequest usuarioRequest) {
        return ResponseEntity.ok(usuarioService.updateUser(id, usuarioRequest));

    }
    // Solo el ADMIN puede eliminar usuarios
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Solo el ADMIN puede asignar roles
    @PutMapping("/{id}/asignar-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioResponse> asignarRolAdmin(@PathVariable Long id) {
        UsuarioResponse updatedUser = usuarioService.asignarRolAdmin(id);
        return ResponseEntity.ok(updatedUser);
    }

    // Solo el ADMIN puede desasignar roles
    @PutMapping("/{id}/desasignar-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioResponse> desAsignarRolAdmin(@PathVariable Long id) {
        UsuarioResponse updatedUser = usuarioService.desAsignarRolAdmin(id);
        return ResponseEntity.ok(updatedUser);
    }

//    @PutMapping("/{id}/asignar-permisos")
//    public ResponseEntity<UsuarioResponse> asignarPermisos(
//            @PathVariable Long id,
//            @RequestBody Set<String> metodosPermitidos,
//            @RequestAttribute Long adminId) {
//        UsuarioResponse updatedUser = usuarioService.asignarPermisos(id, metodosPermitidos, adminId);
//        return ResponseEntity.ok(updatedUser);
//    }

}

