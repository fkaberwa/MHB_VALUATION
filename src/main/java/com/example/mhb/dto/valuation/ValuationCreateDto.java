package com.example.mhb.dto.valuation;

import com.example.mhb.entity.enums.*;
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

    @Schema(example = "LEGAL_MORTGAGE")
    private CollateralType collateralType;

    @Schema(example = "SINGLE_OWNED")
    private OwnershipType ownershipType;

    // Ownership details (validated in service layer)
    private String ownerFullName;
    private String spouseOneName;
    private String spouseTwoName;
    private String groupOwners;
    private String companyName;
    private String companyPhone;
    private String companyDirectors;

    @Positive(message = "Forced Sale Value must be positive")
    @Schema(example = "45000000")
    private BigDecimal forcedSaleValue;

    @Schema(example = "60000000")
    private BigDecimal marketValue;

    @Schema(example = "PENDING")
    private ValuationStatus status;
}
