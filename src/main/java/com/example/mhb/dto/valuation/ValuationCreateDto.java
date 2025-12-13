package com.example.mhb.dto.valuation;

import com.example.mhb.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Payload for creating or updating a valuation form")
public class ValuationCreateDto {

    @Schema(example = "1")
    private Long customerId;

    private CollateralType collateralType;
    private OwnershipType ownershipType;

    // ===== OWNERSHIP DETAILS =====
    private String ownerFullName;     // SINGLE_OWNED
    private String spouseOneName;     // MATRIMONIAL
    private String spouseTwoName;
    private String groupOwners;       // GROUP (comma separated)
    private String companyName;       // COMPANY
    private String companyPhone;
    private String companyDirectors;

    // ===== VALUES =====
    @Positive(message = "Forced Sale Value must be positive")
    private BigDecimal forcedSaleValue;

    private BigDecimal marketValue;

    private ValuationStatus status;
}
