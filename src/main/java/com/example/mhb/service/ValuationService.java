package com.example.mhb.service;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.mapper.ValuationMapper;
import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.repository.ValuationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /* ================= CREATE ================= */
    public ValuationForm create(ValuationCreateDto dto) {

        if (!Boolean.TRUE.equals(dto.getOwnerIsCustomer())) {
            throw new RuntimeException("Owner must be a customer");
        }

        if (dto.getNida() == null || dto.getNida().isBlank()) {
            throw new RuntimeException("NIDA is required");
        }

        String fullName = String.join(" ",
                dto.getFirstName(),
                dto.getMiddleName(),
                dto.getSurname()
        ).trim();

        Customer customer = customerRepository.findByNida(dto.getNida())
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setFullName(fullName);
                    c.setPhone(dto.getPhone());
                    c.setMaritalStatus(dto.getMaritalStatus());
                    c.setNida(dto.getNida());
                    return customerRepository.save(c);
                });

        ValuationForm valuation = ValuationMapper.toEntity(dto, customer);
        valuation.setStatus("PENDING"); // workflow start

        return valuationRepository.save(valuation);
    }

    /* ================= READ ================= */
    public List<ValuationForm> findAll() {
        return valuationRepository.findAll();
    }

    public ValuationForm findById(Long id) {
        return valuationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valuation not found"));
    }

    /* ================= PARTIAL UPDATE ================= */
    public ValuationForm partialUpdate(Long id, ValuationCreateDto dto) {

        ValuationForm valuation = findById(id);

        ValuationMapper.partialUpdate(valuation, dto);

        return valuationRepository.save(valuation);
    }

    /* ================= DELETE ================= */
    public void delete(Long id) {
        ValuationForm valuation = findById(id);
        valuationRepository.delete(valuation);
    }
}
