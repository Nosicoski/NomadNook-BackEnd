package com.NomadNook.NomadNook.Security.Auth;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@Component
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    //public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
      //  this.jwtService = jwtService;
        //this.userDetailsService = userDetailsService;
    //}

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Verifica si la autenticación ya está establecida en el SecurityContext
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            // Si ya está autenticado, no es necesario procesar el token nuevamente.
            filterChain.doFilter(request, response);
            return;
            
        }

        // Si no hay autenticación, continua con la verificación del token JWT
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // Si no hay token, simplemente pasa la solicitud al siguiente filtro
        }

        jwt = authHeader.substring(7); // Extrae el token (sin "Bearer ")
        userEmail = jwtService.extractUsername(jwt); // Extrae el email desde el token

        if (userEmail != null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Si el token es válido, asignamos el usuario en el contexto de seguridad
                    String role = jwtService.extractClaim(jwt, claims -> claims.get("role", String.class));
                    Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);  // Establece la autenticación

                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired or invalid");
                    return;  // Si el token es inválido, enviamos un error y no continuamos
                }
            } catch (UsernameNotFoundException e) {
                logger.error("User not found: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;  // En caso de error al cargar el usuario, enviamos un error
            } catch (JwtException e) {
                logger.error("JWT token is invalid: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;  // En caso de error con el token, enviamos un error
            } catch (Exception e) {
                logger.error("JWT Authentication failed: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred");
                return;  // En caso de otros errores, enviamos un error interno
            }
        }

        // Continúa con el siguiente filtro si no hubo problemas
        filterChain.doFilter(request, response);
    }
}