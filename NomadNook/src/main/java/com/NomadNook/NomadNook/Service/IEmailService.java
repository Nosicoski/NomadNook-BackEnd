package com.NomadNook.NomadNook.Service;

import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendConfirmationEmail(String to, String username) throws MessagingException;

}
