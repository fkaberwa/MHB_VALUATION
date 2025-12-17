package com.example.mhb.entity;

import com.example.mhb.entity.enums.CollateralType;
import com.example.mhb.entity.enums.OwnershipCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "valuations")
public class ValuationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* CUSTOMER */
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /* COLLATERAL */
    private String collateralLocation;

    @Enumerated(EnumType.STRING)
    private CollateralType collateralType;

    @Enumerated(EnumType.STRING)
    private OwnershipCategory ownershipCategory;

    /* OWNER */
    private String ownerFullName;
    private String ownerPhone;
    private String ownerRelationship;
    private Boolean ownerIsCustomer;

    private String partner1;
    private String partner2;

    /* VALUE */
    @Column(precision = 19, scale = 2)
    private BigDecimal forcedSaleValue;

    /* STATUS */
    private String status; // PENDING / APPROVED / REJECTED

    /* DATES */
    private Instant createdAt;
    private Instant updatedAt;
    private Instant expiresAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
