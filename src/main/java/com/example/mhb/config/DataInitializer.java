package com.example.mhb.config;

import com.example.mhb.entity.Staff;
import com.example.mhb.repository.StaffRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final StaffRepository staffRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:Admin@123}") // fallback; override in env for production
    private String adminPassword;

    public DataInitializer(StaffRepository staffRepo, PasswordEncoder passwordEncoder) {
        this.staffRepo = staffRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        staffRepo.findByUsername(adminUsername).orElseGet(() -> {
            Staff admin = new Staff();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole("ADMIN");
            return staffRepo.save(admin);
        });
    }
}
