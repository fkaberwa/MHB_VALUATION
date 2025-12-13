package com.example.mhb.seeder;

import com.example.mhb.entity.AdminUser;
import com.example.mhb.repository.AdminUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner seedAdmin(AdminUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(
                        AdminUser.builder()
                                .username("admin")
                                .password(encoder.encode("Admin@123"))
                                .build()
                );
            }
        };
    }
}
