package com.example.mhb.service;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.Mapper.ValuationMapper;
import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.repository.ValuationRepository;
import org.springframework.stereotype.Service;

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
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ValuationForm valuation =
                ValuationMapper.toEntity(dto, customer);

        return valuationRepo.save(valuation);
    }
}
