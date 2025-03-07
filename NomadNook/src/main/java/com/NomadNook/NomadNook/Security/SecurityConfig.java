package com.NomadNook.NomadNook.Security;

import com.NomadNook.NomadNook.Security.Auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas sin autenticación
                        .requestMatchers("/api/auth/**").permitAll()  // Permite acceso a la autenticación
                        .requestMatchers("/api/public/**").permitAll() // Rutas públicas
                        .requestMatchers(HttpMethod.GET, "/api/alojamientos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/imagenes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/resenas/**").permitAll()
                        //.anyRequest().authenticated() // Todas las demás requieren autenticación

                        // Rutas de Características
                        .requestMatchers(HttpMethod.GET, "/api/caracteristicas/**").permitAll()  // CLIENTE y ADMIN pueden ver
                        .requestMatchers(HttpMethod.POST, "/api/caracteristicas/**").hasAuthority("ADMIN")  // Solo ADMIN puede agregar
                        .requestMatchers(HttpMethod.PUT, "/api/caracteristicas/**").hasAuthority("ADMIN")  // Solo ADMIN puede modificar
                        .requestMatchers(HttpMethod.DELETE, "/api/caracteristicas/**").hasAuthority("ADMIN")  // Solo ADMIN puede eliminar

                        // Rutas de Categorías
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()  // CLIENTE y ADMIN pueden ver
                        .requestMatchers(HttpMethod.POST, "/api/categorias/**").hasAuthority("ADMIN")  // Solo ADMIN puede agregar
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasAuthority("ADMIN")  // Solo ADMIN puede modificar
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasAuthority("ADMIN")  // Solo ADMIN puede eliminar

                        // Reseñas: cliente puede crear, modificar y eliminar sus propias reseñas
                        .requestMatchers(HttpMethod.POST, "/api/resenas/**").hasAnyAuthority("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/resenas/**").hasAnyAuthority("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/resenas/**").hasAnyAuthority("CLIENTE", "ADMIN")

                        .requestMatchers("/api/usuarios/register").permitAll() // Permitir acceso al registro
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/guardar").permitAll()

                        // Reservas: cliente puede ver, crear, modificar y eliminar sus reservas
                        .requestMatchers("/api/reservas/**").hasAnyAuthority("CLIENTE", "ADMIN")

                        // Pagos: cliente puede ver y crear sus pagos
                        .requestMatchers(HttpMethod.GET, "/api/pagos/**").hasAnyAuthority("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/pagos/**").hasAnyAuthority("CLIENTE", "ADMIN")

                        // Admin tiene acceso a todo el resto
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()

                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless porque estamos usando JWT
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:5173"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}