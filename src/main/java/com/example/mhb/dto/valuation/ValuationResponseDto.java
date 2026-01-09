package com.example.mhb.dto.valuation;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class ValuationResponseDto {

    private Long id;

    /* ================= CUSTOMER ================= */
    private Long customerId;
    private String customerName;
    private String customerPhone;

    /* ================= OWNERSHIP ================= */
    private Boolean ownerIsCustomer;
    private String ownerFullName;
    private String ownerPhone;
    private String ownerRelationship;
    private String ownershipCategory;
    private String partner1;
    private String partner2;

    /* ================= COLLATERAL ================= */
    private String collateralLocation;
    private String collateralType;
    private BigDecimal forcedSaleValue;

    /* ================= STATUS ================= */
    private String status;

    /* ================= DATES ================= */
    private Instant createdAt;
    private Instant expiresAt;
    private Instant updatedAt;
}
