// Paquete donde se encuentra la clase
package com.NomadNook.NomadNook.Security;

// Importaciones necesarias
import com.NomadNook.NomadNook.Security.Auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

// Anotación que indica que esta clase es una configuración de Spring
@Configuration
// Habilita la configuración de seguridad web
@EnableWebSecurity
// Lombok genera un constructor con los campos finales (jwtAuthFilter y authenticationProvider)
@RequiredArgsConstructor
public class SecurityConfig {

    // Filtro de autenticación JWT
    private final JwtAuthenticationFilter jwtAuthFilter;
    // Proveedor de autenticación
    private final AuthenticationProvider authenticationProvider;

    // Bean que define la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura CORS (Cross-Origin Resource Sharing)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Deshabilita CSRF (Cross-Site Request Forgery) ya que se usa JWT
                .csrf(csrf -> csrf.disable())
                // Configura las reglas de autorización de las solicitudes HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite acceso sin autenticación a las rutas de autenticación
                        .requestMatchers("/api/auth/**").permitAll()
                        // Permite acceso sin autenticación a las rutas públicas
                        .requestMatchers("/api/public/**").permitAll()
                        // Todas las demás rutas requieren autenticación
                        .anyRequest().authenticated()
                )
                // Configura la gestión de sesiones para ser sin estado (stateless)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Establece el proveedor de autenticación
                .authenticationProvider(authenticationProvider)
                // Añade el filtro JWT antes del filtro de autenticación por usuario y contraseña
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // Construye y retorna la cadena de filtros de seguridad
        return http.build();
    }

    // Bean que configura CORS (Cross-Origin Resource Sharing)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Crea una configuración de CORS
        CorsConfiguration configuration = new CorsConfiguration();
        // Define los orígenes permitidos (en este caso, localhost en los puertos 3000 y 5173)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:5173"
        ));
        // Define los métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Define los encabezados permitidos (todos en este caso)
        configuration.setAllowedHeaders(List.of("*"));
        // Permite el envío de credenciales (cookies, encabezados de autenticación, etc.)
        configuration.setAllowCredentials(true);

        // Crea una fuente de configuración de CORS basada en URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Registra la configuración de CORS para todas las rutas
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}