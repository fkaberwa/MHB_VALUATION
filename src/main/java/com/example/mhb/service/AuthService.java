package com.example.mhb.service;

import com.example.mhb.dto.auth.LoginRequestDto;
import com.example.mhb.dto.auth.TokenResponseDto;
import com.example.mhb.entity.Admin;
import com.example.mhb.entity.RefreshToken;
import com.example.mhb.repository.AdminRepository;
import com.example.mhb.repository.RefreshTokenRepository;
import com.example.mhb.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AdminRepository adminRepository,
            RefreshTokenRepository refreshTokenRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponseDto login(LoginRequestDto request) {

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(admin.getUsername());
        String refreshToken = jwtService.generateRefreshToken(admin.getUsername());

        RefreshToken token = new RefreshToken();
        token.setUsername(admin.getUsername());
        token.setToken(refreshToken);
        token.setRevoked(false);
        refreshTokenRepository.save(token);

        return new TokenResponseDto(accessToken, refreshToken, 900);
    }

    public TokenResponseDto refresh(String refreshToken) {

    RefreshToken stored = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

    if (stored.isRevoked() || stored.getExpiresAt().isBefore(java.time.Instant.now())) {
        throw new RuntimeException("Refresh token expired or revoked");
    }

    String newAccessToken = jwtService.generateAccessToken(stored.getUsername());
    String newRefreshToken = jwtService.generateRefreshToken(stored.getUsername());

    stored.setRevoked(true);
    refreshTokenRepository.save(stored);

    RefreshToken newToken = new RefreshToken();
    newToken.setUsername(stored.getUsername());
    newToken.setToken(newRefreshToken);
    newToken.setRevoked(false);
    refreshTokenRepository.save(newToken);

    return new TokenResponseDto(newAccessToken, newRefreshToken, 900);
}

public void logout(String refreshToken) {
    refreshTokenRepository.findByToken(refreshToken)
            .ifPresent(token -> {
                token.setRevoked(true);
                refreshTokenRepository.save(token);
            });
}

}
