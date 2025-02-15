package com.NomadNook.NomadNook.Config;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class InitConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            IAlojamientoRepository alojamientoRepository,
            IUsuarioRepository usuarioRepository) {
        return args -> {
            // Crear usuario propietario
            Usuario propietario = Usuario.builder()
                    .nombre("Juan Pérez")
                    .email("juan@example.com")
                    .password("123456")
                    .telefono("123456789")
                    .rol(Usuario.Rol.ADMIN)
                    .build();

            usuarioRepository.save(propietario);

            // Crear alojamientos primero (sin imágenes inicialmente)

            Alojamiento cabana = Alojamiento.builder()
                    .titulo("Cabaña Rústica en el Bosque")
                    .descripcion("Hermosa cabaña de madera rodeada de naturaleza, perfecta para escapadas románticas. " +
                            "Cuenta con chimenea, cocina completa y vista panorámica al bosque.")
                    .tipo(Alojamiento.TipoAlojamiento.CABAÑA)
                    .capacidad(4)
                    .precioPorNoche(new BigDecimal("150.00"))
                    .ubicacion("Bariloche")
                    .direccion("Ruta 40 Km 2134")
                    .disponible(true)
                    .propietario(propietario)
                    .imagenes(new ArrayList<>())
                    .build();

            Alojamiento departamento = Alojamiento.builder()
                    .titulo("Moderno Departamento Céntrico")
                    .descripcion("Departamento totalmente equipado en el corazón de la ciudad. " +
                            "A pasos del transporte público y principales atracciones.")
                    .tipo(Alojamiento.TipoAlojamiento.DEPARTAMENTO)
                    .capacidad(2)
                    .precioPorNoche(new BigDecimal("80.00"))
                    .ubicacion("Buenos Aires")
                    .direccion("Av. Corrientes 1234")
                    .disponible(true)
                    .propietario(propietario)
                    .imagenes(new ArrayList<>())
                    .build();

            Alojamiento casa = Alojamiento.builder()
                    .titulo("Casa de Playa con Vista al Mar")
                    .descripcion("Espaciosa casa frente al mar con terraza y acceso directo a la playa. " +
                            "Ideal para familias grandes o grupos de amigos.")
                    .tipo(Alojamiento.TipoAlojamiento.CASA)
                    .capacidad(8)
                    .precioPorNoche(new BigDecimal("300.00"))
                    .ubicacion("Mar del Plata")
                    .direccion("Av. Costanera 567")
                    .disponible(true)
                    .propietario(propietario)
                    .imagenes(new ArrayList<>())
                    .build();

            // Guardar alojamientos
            alojamientoRepository.saveAll(Arrays.asList(cabana, departamento, casa));

        };
    }
}