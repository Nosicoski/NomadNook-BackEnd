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
import org.springframework.security.core.userdetails.UserDetails;
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
            Usuario usuario = Usuario.builder()
                    .nombre(request.getNombre())
                    .apellido(request.getApellido())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .telefono(request.getTelefono())
                    .rol(request.getRol() != null ? request.getRol() : Usuario.Rol.CLIENTE)
                    .fechaRegistro(LocalDateTime.now())
                    .build();

            // Guardar usuario
            usuarioRepository.save(usuario);

            // Generar token
            String token = jwtService.generateToken(
                    User.builder()
                            .username(usuario.getEmail())
                            .password(usuario.getPassword())
                            .authorities(Collections.singleton(
                                    new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
                            ))
                            .build()
            );

            // Retornar respuesta
            return ResponseEntity.ok(LoginResponse.builder()
                    .token(token)
                    .email(usuario.getEmail())
                    .rol(usuario.getRol())
                    .mensaje("Usuario registrado exitosamente")
                    .build());

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
                    User.builder()
                            .username(usuario.getEmail())
                            .password(usuario.getPassword())
                            .authorities(Collections.singleton(
                                    new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
                            ))
                            .build()
            );

            return ResponseEntity.ok(LoginResponse.builder()
                    .token(token)
                    .email(usuario.getEmail())
                    .rol(usuario.getRol())
                    .mensaje("Login exitoso")
                    .build());

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .mensaje("Credenciales inválidas")
                            .build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(LoginResponse.builder()
                            .mensaje("Error en el login: " + e.getMessage())
                            .build());
        }
    }
}