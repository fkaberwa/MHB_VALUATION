package com.example.mhb.dto.valuation;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ValuationCreateDto {

    private String firstName;
    private String middleName;
    private String surname;

    private String nida;
    private String phone;
    private String maritalStatus;

    private Boolean ownerIsCustomer;

    private String ownerFullName;
    private String ownerPhone;
    private String ownerRelationship;

    private String partner1;
    private String partner2;

    private String collateralLocation;
    private String collateralType;      // LEGAL / EQUITABLE
    private String ownershipCategory;    // SINGLE / MATRIMONIAL

    private Double forcedSaleValue;

    private Instant expiresAt;
}
