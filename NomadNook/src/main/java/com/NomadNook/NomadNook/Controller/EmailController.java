package com.NomadNook.NomadNook.Controller;

import com.NomadNook.NomadNook.DTO.REQUEST.EmailRequest;
import com.NomadNook.NomadNook.Service.Impl.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/resend")
    public ResponseEntity<?> resendConfirmationEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendConfirmationEmail(request.getEmail(), request.getUsername());
            return ResponseEntity
                    .ok()
                    .body("Correo de confirmación reenviado exitosamente.");
        } catch (MessagingException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al reenviar el correo de confirmación. Por favor, intenta más tarde.");
        }

    }
}