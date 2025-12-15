package com.example.mhb.dto.customer;

import com.example.mhb.entity.enums.Gender;
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

    @NotBlank(message = "Customer name is required")
    @Schema(example = "John Doe")
    private String name;

    @Schema(
        example = "12345678-12345-12345-12",
        description = "NIDA must be 20 digits in format xxxxxxxx-xxxxx-xxxxx-xx"
    )
    @Pattern(
        regexp = "^\\d{8}-\\d{5}-\\d{5}-\\d{2}$",
        message = "NIDA must be in format xxxxxxxx-xxxxx-xxxxx-xx"
    )
    private String nida;

    @NotBlank(message = "Contact phone number is required")
    @Schema(example = "+255 712 345 678")
    @Pattern(
        regexp = "^\\+255\\s\\d{3}\\s\\d{3}\\s\\d{3}$",
        message = "Contact must be in format +255 xxx xxx xxx"
    )
    private String contact;

    @NotNull(message = "Gender is required")
    @Schema(example = "MALE")
    private Gender gender;
}
