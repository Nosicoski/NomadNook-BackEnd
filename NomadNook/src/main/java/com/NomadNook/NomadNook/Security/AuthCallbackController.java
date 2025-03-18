package com.NomadNook.NomadNook.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthCallbackController {

    private static final Logger logger = LoggerFactory.getLogger(AuthCallbackController.class);

    @GetMapping("/loginSuccess")
    public ResponseEntity<String> loginSuccess(Authentication authentication) {
        logger.info("Login successful for user: {}",
                authentication != null ? authentication.getName() : "Unknown");
        return ResponseEntity.ok("Login successful!");
    }

    @GetMapping("/loginFailure")
    public ResponseEntity<String> loginFailure() {
        logger.error("Login failed");
        return ResponseEntity.status(401).body("Login failed!");
    }
}