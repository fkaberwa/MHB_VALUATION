package com.example.mhb.security;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class JwtKeyManager {

    private final String secret;

    public JwtKeyManager() {
        byte[] bytes = new byte[64];
        new SecureRandom().nextBytes(bytes);
        this.secret = Base64.getEncoder().encodeToString(bytes);
    }

    public String getSecret() {
        return secret;
    }
}
