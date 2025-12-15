package com.example.mhb.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiInfo {

    @Bean
    public Info apiInfo() {
        return new Info()
                .title("MHB Valuation Backend API")
                .version("1.0.0")
                .description("""
                    Secure admin-based valuation system.
                    
                    Flow:
                    1. Admin logs in
                    2. Receives JWT token
                    3. Uses token to manage customers and valuation forms
                    
                    All endpoints (except /auth) require Bearer Token.
                    """);
    }
}
