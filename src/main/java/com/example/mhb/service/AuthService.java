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

import java.time.Instant;

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

    // 🔐 LOGIN
    public TokenResponseDto login(LoginRequestDto request) {

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(admin.getUsername());
        String refreshToken = jwtService.generateRefreshToken(admin.getUsername());

        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUsername(admin.getUsername());
        token.setExpiresAt(Instant.now().plusSeconds(30L * 24 * 60 * 60)); // 30 days
        token.setRevoked(false);

        refreshTokenRepository.save(token);

        return new TokenResponseDto(
                accessToken,
                refreshToken,
                900 // 15 minutes
        );
    }

    // 🔄 REFRESH
    public TokenResponseDto refresh(String refreshToken) {

        RefreshToken stored = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (stored.isRevoked() || stored.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired or revoked");
        }

        String newAccessToken = jwtService.generateAccessToken(stored.getUsername());

        return new TokenResponseDto(
                newAccessToken,
                refreshToken,
                900
        );
    }

    // 🚪 LOGOUT
    public void logout(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
}
