package com.example.mhb.repository;

import com.example.mhb.entity.ValuationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValuationRepository extends JpaRepository<ValuationForm, Long> {

    @Query("""
        SELECT v FROM ValuationForm v
        JOIN FETCH v.customer
    """)
    List<ValuationForm> findAllWithCustomer();

    List<ValuationForm> findByStatus(String status);
}
