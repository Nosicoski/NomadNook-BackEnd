package com.NomadNook.NomadNook.Service.Impl;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import com.NomadNook.NomadNook.DTO.REQUEST.UsuarioRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Service.IUsuarioService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

    @Value("${api.path}")
    private String API_PATH;

    // Inyectamos el repositorio y ModelMapper

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    private UsuarioResponse createUsuarioResponse(Usuario usuario) {
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNombre(usuario.getNombre());
        usuarioResponse.setApellido(usuario.getApellido());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setTelefono(usuario.getTelefono());
        usuarioResponse.setPassword(usuario.getPassword());
        usuarioResponse.setRol(usuario.getRol());
        usuarioResponse.setFechaRegistro(usuario.getFechaRegistro());
        usuarioResponse.setAlojamientos(API_PATH + "alojamientos/buscar/usuario/" + usuarioResponse.getId());
        return usuarioResponse;
    }

    @Override
    public UsuarioResponse createUser(UsuarioRequest usuarioRequest) {
        // Verificar que el email no esté registrado
        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }
        // Mapear del DTO de request a la entidad
        Usuario usuario = modelMapper.map(usuarioRequest, Usuario.class);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        LOGGER.info("Usuario creado con id: {} y email: {}", savedUsuario.getId(), savedUsuario.getEmail());
        // Mapear la entidad guardada a un DTO de respuesta
        return createUsuarioResponse(savedUsuario);
    }

    @Override
    public UsuarioResponse getUserById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id: " + id));
        return createUsuarioResponse(usuario);
    }

    @Override
    public List<UsuarioResponse> listAllUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> responses = new ArrayList<>();
        for(Usuario usuario: usuarios) {
            UsuarioResponse usuarioResponse = createUsuarioResponse(usuario);
            responses.add(usuarioResponse);
        }
        return responses;
    }

    @Override
    public UsuarioResponse updateUser(Long id, UsuarioRequest usuarioRequest) {
        // Verificar si el email ya está registrado por otro usuario
        Optional<Usuario> existingUsuarioWithEmail = usuarioRepository.findByEmail(usuarioRequest.getEmail());
        if (existingUsuarioWithEmail.isPresent() && !existingUsuarioWithEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("El email ya está registrado por otro usuario.");
        }
        // Buscar el usuario existente
        Usuario existingUsuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con id: " + id));

        // Actualizar la entidad existente con los valores del request
        modelMapper.map(usuarioRequest, existingUsuario);
        Usuario updatedUsuario = usuarioRepository.save(existingUsuario);
        LOGGER.info("Usuario actualizado con id: {}", updatedUsuario.getId());
        return createUsuarioResponse(updatedUsuario);
    }

    @Override
    public void deleteUser(Long id) {
        Usuario existingUsuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario a eliminar con id: " + id));
        usuarioRepository.delete(existingUsuario);
        LOGGER.info("Usuario eliminado con id: {}", id);
    }

}
