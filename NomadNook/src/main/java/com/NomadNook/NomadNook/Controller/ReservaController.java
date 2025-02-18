package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Service.IReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Reserva")
public class ReservaController {private final IReservaService reservaService;

    public ReservaController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.listAllReservas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Reserva reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        Reserva createdReserva = reservaService.createReserva(reserva);
        return ResponseEntity.ok(createdReserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva updatedReserva = reservaService.updateReserva(id, reserva);
        return ResponseEntity.ok(updatedReserva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }
}