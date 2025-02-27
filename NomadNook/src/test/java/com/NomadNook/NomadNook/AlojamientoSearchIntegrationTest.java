package com.NomadNook.NomadNook;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Alojamiento.TipoAlojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class AlojamientoSearchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IAlojamientoRepository alojamientoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        // Limpiar las tablas de prueba
        alojamientoRepository.deleteAll();
        usuarioRepository.deleteAll();
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("123456"); // No vacío
        usuario.setFechaRegistro(LocalDateTime.now()); // No nulo
        usuario.setRol(Usuario.Rol.ADMIN); // No nulo
        usuario.setTelefono("1234567890");

        usuarioRepository.save(usuario);

        // Insertar un alojamiento de prueba
        Alojamiento alojamiento = new Alojamiento();
        alojamiento.setTitulo("Hermoso alojamiento en la playa");
        alojamiento.setDescripcion("Disfruta de unas vacaciones con vista al mar y excelentes comodidades.");
        alojamiento.setTipo(TipoAlojamiento.PLAYA);
        alojamiento.setCapacidad(4);
        alojamiento.setPrecioPorNoche(new BigDecimal("150.00"));
        alojamiento.setUbicacion("Playa Cancún");
        alojamiento.setDireccion("Av. del Mar 123");
        alojamiento.setDisponible(true);
        alojamiento.setPropietario(usuario);
        alojamientoRepository.save(alojamiento);
    }

    @Test
    public void testSearchAlojamientos() throws Exception {
        // Realizar búsqueda utilizando la palabra "playa" que aparece en el título y descripción
        mockMvc.perform(get("/api/alojamientos/buscar")
                        .param("query", "playa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Se espera que al menos se encuentre un resultado
                .andExpect(jsonPath("$", hasSize(1)))
                // Verificar que el título del alojamiento encontrado contenga la palabra "playa"
                .andExpect(jsonPath("$[0].titulo").value("Hermoso alojamiento en la playa"));
    }

}