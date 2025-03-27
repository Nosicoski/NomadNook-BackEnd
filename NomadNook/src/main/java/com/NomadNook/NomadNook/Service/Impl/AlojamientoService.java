package com.NomadNook.NomadNook.Service.Impl;


import com.NomadNook.NomadNook.Exception.ResourceNotFoundException;
import com.NomadNook.NomadNook.Model.Alojamiento;
import com.NomadNook.NomadNook.Model.Caracteristica;
import com.NomadNook.NomadNook.Model.Categoria;
import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Repository.IAlojamientoRepository;
import com.NomadNook.NomadNook.Repository.ICaracteristicaRepository;

import com.NomadNook.NomadNook.DTO.REQUEST.AlojamientoRequest;
import com.NomadNook.NomadNook.DTO.RESPONSE.AlojamientoResponse;
import com.NomadNook.NomadNook.Repository.ICategoriaRepository;
import com.NomadNook.NomadNook.Repository.IReservaRepository;
import com.NomadNook.NomadNook.Service.IAlojamientoService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AlojamientoService implements IAlojamientoService {

    private final IAlojamientoRepository alojamientoRepository;
    private final ICaracteristicaRepository caracteristicaRepository;
    private final ICategoriaRepository categoriaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(AlojamientoService.class);
    private ModelMapper modelMapper;
    private final IReservaRepository reservaRepository;

    @Value("${api.path}")
    private String API_PATH;

    @Autowired
    public AlojamientoService(
            ModelMapper modelMapper,
            IAlojamientoRepository alojamientoRepository,
            ICaracteristicaRepository caracteristicaRepository,
            ICategoriaRepository categoriaRepository,
            IReservaRepository reservaRepository) {
        this.modelMapper = modelMapper;
        this.alojamientoRepository = alojamientoRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.categoriaRepository = categoriaRepository;
        this.reservaRepository = reservaRepository;
    }

    private AlojamientoResponse createAlojamientoResponse(Alojamiento alojamiento) {

        AlojamientoResponse alojamientoResponse = new AlojamientoResponse();
        alojamientoResponse.setId(alojamiento.getId());
        alojamientoResponse.setTitulo(alojamiento.getTitulo());
        alojamientoResponse.setDescripcion(alojamiento.getDescripcion());
        alojamientoResponse.setCategorias(alojamiento.getCategorias());
        alojamientoResponse.setCapacidad(alojamiento.getCapacidad());
        alojamientoResponse.setPrecioPorNoche(alojamiento.getPrecioPorNoche());
        alojamientoResponse.setUbicacion(alojamiento.getUbicacion());
        alojamientoResponse.setDireccion(alojamiento.getDireccion());
        alojamientoResponse.setDisponible(alojamiento.getDisponible());
        alojamientoResponse.setPropietario_id(alojamiento.getPropietario().getId());
        alojamientoResponse.setImagenes(alojamiento.getImagenes());
        alojamientoResponse.setCaracteristicas(alojamiento.getCaracteristicas());


        alojamientoResponse.setFechaReservaInicio(alojamiento.getFechaReservaInicio());
        alojamientoResponse.setFechaReservaFin(alojamiento.getFechaReservaFin());

        return alojamientoResponse;
    }

    @Override
    public AlojamientoResponse createAlojamiento(AlojamientoRequest requestDTO) {

        Alojamiento alojamiento = modelMapper.map(requestDTO, Alojamiento.class);


        Alojamiento alojamientoGuardado = alojamientoRepository.save(alojamiento);


        return createAlojamientoResponse(alojamientoGuardado);
    }


    @Override
    public AlojamientoResponse getAlojamientoById(Long id) {
        Alojamiento alojamiento = alojamientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento con id: " + id));
        return createAlojamientoResponse(alojamiento);
    }

    @Override
    public List<AlojamientoResponse> listAllAlojamientos() {
        List<Alojamiento> alojamientos = alojamientoRepository.findAll();
        List<AlojamientoResponse> responses = new ArrayList<>();
        for (Alojamiento alojamiento : alojamientos) {
            AlojamientoResponse alojamientoResponse = createAlojamientoResponse(alojamiento);
            responses.add(alojamientoResponse);
        }
        return responses;
    }

    @Override
    public AlojamientoResponse updateAlojamiento(Long id, AlojamientoRequest request) {

        Alojamiento existingAlojamiento = alojamientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento con id: " + id));


        if (!existingAlojamiento.getTitulo().equals(request.getTitulo()) && alojamientoRepository.existsByTitulo(request.getTitulo())) {
            throw new IllegalArgumentException("Ya existe un alojamiento con el mismo nombre");
        }


        modelMapper.map(request, existingAlojamiento);


        Alojamiento updatedAlojamiento = alojamientoRepository.save(existingAlojamiento);
        LOGGER.info("Alojamiento actualizado con id: {}", updatedAlojamiento.getId());
        return createAlojamientoResponse(updatedAlojamiento);
    }


    @Override
    public void deleteAlojamiento(Long id) {
        Alojamiento existingAlojamiento = alojamientoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró el alojamiento a eliminar con id: " + id));
        alojamientoRepository.delete(existingAlojamiento);
        LOGGER.info("Alojamiento eliminado con id: {}", id);
    }

    @Override
    public List<AlojamientoResponse> listAllAlojamientosByPropietario(Long propietario_id) {

        List<Alojamiento> alojamientos = alojamientoRepository.findAllByPropietarioId(propietario_id);
        List<AlojamientoResponse> responses = new ArrayList<>();
        for (Alojamiento alojamiento : alojamientos) {
            AlojamientoResponse alojamientoResponse = createAlojamientoResponse(alojamiento);
            responses.add(alojamientoResponse);
        }
        return responses;
    }

    @Override
    public void agregarCaracteristicaAlojamiento(Long alojamientoId, Long caracteristicaId) {
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado"));

        Caracteristica caracteristica = caracteristicaRepository.findById(caracteristicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Característica no encontrada"));

        alojamiento.getCaracteristicas().add(caracteristica);
        alojamientoRepository.save(alojamiento);
    }

    @Override
    public void agregarCategoriaAlojamiento(Long alojamientoId, Long  categoriaId) {
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado"));

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));

        alojamiento.getCategorias().add(categoria);
        alojamientoRepository.save(alojamiento);
    }

    @Override
    public void agregarCaracteristicasAlojamiento(Long alojamientoId, Set<Caracteristica> caracteristicas) {
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado"));

        alojamiento.setCaracteristicas(caracteristicas);
        alojamientoRepository.save(alojamiento);
    }

    @Override
    public void agregarCategoriasAlojamiento(Long alojamientoId, Set<Categoria> categorias) {
        Alojamiento alojamiento = alojamientoRepository.findById(alojamientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Alojamiento no encontrado"));

        alojamiento.setCategorias(categorias);
        alojamientoRepository.save(alojamiento);

    }

    @Override
    public boolean isAlojamientoDisponible(Long alojamientoId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Define los estados de reserva que consideras para bloquear el alojamiento.
        // Por ejemplo, supongamos que solo consideramos el estado CONFIRMADA.
        List<Reserva.EstadoReserva> estadosValidos = Arrays.asList(Reserva.EstadoReserva.CONFIRMADA);

        List<Reserva> reservas = reservaRepository
                .findByAlojamientoIdAndEstadoInAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                        alojamientoId,
                        estadosValidos,
                        fechaFin,   // fecha máxima: la fechaFin de la consulta
                        fechaInicio // fecha mínima: la fechaInicio de la consulta
                );

        // Si no hay reservas que se solapen, el alojamiento está disponible.
        return reservas.isEmpty();
    }

    public List<AlojamientoResponse> buscarAlojamientosDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        // Validación de fechas
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        List<Alojamiento> alojamientos = alojamientoRepository.findAvailableAlojamientosInDateRange(fechaInicio, fechaFin);
        List<AlojamientoResponse> responses = new ArrayList<>();
        for(Alojamiento alojamiento : alojamientos) {
            AlojamientoResponse response = createAlojamientoResponse(alojamiento);
            responses.add(response);
        }

        // Usar el método del repositorio
        return responses;
    }


}

