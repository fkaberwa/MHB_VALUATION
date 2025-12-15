package com.example.mhb.controller;

import com.example.mhb.dto.auth.LoginRequestDto;
import com.example.mhb.dto.auth.TokenResponseDto;
import com.example.mhb.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(
            @RequestBody LoginRequestDto request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(
            @RequestHeader("Authorization") String header
    ) {
        String token = header.substring(7);
        return ResponseEntity.ok(authService.refresh(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String header
    ) {
        String token = header.substring(7);
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }
}
