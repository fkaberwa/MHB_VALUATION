package com.example.mhb.dto.valuation;

import com.example.mhb.entity.enums.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Valuation form response payload")
public class ValuationResponseDto {

    private Long id;
    private Long customerId;

    private CollateralType collateralType;
    private OwnershipType ownershipType;

    private String ownerFullName;
    private String spouseOneName;
    private String spouseTwoName;
    private String groupOwners;
    private String companyName;
    private String companyPhone;
    private String companyDirectors;

    private BigDecimal forcedSaleValue;
    private BigDecimal marketValue;

    private ValuationStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
