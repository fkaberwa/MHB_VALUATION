package com.example.mhb.controller;

import com.example.mhb.dto.auth.LoginRequestDto;
import com.example.mhb.dto.auth.TokenResponseDto;
import com.example.mhb.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

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

    @GetMapping("/me")
    public ResponseEntity<String> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("unauthenticated");
        }
        return ResponseEntity.ok(authentication.getName());
    }

}

