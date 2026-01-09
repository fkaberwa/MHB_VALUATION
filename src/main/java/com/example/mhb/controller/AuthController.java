package com.example.mhb.controller;

import com.example.mhb.dto.auth.LoginRequestDto;
import com.example.mhb.dto.auth.LoginResponseDto;
import com.example.mhb.dto.common.ApiResponse;
import com.example.mhb.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @RequestBody LoginRequestDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Login successful",
                        authService.login(
                                request.getUsername(),
                                request.getPassword()
                        )
                )
        );
    }
}
