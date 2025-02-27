package com.NomadNook.NomadNook.Service.Impl;

import com.NomadNook.NomadNook.Service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
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
        helper.setSubject("Registration Confirmation");

        String emailContent = """
            <html>
                <body>
                    <h2>Welcome to Our Platform!</h2>
                    <p>Dear %s,</p>
                    <p>Thank you for registering with us. Your account has been successfully created.</p>
                    <p>You can now log in to your account and start using our services.</p>
                    <br>
                    <p>Best regards,</p>
                    <p>Your Application Team</p>
                </body>
            </html>
            """.formatted(username);

        helper.setText(emailContent, true); // true indicates HTML content
        mailSender.send(message);
    }

}