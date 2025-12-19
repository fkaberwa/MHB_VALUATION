package com.example.mhb.seeder;

import com.example.mhb.entity.Role;
import com.example.mhb.entity.User;
import com.example.mhb.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seedUsers() {

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("approver").isEmpty()) {
            User approver = new User();
            approver.setUsername("approver");
            approver.setPassword(passwordEncoder.encode("approver123"));
            approver.setRole(Role.APPROVER);
            approver.setEnabled(true);
            userRepository.save(approver);
        }
    }
}
