package com.example.mhb.service;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.Mapper.ValuationMapper;
import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.repository.ValuationRepository;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class ValuationService {

    private final ValuationRepository valuationRepo;
    private final CustomerRepository customerRepo;

    public ValuationService(
            ValuationRepository valuationRepo,
            CustomerRepository customerRepo
    ) {
        this.valuationRepo = valuationRepo;
        this.customerRepo = customerRepo;
    }

    public ValuationForm create(ValuationCreateDto dto) {
        Long customerId = Objects.requireNonNull(dto.getCustomerId(), "Customer ID is required");

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ValuationForm valuation =
                ValuationMapper.toEntity(dto, customer);

        return valuationRepo.save(valuation);
    }
}
