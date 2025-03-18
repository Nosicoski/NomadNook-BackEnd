package com.NomadNook.NomadNook.Security.Auth;

import com.NomadNook.NomadNook.DTO.LOGIN.LoginResponse;
import com.NomadNook.NomadNook.Security.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtService.generateToken(userPrincipal.getUserDetails());

        // Create response object
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .email(userPrincipal.getUsername())
                .rol(userPrincipal.getUsuario().getRol())
                .nombre(userPrincipal.getUsuario().getNombre())
                .apellido(userPrincipal.getUsuario().getApellido())
                .mensaje("Login exitoso con Google")
                .build();

        // Set response
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }
}