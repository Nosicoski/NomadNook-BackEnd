package com.NomadNook.NomadNook.Security.Auth;

import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;

import com.NomadNook.NomadNook.Security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            throw new OAuth2AuthenticationException(ex.getMessage());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // Extract email based on provider (Google in this case)
        String email = oAuth2User.getAttribute("email");

        // Check if user exists
        Optional<Usuario> userOptional = usuarioRepository.findByEmail(email);
        Usuario usuario;

        if (userOptional.isPresent()) {
            // Update existing user with OAuth2 info if needed
            usuario = userOptional.get();
        } else {
            // Create new user
            usuario = registerNewUser(email, oAuth2User);
        }

        // Return UserDetails with OAuth2 attributes
        return new UserPrincipal(
                usuario,
                oAuth2User.getAttributes()
        );
    }

    private Usuario registerNewUser(String email, OAuth2User oAuth2User) {
        Usuario usuario = new Usuario();

        // Set user details from OAuth2 provider
        usuario.setEmail(email);
        usuario.setNombre((String) oAuth2User.getAttribute("given_name"));
        usuario.setApellido((String) oAuth2User.getAttribute("family_name"));
        usuario.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // Random password
        usuario.setRol(Usuario.Rol.CLIENTE); // Default role
        usuario.setFechaRegistro(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }
}