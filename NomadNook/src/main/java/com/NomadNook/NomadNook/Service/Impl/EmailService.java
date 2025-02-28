package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String EMAIL;

    public void sendConfirmationEmail(String toEmail, String username) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(EMAIL);
        helper.setTo(toEmail);
        helper.setSubject("¡Bienvenido! Te has registrado a NomadNook");

        String emailContent = """
            <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                        <h2 style="color: #2c3e50;">¡Bienvenido a nuestra plataforma!</h2>
                        
                        <p>Estimado(a) %s:</p>
                        
                        <p>¡Nos alegra confirmar que tu registro se ha completado exitosamente!</p>
                        
                        <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;">
                            <h3 style="margin-top: 0;">Detalles de tu cuenta:</h3>
                            <p>• Nombre: %s</p>
                            <p>• Correo electrónico: %s</p>
                        </div>
                        
                        <p>Para acceder a tu cuenta, haz clic en el siguiente enlace:</p>
                        
                        <div style="text-align: center; margin: 25px 0;">
                            <a href="http://www.ejemplo.com/login" 
                               style="background-color: #007bff; 
                                      color: white; 
                                      padding: 10px 20px; 
                                      text-decoration: none; 
                                      border-radius: 5px; 
                                      font-weight: bold;">
                                Iniciar Sesión
                            </a>
                        </div>
                        
                        <p>Si tienes alguna pregunta o necesitas ayuda, no dudes en contactar a nuestro equipo de soporte.</p>
                        
                        <p>¡Gracias por registrarte con nosotros!</p>
                        
                        <p>Saludos cordiales,<br>
                        El Equipo de NomadNook</p>
                        
                        <hr style="border: 1px solid #eee; margin: 20px 0;">
                        
                        <p style="font-size: 12px; color: #666;">
                            Nota: Si no realizaste este registro, por favor ignora este mensaje o contáctanos inmediatamente.
                        </p>
                    </div>
                </body>
            </html>
            """.formatted(username, username, toEmail);

        helper.setText(emailContent, true); // true indicates HTML content
        mailSender.send(message);
    }

}