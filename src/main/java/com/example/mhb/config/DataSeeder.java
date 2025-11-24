package com.example.mhb.config;

import com.example.mhb.entity.Staff;
import com.example.mhb.repository.StaffRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedAdmin(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // Check if admin already exists
            if (staffRepository.findByUsername("mmihayo").isEmpty()) {
                Staff admin = new Staff();
                admin.setUsername("mmihayo");
                admin.setRole("ADMIN");
                admin.setPassword(passwordEncoder.encode("Admin@123")); // default password

                staffRepository.save(admin);

                System.out.println("✔ Admin user created successfully (username='admin', password='Admin@123')");
            } else {
                System.out.println("✔ Admin user already exists. No new admin created.");
            }

        };
    }
}
