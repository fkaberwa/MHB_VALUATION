package com.example.mhb.seeder;

import com.example.mhb.entity.User;
import com.example.mhb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedUsers(
            UserRepository userRepository,
            PasswordEncoder encoder
    ) {
        return args -> {

            if (userRepository.count() == 0) {

                User admin = new User();
                admin.setUsername("mmihayo");
                admin.setPassword(encoder.encode("Admin@123"));
                admin.setRole("ADMIN");
                userRepository.save(admin);

                User approver = new User();
                approver.setUsername("mfreddy");
                approver.setPassword(encoder.encode("Approver@123"));
                approver.setRole("APPROVER");
                userRepository.save(approver);
            }
        };
    }
}
