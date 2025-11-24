package com.example.mhb.dto;

import jakarta.validation.constraints.*;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffCreationDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank
    private String role; // e.g., ADMIN or STAFF
}
