package com.example.mhb.config;

import com.example.mhb.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(sess ->
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                /* ================= AUTH ================= */
                .requestMatchers("/api/auth/**").permitAll()

                /* ================= ADMIN ================= */
                .requestMatchers(HttpMethod.POST, "/api/valuations/create")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PATCH, "/api/valuations/update/**")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/api/valuations/delete/**")
                    .hasRole("ADMIN")

                /* ================= READ (ADMIN + APPROVER) ================= */
                .requestMatchers(HttpMethod.GET, "/api/valuations/list")
                    .hasAnyRole("ADMIN", "APPROVER")

                .requestMatchers(HttpMethod.GET, "/api/valuations/view/**")
                    .hasAnyRole("ADMIN", "APPROVER")

                /* ================= APPROVER ================= */
                .requestMatchers(HttpMethod.GET, "/api/valuations/pending")
                    .hasRole("APPROVER")

                .requestMatchers(HttpMethod.PUT, "/api/valuations/approve/**")
                    .hasRole("APPROVER")

                .requestMatchers(HttpMethod.PUT, "/api/valuations/reject/**")
                    .hasRole("APPROVER")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
