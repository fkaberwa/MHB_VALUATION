package com.example.mhb.security;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class JwtKeyManager {

    private static final Logger log = LoggerFactory.getLogger(JwtKeyManager.class);

    private String currentSecret;
    private String previousSecret;

    @PostConstruct
    public void init() {
        previousSecret = currentSecret;
        currentSecret = generateSecret();

        log.warn("JWT SECRET AUTO-GENERATED (10-min access tokens active)");
        log.warn("Current secret (first 20 chars): {}...", currentSecret.substring(0, 20));
    }

    private String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[64]; // 512-bit → perfect for HS256
        random.nextBytes(bytes);
        return Base64.getEncoder().withoutPadding().encodeToString(bytes);
    }

    public String getCurrentSecret() {
        return currentSecret;
    }

    public String getPreviousSecret() {
        return previousSecret;
    }
}