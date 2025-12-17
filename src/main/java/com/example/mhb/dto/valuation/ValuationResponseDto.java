package com.example.mhb.dto.valuation;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class ValuationResponseDto {

    private Long id;
    private Long customerId;

    private String collateralLocation;
    private String collateralType;
    private String ownershipCategory;

    private String ownerFullName;
    private String ownerPhone;
    private String ownerRelationship;
    private Boolean ownerIsCustomer;

    private String partner1;
    private String partner2;

    private BigDecimal forcedSaleValue;
    private String status;

    private Instant createdAt;
    private Instant expiresAt;
}
