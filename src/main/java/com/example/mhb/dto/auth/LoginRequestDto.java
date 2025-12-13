package com.example.mhb.dto.auth;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
