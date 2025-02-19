package com.NomadNook.NomadNook.Security;

import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import com.NomadNook.NomadNook.Security.Dto.LoginRequest;
import com.NomadNook.NomadNook.Security.Dto.LoginResponse;
import com.NomadNook.NomadNook.Security.Auth.JwtService;
import com.NomadNook.NomadNook.Security.Dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public JwtService getJwtService() {
        return jwtService;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public IUsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("El email ya está registrado");
        }

        try {
            // Crear nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
            usuario.setTelefono(request.getTelefono());
            usuario.setRol(request.getRol() != null ? request.getRol() : Usuario.Rol.CLIENTE);
            usuario.setFechaRegistro(LocalDateTime.now());

            // Guardar usuario
            usuarioRepository.save(usuario);

            // Generar token
            String token = jwtService.generateToken(
                    new User(
                            usuario.getEmail(),
                            usuario.getPassword(),
                            Collections.singleton(
                                    new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
                    )
            );

            // Retornar respuesta utilizando el builder
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .email(usuario.getEmail())
                    .rol(usuario.getRol())
                    .mensaje("Login exitoso")
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            String token = jwtService.generateToken(
                    new User(
                            usuario.getEmail(),
                            usuario.getPassword(),
                            Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
                    )
            );

            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .email(usuario.getEmail())
                    .rol(usuario.getRol())
                    .mensaje("Login exitoso")
                    .build();

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            LoginResponse response = new LoginResponse();
            response.setMensaje("Credenciales inválidas");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);

        } catch (Exception e) {
            LoginResponse response = new LoginResponse();
            response.setMensaje("Error en el login: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }
}
