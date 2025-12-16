package com.example.mhb.repository;

import com.example.mhb.entity.ValuationForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValuationRepository extends JpaRepository<ValuationForm, Long> {

    // 🔍 Search by CUSTOMER NIDA
    Page<ValuationForm> findByCustomer_NidaContainingIgnoreCase(
            String nida,
            Pageable pageable
    );
}
