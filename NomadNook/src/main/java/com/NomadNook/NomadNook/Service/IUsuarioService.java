package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.REQUEST.UsuarioRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;

import java.util.List;

public interface IUsuarioService {

    UsuarioResponse createUser(UsuarioRequest usuarioRequest);
    UsuarioResponse getUserById(Long id);
    List<UsuarioResponse> listAllUsers();
    UsuarioResponse updateUser(Long id, UsuarioRequest usuarioRequest);
    void deleteUser(Long id) ;
    UsuarioResponse asignarRolAdmin(Long id);
}
