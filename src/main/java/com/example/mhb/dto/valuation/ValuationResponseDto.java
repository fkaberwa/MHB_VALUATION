package com.example.mhb.dto.valuation;

import com.example.mhb.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ValuationResponseDto {

    private Long id;

    private Long customerId;
    private String customerName;

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
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
}
