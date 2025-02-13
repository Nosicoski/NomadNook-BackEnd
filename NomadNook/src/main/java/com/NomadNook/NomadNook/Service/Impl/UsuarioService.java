package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import com.NomadNook.NomadNook.Service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class UsuarioService  implements IUsuarioService {



    private final IUsuarioRepository usuarioRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public Usuario createUser(Usuario usuario) {
        Usuario savedUsuario = usuarioRepository.save(usuario);  // Cambiado de User a Usuario
        LOGGER.info("Usuario creado con id: {}", savedUsuario.getId());
        return savedUsuario;
    }

    @Override
    public Usuario getUserById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id: " + id));
    }

    @Override
    public List<Usuario> listAllUsers() {
        return usuarioRepository.findAll();
    }


    @Override
    public Usuario updateUser(Long id, Usuario usuario) {  // Cambiado de User a Usuario
        Usuario existingUsuario = usuarioRepository.findById(id)  // Cambiado de User a Usuario
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id: " + id));

        existingUsuario.setNombre(usuario.getNombre());
        existingUsuario.setApellido(usuario.getApellido());
        existingUsuario.setEmail(usuario.getEmail());
        existingUsuario.setTelefono(usuario.getTelefono());
        // Se pueden actualizar otros atributos según convenga (por ejemplo, password o rol)

        Usuario updatedUsuario = usuarioRepository.save(existingUsuario);  // Cambiado de User a Usuario
        LOGGER.info("Usuario actualizado con id: {}", updatedUsuario.getId());
        return updatedUsuario;
    }



    @Override
    public void deleteUser(Long id) {
        Usuario existingUsuario = usuarioRepository.findById(id)  // Cambiado de User a Usuario
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario a eliminar con id: " + id));
        usuarioRepository.delete(existingUsuario);
        LOGGER.info("Usuario eliminado con id: {}", id);
    }
}
