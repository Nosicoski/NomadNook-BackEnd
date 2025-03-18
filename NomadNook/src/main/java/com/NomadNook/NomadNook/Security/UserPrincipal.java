package com.NomadNook.NomadNook.Security;

import com.NomadNook.NomadNook.Model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {

    private Usuario usuario;
    private Map<String, Object> attributes;

    public UserPrincipal(Usuario usuario, Map<String, Object> attributes) {
        this.usuario = usuario;
        this.attributes = attributes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(usuario.getRol().name()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return usuario.getEmail();
    }

    public UserDetails getUserDetails() {
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                getAuthorities()
        );
    }
}