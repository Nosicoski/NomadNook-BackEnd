package com.NomadNook.NomadNook.Config;

import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Imagen;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.IImagenRepository;
import com.NomadNook.NomadNook.Repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

////@Configuration
//public class ConfigInit {
//
////    @Autowired
////    private PasswordEncoder passwordEncoder;
////
//// //   @Bean
////    CommandLineRunner init(IUsuarioRepository usuarioRepository,
////                           IAlojamientoRepository alojamientoRepository,
////                           IImagenRepository imagenRepository) {
////        return args -> {
////            // Crear usuarios
////            Usuario admin = new Usuario();
////            admin.setNombre("Admin");
////            admin.setApellido("Sistema");
////            admin.setEmail("admin@sistema.com");
////            admin.setPassword(passwordEncoder.encode("admin123"));
////            admin.setRol(Usuario.Rol.ADMIN);
////            admin.setTelefono("123456789");
////            admin.setFechaRegistro(LocalDateTime.now());
////            admin.setAlojamientos(new ArrayList<>());
////            usuarioRepository.save(admin);
////
////            Usuario propietario = new Usuario();
////            propietario.setNombre("Juan");
////            propietario.setApellido("Pérez");
////            propietario.setEmail("juan@example.com");
////            propietario.setPassword(passwordEncoder.encode("123456"));
////            propietario.setRol(Usuario.Rol.CLIENTE);
////            propietario.setTelefono("987654321");
////            propietario.setFechaRegistro(LocalDateTime.now());
////            propietario.setAlojamientos(new ArrayList<>());
////            usuarioRepository.save(propietario);
////
////            // Crear alojamientos
////            Alojamiento cabana = new Alojamiento();
////            cabana.setTitulo("Cabaña en el bosque");
////            cabana.setDescripcion("Hermosa cabaña rodeada de naturaleza");
////            cabana.setTipo(Alojamiento.TipoAlojamiento.CABAÑA);
////            cabana.setCapacidad(4);
////            cabana.setPrecioPorNoche(new BigDecimal("100.00"));
////            cabana.setUbicacion("Bosque Verde");
////            cabana.setDireccion("Camino del Bosque km 5");
////            cabana.setDisponible(true);
////            cabana.setPropietario(propietario);
////            cabana.setImagenes(new ArrayList<>());
////
////            Alojamiento departamento = new Alojamiento();
////            departamento.setTitulo("Departamento céntrico");
////            departamento.setDescripcion("Moderno departamento en el centro");
////            departamento.setTipo(Alojamiento.TipoAlojamiento.DEPARTAMENTO);
////            departamento.setCapacidad(2);
////            departamento.setPrecioPorNoche(new BigDecimal("80.00"));
////            departamento.setUbicacion("Centro Ciudad");
////            departamento.setDireccion("Av. Principal 123");
////            departamento.setDisponible(true);
////            departamento.setPropietario(propietario);
////            departamento.setImagenes(new ArrayList<>());
////
////            // Guardar alojamientos
////            alojamientoRepository.save(cabana);
////            alojamientoRepository.save(departamento);
////
////            // Crear y guardar imágenes
////            Imagen imagen1 = new Imagen();
////            imagen1.setUrl("https://ejemplo.com/cabana1.jpg");
////            imagen1.setAlojamiento(cabana);
////
////            Imagen imagen2 = new Imagen();
////            imagen2.setUrl("https://ejemplo.com/cabana2.jpg");
////            imagen2.setAlojamiento(cabana);
////
////            Imagen imagen3 = new Imagen();
////            imagen3.setUrl("https://ejemplo.com/depto1.jpg");
////            imagen3.setAlojamiento(departamento);
////
////            // Guardar imágenes
////            imagenRepository.save(imagen1);
////            imagenRepository.save(imagen2);
////            imagenRepository.save(imagen3);
////
////            // Actualizar listas de imágenes en alojamientos
////            List<Imagen> imagenesCabana = new ArrayList<>();
////            imagenesCabana.add(imagen1);
////            imagenesCabana.add(imagen2);
////            cabana.setImagenes(imagenesCabana);
////
////            List<Imagen> imagenesDepto = new ArrayList<>();
////            imagenesDepto.add(imagen3);
////            departamento.setImagenes(imagenesDepto);
////
////            // Actualizar alojamientos
////            alojamientoRepository.save(cabana);
////            alojamientoRepository.save(departamento);
////
////            // Actualizar lista de alojamientos del propietario
////            List<Alojamiento> alojamientosPropietario = new ArrayList<>();
////            alojamientosPropietario.add(cabana);
////            alojamientosPropietario.add(departamento);
////            propietario.setAlojamientos(alojamientosPropietario);
////            usuarioRepository.save(propietario);
////        };
//    }
//}