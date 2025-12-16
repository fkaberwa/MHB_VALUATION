package com.example.mhb.dto.valuation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValuationCreateDto {

    private String firstName;
    private String middleName;
    private String surname;

    private String maritalStatus;

    private String nida;        // ✅ MUST be String
    private String phone;

    private Boolean ownerIsCustomer;

    private String collateralType;
    private String ownershipCategory;
    private String collateralLocation;

    private Double forcedSaleValue;

    private String ownerFullName;
    private String partner1;
    private String partner2;

    private String ownerPhone;
    private String ownerRelationship;
}
