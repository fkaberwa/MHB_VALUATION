package com.example.mhb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValuationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propertyType;
    private String ownerName;
    private String location;
    private Double estimatedValue;
    private String status; // e.g. Pending, Approved, Rejected

    // 🔗 Relationship with Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")  // foreign key column in valuation_form table
    private Customer customer;
}
