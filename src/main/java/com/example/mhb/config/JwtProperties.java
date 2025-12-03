package com.example.mhb.config;

import com.example.mhb.security.JwtKeyManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    private final JwtKeyManager keyManager;

    @Value("${jwt.access-token-expiration-ms:600000}")  // 10 minutes default
    private long accessTokenExpirationMs;

    @Value("${jwt.refresh-token-expiration-ms:2592000000}") // 30 days
    private long refreshTokenExpirationMs;

    public JwtProperties(JwtKeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public String getCurrentSecret() {
        return keyManager.getCurrentSecret();
    }

    public String getPreviousSecret() {
        return keyManager.getPreviousSecret();
    }

    public long getAccessTokenExpirationMs() {
        return accessTokenExpirationMs;
    }

    public long getRefreshTokenExpirationMs() {
        return refreshTokenExpirationMs;
    }
}