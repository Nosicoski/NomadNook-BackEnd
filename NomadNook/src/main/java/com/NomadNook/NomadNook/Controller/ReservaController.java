package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Service.IReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Reservas")
public class ReservaController {private final IReservaService reservaService;

    public ReservaController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }


    // CREA una Reserva
    @PostMapping("/guardar")
    public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
        Reserva createdReserva = reservaService.createReserva(reserva);
        return ResponseEntity.ok(createdReserva);
    }



    // TRAE todas las Reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.listAllReservas();
        return ResponseEntity.ok(reservas);
    }
    // TRAE una Reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Reserva reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }


    // ACTUALIZA una Reserva por ID
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva updatedReserva = reservaService.updateReserva(id, reserva);
        return ResponseEntity.ok(updatedReserva);
    }

    // ELIMINA una Reserva por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }
}