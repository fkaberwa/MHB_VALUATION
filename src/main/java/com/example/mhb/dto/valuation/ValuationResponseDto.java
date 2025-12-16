package com.example.mhb.dto.valuation;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ValuationResponseDto {

    private Long id;
    private Long customerId;

    private String collateralType;
    private String ownershipCategory;

    private String ownerFullName;
    private String partner1;
    private String partner2;

    private Boolean ownerIsCustomer;
    private String ownerPhone;
    private String ownerRelationship;

    private String collateralLocation;
    private BigDecimal forcedSaleValue;

    private String status;
}
