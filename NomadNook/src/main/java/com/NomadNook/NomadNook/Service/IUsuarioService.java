package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.Model.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario createUser(Usuario usuario);
    Usuario getUserById(Long id);
    List<Usuario> listAllUsers();
    Usuario updateUser(Long id, Usuario usuario);
    void deleteUser(Long id);
}
