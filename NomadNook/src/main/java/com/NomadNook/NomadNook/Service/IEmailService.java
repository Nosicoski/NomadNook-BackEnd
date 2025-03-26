package com.NomadNook.NomadNook.Service;

import com.NomadNook.NomadNook.DTO.RESPONSE.ReservaResponse;
import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendConfirmationEmail(String to, String username) throws MessagingException;
    void sendReservaEmail(ReservaResponse reserva) throws MessagingException;
}
