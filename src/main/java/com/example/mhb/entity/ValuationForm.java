package com.example.mhb.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "valuations")
public class ValuationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Customer customer;

    private String collateralType;
    private String ownershipType;

    private String ownerFullName;
    private String spouseOneName;
    private String spouseTwoName;

    private String groupOwners;

    private String companyName;
    private String companyPhone;
    private String companyDirectors;

    private BigDecimal forcedSaleValue;
    private BigDecimal marketValue;

    private String status;

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCollateralType() {
        return collateralType;
    }

    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public String getSpouseOneName() {
        return spouseOneName;
    }

    public void setSpouseOneName(String spouseOneName) {
        this.spouseOneName = spouseOneName;
    }

    public String getSpouseTwoName() {
        return spouseTwoName;
    }

    public void setSpouseTwoName(String spouseTwoName) {
        this.spouseTwoName = spouseTwoName;
    }

    public String getGroupOwners() {
        return groupOwners;
    }

    public void setGroupOwners(String groupOwners) {
        this.groupOwners = groupOwners;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyDirectors() {
        return companyDirectors;
    }

    public void setCompanyDirectors(String companyDirectors) {
        this.companyDirectors = companyDirectors;
    }

    public BigDecimal getForcedSaleValue() {
        return forcedSaleValue;
    }

    public void setForcedSaleValue(BigDecimal forcedSaleValue) {
        this.forcedSaleValue = forcedSaleValue;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
