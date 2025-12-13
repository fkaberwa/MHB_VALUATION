package com.example.mhb.dto.customer;

import com.example.mhb.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload for creating or updating a customer")
public class CustomerCreateDto {

    @Schema(
        example = "John Doe",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Customer name is required")
    private String name;

    @Schema(
        example = "12345678-12345-12345-12",
        description = "NIDA must be exactly 20 digits in format xxxxxxxx-xxxxx-xxxxx-xx"
    )
    @Pattern(
        regexp = "^\\d{8}-\\d{5}-\\d{5}-\\d{2}$",
        message = "NIDA must be in format xxxxxxxx-xxxxx-xxxxx-xx (20 digits)"
    )
    private String nida;

    @Schema(
        example = "+255 712 345 678",
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "Phone number must be in format +255 xxx xxx xxx"
    )
    @NotBlank(message = "Contact phone number is required")
    @Pattern(
        regexp = "^\\+255\\s\\d{3}\\s\\d{3}\\s\\d{3}$",
        message = "Contact must be in format +255 xxx xxx xxx"
    )
    private String contact;

    @Schema(
        example = "MALE",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "Gender is required")
    private Gender gender;
}
