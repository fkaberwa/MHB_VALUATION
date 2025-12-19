package com.example.mhb.repository;

import com.example.mhb.entity.ValuationForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValuationRepository extends JpaRepository<ValuationForm, Long> {

    List<ValuationForm> findByStatus(String status);
}
