package com.example.mhb.service;

import com.example.mhb.dto.auth.LoginResponseDto;
import com.example.mhb.entity.User;
import com.example.mhb.repository.UserRepository;
import com.example.mhb.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto login(
            String username,
            String password,
            String role
    ) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password")
                );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // 🔐 ROLE CHECK (VERY IMPORTANT)
        if (!user.getRole().name().equalsIgnoreCase(role)) {
            throw new RuntimeException("You are not authorized for this role");
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new LoginResponseDto(
                token,
                user.getUsername(),
                user.getRole().name().toLowerCase()
        );
    }
}
