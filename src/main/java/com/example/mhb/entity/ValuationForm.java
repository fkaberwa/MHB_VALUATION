package com.example.mhb.entity;

import com.example.mhb.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "valuation_forms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValuationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // === RELATIONSHIP ===
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // === COLLATERAL ===
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CollateralType collateralType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OwnershipType ownershipType;

    // === OWNERSHIP DETAILS ===
    private String ownerFullName;              // SINGLE
    private String spouseOneName;              // MATRIMONIAL
    private String spouseTwoName;

    @Column(columnDefinition = "TEXT")
    private String groupOwners;                // GROUP (comma separated)

    private String companyName;                // COMPANY
    private String companyPhone;
    private String companyDirectors;            // comma separated

    // === VALUES ===
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal forcedSaleValue;

    @Column(precision = 15, scale = 2)
    private BigDecimal marketValue;             // REQUIRED IF APPROVED

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValuationStatus status;

    // === AUDIT ===
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime deletedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
