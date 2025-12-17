package com.example.mhb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = "nida")
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(nullable = false, length = 20)
    private String nida;

    private String phone;

    private String maritalStatus;
}
