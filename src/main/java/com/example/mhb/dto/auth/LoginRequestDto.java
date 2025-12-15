package com.example.mhb.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Login request payload")
public class LoginRequestDto {

    @NotBlank(message = "Username is required")
    @Schema(example = "admin")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(example = "Admin@123")
    private String password;
}
