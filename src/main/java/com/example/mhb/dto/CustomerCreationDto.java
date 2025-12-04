package com.example.mhb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreationDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    // NIDA must be exactly 20 digits
    @Pattern(regexp = "^\\d{20}$", message = "NIDA must be exactly 20 digits")
    private String nida;

    // Accepts +255(0) 755 876 985
    @Pattern(
            regexp = "^\\+255\\(0\\)\\s\\d{3}\\s\\d{3}\\s\\d{3}$",
            message = "Contact must match +255(0) xxx xxx xxx"
    )
    private String contact;

    private String gender;

    private String idType;

    private String version;
}
