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

        /* Admin tiene permiso a todo
           Cliente tiene permiso a:
           - Register y login
           - Ver todos los alojamientos e imagenes
           - Ver, modificar y eliminar su perfil de usuario
           - Ver todas las resenas, y crear, modificar y eliminar sus propias resenas
           - Ver, crear, modificar y eliminar sus reserva
           - Ver y crear sus propios pagos
           Sin autenticacion puede:
           - Register y login
           - Ver todos los alojamientos e imagenes
           - Ver todas las resenas
         */


        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas sin autenticación
                        .requestMatchers("/api/auth/**").permitAll()  // Permite acceso a la autenticación
                        .requestMatchers("/api/public/**").permitAll() // Rutas públicas
                        .requestMatchers(HttpMethod.GET, "api/alojamientos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/imagenes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/resenas/**").permitAll()
                        //.anyRequest().authenticated() // Todas las demás requieren autenticación

                        // Reseñas: cliente puede crear, modificar y eliminar sus propias reseñas
                        .requestMatchers(HttpMethod.POST, "api/resenas/**").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/resenas/**").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/resenas/**").hasAnyRole("CLIENT", "ADMIN")

                        .requestMatchers("api/usuarios/register").permitAll() // Permitir acceso al registro
                        .requestMatchers(HttpMethod.POST, "api/usuarios/register").permitAll()

                        // Reservas: cliente puede ver, crear, modificar y eliminar sus reservas
                        .requestMatchers("api/reservas/**").hasAnyRole("CLIENT", "ADMIN")

                        // Pagos: cliente puede ver y crear sus pagos
                        .requestMatchers(HttpMethod.GET, "api/pagos/**").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "api/pagos/**").hasAnyRole("CLIENT", "ADMIN")

                        // Admin tiene acceso a todo el resto
                        .requestMatchers("api/admin/**").hasRole("ADMIN")

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
        // Corregido para permitir todos los métodos HTTP necesarios
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}