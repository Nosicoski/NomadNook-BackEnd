package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.RESPONSE.ReservaResponse;
import com.NomadNook.NomadNook.DTO.RESPONSE.UsuarioResponse;
import com.NomadNook.NomadNook.Model.Reserva;
import com.NomadNook.NomadNook.Model.Usuario;
import com.NomadNook.NomadNook.Service.IEmailService;
import com.NomadNook.NomadNook.Service.IReservaService;
import com.NomadNook.NomadNook.Service.IUsuarioService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Reservas")
public class ReservaController {

    private final IReservaService reservaService;
    private final IEmailService emailService;
    private final IUsuarioService usuarioService;

    public ReservaController(IReservaService reservaService, IEmailService emailService, IUsuarioService usuarioService) {
        this.reservaService = reservaService;
        this.emailService = emailService;
        this.usuarioService = usuarioService;
    }



    // CREA una Reserva
    @PostMapping("/guardar")
    public ResponseEntity<ReservaResponse> createReserva(@RequestBody Reserva reserva) throws MessagingException {
        ReservaResponse createdReserva = reservaService.createReserva(reserva);
        UsuarioResponse usuario = usuarioService.getUserById(reserva.getCliente().getId());
        System.out.println(usuario.getEmail() +" "+  usuario.getNombre() +" "+ reserva);
        emailService.sendReservaEmail(createdReserva);
        return ResponseEntity.ok(createdReserva);
    }



    // TRAE todas las Reservas

    @GetMapping ("/listarTodos")
    public ResponseEntity<List<ReservaResponse>> getAllReservas() {
        List<ReservaResponse> reservas = reservaService.listAllReservas();
        return ResponseEntity.ok(reservas);
    }
    // TRAE una Reserva por ID

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ReservaResponse> getReservaById(@PathVariable Long id) {
        ReservaResponse reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }


    // ACTUALIZA una Reserva por ID

    @PutMapping ("/actualizar/{id}")
    public ResponseEntity<ReservaResponse> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        ReservaResponse updatedReserva = reservaService.updateReserva(id, reserva);
        return ResponseEntity.ok(updatedReserva);
    }

    // ELIMINA una Reserva por ID

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }
}