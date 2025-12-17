package com.example.mhb.repository;

import com.example.mhb.entity.ValuationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ValuationRepository
        extends JpaRepository<ValuationForm, Long>,
                JpaSpecificationExecutor<ValuationForm> {
}
