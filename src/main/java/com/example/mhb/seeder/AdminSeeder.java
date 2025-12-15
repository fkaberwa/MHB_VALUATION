package com.example.mhb.seeder;

import com.example.mhb.entity.Admin;
import com.example.mhb.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner seedAdmin(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (adminRepository.findByUsername("admin").isEmpty()) {

                Admin admin = new Admin();
                admin.setUsername("mmihayo");
                admin.setPassword(
                        passwordEncoder.encode("Admin@123")
                );

                adminRepository.save(admin);

                System.out.println("✔ Default admin created: username=admin, password=Admin@123");
            }
        };
    }
}
