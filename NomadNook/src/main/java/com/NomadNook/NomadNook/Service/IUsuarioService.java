package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.REQUEST.UsuarioRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Model.Usuario;

import java.util.List;

public interface IUsuarioService {

    void createUser(List<Usuario> usuarios);
    UsuarioResponse getUserById(Long id);
    List<UsuarioResponse> listAllUsers();
    UsuarioResponse updateUser(Long id, UsuarioRequest usuarioRequest);
    void deleteUser(Long id) ;
    UsuarioResponse asignarRolAdmin(Long id);
}
