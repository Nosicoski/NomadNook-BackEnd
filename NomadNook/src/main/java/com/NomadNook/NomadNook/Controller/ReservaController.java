package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Service.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    // Crear una nueva reserva
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        Reserva nuevaReserva = reservaService.createReserva(reserva);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    // Obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodasLasReservas() {
        List<Reserva> reservas = reservaService.listReservas();
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    // Obtener una reserva por id
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.getReservaById(id);
        return reserva.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener reservas por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Reserva>> obtenerReservasPorCliente(@PathVariable Long clienteId) {
        List<Reserva> reservas = reservaService.listReservasByCliente(clienteId);
        if (reservas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    // Actualizar una reserva
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Optional<Reserva> reservaExistente = reservaService.getReservaById(id);
        if (reservaExistente.isPresent()) {
            reserva.setId(id); // Asegúrate de que la reserva tenga el ID correcto
            Reserva reservaActualizada = reservaService.updateReserva(id, reserva);
            return new ResponseEntity<>(reservaActualizada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        if (reservaService.deleteReserva(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Respuesta 204 (eliminado correctamente)
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
