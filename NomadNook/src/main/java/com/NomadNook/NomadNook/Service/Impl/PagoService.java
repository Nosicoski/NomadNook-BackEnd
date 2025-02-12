package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Model.Pago;
import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Repository.IPagoRepository;
import com.NomadNook.NomadNook.Repository.IReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final IPagoRepository pagoRepository;
    private final IReservaRepository reservaRepository;

    @Autowired
    public PagoService(IPagoRepository pagoRepository, IReservaRepository reservaRepository) {
        this.pagoRepository = pagoRepository;
        this.reservaRepository = reservaRepository;
    }

    // Obtener todos los pagos
    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    // Obtener un pago por su ID
    public Optional<Pago> getPagoById(Long id) {
        return pagoRepository.findById(id);
    }

    // Crear un nuevo pago
    public Pago createPago(Pago pago) {
        // Verificar si la reserva existe antes de crear el pago
        if (!reservaRepository.existsById(pago.getReserva().getId())) {
            throw new IllegalArgumentException("Reserva no encontrada con id: " + pago.getReserva().getId());
        }

        // Guardar el pago en la base de datos
        return pagoRepository.save(pago);
    }



    // Actualizar un pago existente
    public Optional<Pago> updatePago(Long id, Pago pago) {

        Optional<Pago> existingPago = pagoRepository.findById(id);
        if (existingPago.isPresent()) {
            Pago updatedPago = existingPago.get();
            updatedPago.setMonto(pago.getMonto());
            updatedPago.setFechaPago(pago.getFechaPago());
            updatedPago.setMetodoPago(pago.getMetodoPago());
            updatedPago.setEstado(pago.getEstado());


            return Optional.of(pagoRepository.save(updatedPago));
        }

        return Optional.empty();
    }


    // Eliminar un pago
    public boolean deletePago(Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
