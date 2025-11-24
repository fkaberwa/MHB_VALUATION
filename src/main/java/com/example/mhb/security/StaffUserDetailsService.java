package com.example.mhb.security;

import com.example.mhb.entity.Staff;
import com.example.mhb.repository.StaffRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class StaffUserDetailsService implements UserDetailsService {
    private final StaffRepository repo;

    public StaffUserDetailsService(StaffRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Staff staff = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(staff.getUsername())
                .password(staff.getPassword())
                .roles(staff.getRole())
                .build();
    }
}
