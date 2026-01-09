package com.example.mhb.dto.valuation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ValuationCreateDto {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "NIDA is required")
    @Pattern(
        regexp = "\\d{20}",
        message = "NIDA must be exactly 20 digits"
    )
    private String nida;

    @NotBlank(message = "Phone is required")
    @Pattern(
        regexp = "\\+255\\(0\\)\\s\\d{3}\\s\\d{3}\\s\\d{3}",
        message = "Phone must be in format +255(0) xxx xxx xxx"
    )
    private String phone;

    @NotBlank(message = "Marital status is required")
    private String maritalStatus;

    @NotNull(message = "Owner flag is required")
    private Boolean ownerIsCustomer;

    private String ownerFullName;
    private String ownerPhone;
    private String ownerRelationship;

    private String partner1;
    private String partner2;

    @NotBlank(message = "Collateral location is required")
    private String collateralLocation;

    @NotBlank(message = "Collateral type is required")
    private String collateralType;

    @NotBlank(message = "Ownership category is required")
    private String ownershipCategory;

    @NotNull(message = "Forced sale value is required")
    @Positive(message = "Forced sale value must be positive")
    private String forcedSaleValue; // 👈 STRING to accept comma input

    private LocalDate expiresAt;
}
