package com.NomadNook.NomadNook.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class GoogleAuthController {

    private static final Logger logger = LoggerFactory.getLogger(GoogleAuthController.class);

    @GetMapping("/google-login")
    public ResponseEntity<String> googleLogin() {
        logger.info("Google login endpoint accessed");
        String authorizationUrl = "/oauth2/authorization/google";
        logger.info("Redirecting to: {}", authorizationUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, authorizationUrl)
                .body("Redirecting to Google login...");
    }
}