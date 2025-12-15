package com.example.mhb.service;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.mapper.ValuationMapper;
import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.repository.ValuationRepository;
import org.springframework.stereotype.Service;

@Service
public class ValuationService {

    private final ValuationRepository valuationRepository;
    private final CustomerRepository customerRepository;

    public ValuationService(
            ValuationRepository valuationRepository,
            CustomerRepository customerRepository
    ) {
        this.valuationRepository = valuationRepository;
        this.customerRepository = customerRepository;
    }

    public ValuationForm create(ValuationCreateDto dto) {

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ValuationForm valuation = ValuationMapper.toEntity(dto, customer);
        return valuationRepository.save(valuation);
    }
}
