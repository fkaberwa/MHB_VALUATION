package com.example.mhb.entity;

import com.example.mhb.entity.enums.CollateralType;
import com.example.mhb.entity.enums.OwnershipCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "valuations")
@Getter
@Setter
public class ValuationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private CollateralType collateralType;

    @Enumerated(EnumType.STRING)
    private OwnershipCategory ownershipCategory;

    private String ownerFullName;
    private String partner1;
    private String partner2;

    private Boolean ownerIsCustomer;
    private String ownerPhone;
    private String ownerRelationship;

    private String collateralLocation;

    @Column(precision = 19, scale = 2)
    private BigDecimal forcedSaleValue;

    private String status; // DRAFT, APPROVED, REJECTED
}
