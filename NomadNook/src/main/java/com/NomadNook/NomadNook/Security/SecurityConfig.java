package com.NomadNook.NomadNook.Security;

import com.NomadNook.NomadNook.Security.Auth.JwtAuthenticationFilter;
import com.NomadNook.NomadNook.Security.Auth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
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
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas sin autenticación
                        .requestMatchers("/api/auth/**","/oauth2/**", "/login/**", "/oauth2/authorization/**").permitAll()  // Permite acceso a la autenticación
                        .requestMatchers("/api/public/**").permitAll() // Rutas públicas
                        .requestMatchers(HttpMethod.GET, "/api/alojamientos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/imagenes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/resenas/**").permitAll()
                        //.anyRequest().authenticated() // Todas las demás requieren autenticación

                        // Rutas de Características
                        .requestMatchers(HttpMethod.GET, "/api/caracteristicas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/caracteristicas/**").hasAuthority("ADMIN")  // Solo ADMIN puede agregar
                        .requestMatchers(HttpMethod.PUT, "/api/caracteristicas/**").hasAuthority("ADMIN")  // Solo ADMIN puede modificar
                        .requestMatchers(HttpMethod.DELETE, "/api/caracteristicas/**").hasAuthority("ADMIN")  // Solo ADMIN puede eliminar

                        // Rutas de Categorías
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/categorias/**").hasAuthority("ADMIN")  // Solo ADMIN puede agregar
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasAuthority("ADMIN")  // Solo ADMIN puede modificar
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasAuthority("ADMIN")  // Solo ADMIN puede eliminar

                        // Reseñas: cliente puede crear, modificar y eliminar sus propias reseñas
                        .requestMatchers(HttpMethod.POST, "/api/resenas/**").hasAnyAuthority("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/resenas/**").hasAnyAuthority("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/resenas/**").hasAnyAuthority("CLIENTE", "ADMIN")

                        .requestMatchers("/api/usuarios/register").permitAll() // Permitir acceso al registro
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAuthority("ADMIN")

                        // Reservas: cliente puede ver, crear, modificar y eliminar sus reservas
                        .requestMatchers("/api/reservas/**").hasAnyAuthority("CLIENTE", "ADMIN")

                        // Pagos: cliente puede ver y crear sus pagos
                        .requestMatchers(HttpMethod.GET, "/api/pagos/**").hasAnyAuthority("CLIENTE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/pagos/**").hasAnyAuthority("CLIENTE", "ADMIN")

                        // FAVORITOS: cliente puede ver y marcar los alojamientos
                        .requestMatchers(HttpMethod.POST, "/api/favoritos/marcar").hasAnyRole("CLIENT", "ADMIN")

                        // Admin tiene acceso a todo el resto
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()

                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login")
//                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
//                        .successHandler(oAuth2AuthenticationSuccessHandler)
//                )
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
                "http://localhost:5173",
                "https://nomad-nook.vercel.app/",
                "https://main.d11kq4f727i0g3.amplifyapp.com/"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}