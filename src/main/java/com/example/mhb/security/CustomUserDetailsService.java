package com.example.mhb.security;

import com.example.mhb.repository.AdminRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository repo;

    public CustomUserDetailsService(AdminRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repo.findByUsername(username)
            .map(a -> User.withUsername(a.getUsername())
                .password(a.getPassword())
                .roles("ADMIN")
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
    }
}
