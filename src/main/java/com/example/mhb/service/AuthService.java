package com.example.mhb.service;

import com.example.mhb.entity.User;
import com.example.mhb.repository.UserRepository;
import com.example.mhb.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public String login(String username, String password) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username, password
                        )
                );

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}
