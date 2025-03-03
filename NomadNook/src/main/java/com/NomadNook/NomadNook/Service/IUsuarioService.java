package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.REQUEST.UsuarioRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Model.Usuario;

import java.util.List;
import java.util.Set;

public interface IUsuarioService {

    void createUser(List<Usuario> usuarios);
    UsuarioResponse getUserById(Long id);
    List<UsuarioResponse> listAllUsers();
    UsuarioResponse updateUser(Long id, UsuarioRequest usuarioRequest);
    void deleteUser(Long id) ;
    UsuarioResponse asignarRolAdmin(Long id);
    UsuarioResponse desAsignarRolAdmin(Long id);
//    UsuarioResponse asignarPermisos(Long userId, Set<String> metodosPermitidos, Long adminId);
//    boolean tienePermiso(Long userId, String metodoHttp);
}
