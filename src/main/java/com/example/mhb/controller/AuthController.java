package com.example.mhb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import com.example.mhb.dto.*;
import com.example.mhb.dto.auth.LoginRequestDto;
import com.example.mhb.entity.Staff;
import com.example.mhb.repository.StaffRepository;
import com.example.mhb.security.JwtService;

import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    private final StaffRepository repo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(StaffRepository repo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER - only ADMIN can create other users
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody StaffCreationDto dto) {
        if (repo.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        Staff s = new Staff();
        s.setUsername(dto.getUsername());
        s.setRole(dto.getRole());
        s.setPassword(passwordEncoder.encode(dto.getPassword()));
        Staff saved = repo.save(s);

        return ResponseEntity.ok(Map.of(
                "id", saved.getId(),
                "username", saved.getUsername(),
                "role", saved.getRole(),
                "message", "User created"
        ));
    }

    // LOGIN - returns access + refresh tokens
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {

   // System.out.println("===============================================================");       
      //  log.info("===========================================  Reached here {} ", request.getUsername());
      
        Optional<Staff> optionalStaff = repo.findByUsername(request.getUsername());

        if (optionalStaff.isEmpty() || !passwordEncoder.matches(request.getPassword(), optionalStaff.get().getPassword())) {
            return ResponseEntity.status(403).body(Map.of("error", "Invalid credentials"));
        }

        Staff staff = optionalStaff.get();
        String access = jwtService.generateAccessToken(staff.getUsername());
        String refresh = jwtService.generateRefreshToken(staff.getUsername());

        AuthResponseDto resp = new AuthResponseDto(access, refresh, "Bearer", staff.getUsername(), staff.getRole());
        return ResponseEntity.ok(resp);
    }

    // REFRESH - provide refresh token to get a new access token (and optional new refresh token)
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "refreshToken is required"));
        }

        try {
            String username = jwtService.extractUsername(refreshToken);
            // validate refresh token
            if (!jwtService.isTokenValid(refreshToken, username)) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
            }
            String newAccess = jwtService.generateAccessToken(username);
            String newRefresh = jwtService.generateRefreshToken(username); // optional: issue new refresh token
            return ResponseEntity.ok(Map.of("accessToken", newAccess, "refreshToken", newRefresh));
        } catch (Exception ex) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid token"));
        }
    }
}