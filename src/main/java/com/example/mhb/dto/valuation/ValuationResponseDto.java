package com.example.mhb.dto.valuation;

import java.math.BigDecimal;
import java.time.Instant;

public class ValuationResponseDto {

    private Long id;
    private String collateralLocation;
    private String ownerFullName;
    private String partner1;
    private String partner2;
    private Boolean ownerIsCustomer;
    private String ownerPhone;
    private String ownerRelationship;
    private BigDecimal forcedSaleValue;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollateralLocation() {
        return collateralLocation;
    }

    public void setCollateralLocation(String collateralLocation) {
        this.collateralLocation = collateralLocation;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public String getPartner1() {
        return partner1;
    }

    public void setPartner1(String partner1) {
        this.partner1 = partner1;
    }

    public String getPartner2() {
        return partner2;
    }

    public void setPartner2(String partner2) {
        this.partner2 = partner2;
    }

    public Boolean getOwnerIsCustomer() {
        return ownerIsCustomer;
    }

    public void setOwnerIsCustomer(Boolean ownerIsCustomer) {
        this.ownerIsCustomer = ownerIsCustomer;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerRelationship() {
        return ownerRelationship;
    }

    public void setOwnerRelationship(String ownerRelationship) {
        this.ownerRelationship = ownerRelationship;
    }

    public BigDecimal getForcedSaleValue() {
        return forcedSaleValue;
    }

    public void setForcedSaleValue(BigDecimal forcedSaleValue) {
        this.forcedSaleValue = forcedSaleValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
