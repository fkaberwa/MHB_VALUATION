package com.example.mhb.service;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.entity.enums.CollateralType;
import com.example.mhb.entity.enums.OwnershipCategory;
import com.example.mhb.mapper.ValuationMapper;
import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.repository.ValuationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
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

    // ================= CREATE =================
    public ValuationForm create(ValuationCreateDto dto) {

        if (!Boolean.TRUE.equals(dto.getOwnerIsCustomer())) {
            throw new RuntimeException("Owner must be a customer");
        }

        if (dto.getNida() == null || dto.getNida().isBlank()) {
            throw new RuntimeException("NIDA is required");
        }

        String fullName = String.join(" ",
                dto.getFirstName() == null ? "" : dto.getFirstName(),
                dto.getMiddleName() == null ? "" : dto.getMiddleName(),
                dto.getSurname() == null ? "" : dto.getSurname()
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
        return valuationRepository.save(valuation);
    }

    // ================= READ ALL =================
    public List<ValuationForm> findAll() {
        return valuationRepository.findAll();
    }

    // ================= READ BY ID =================
    public ValuationForm findById(Long id) {
        Objects.requireNonNull(id, "Valuation ID must not be null");
        return valuationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valuation not found"));
    }

    // ================= PARTIAL UPDATE =================
    public ValuationForm partialUpdate(Long id, ValuationCreateDto dto) {

        ValuationForm valuation = findById(id);

        if (dto.getCollateralType() != null) {
            valuation.setCollateralType(
                    CollateralType.valueOf(dto.getCollateralType().toUpperCase())
            );
        }

        if (dto.getOwnershipCategory() != null) {
            valuation.setOwnershipCategory(
                    OwnershipCategory.valueOf(dto.getOwnershipCategory().toUpperCase())
            );
        }

        if (dto.getOwnerFullName() != null) {
            valuation.setOwnerFullName(dto.getOwnerFullName());
        }

        if (dto.getPartner1() != null) {
            valuation.setPartner1(dto.getPartner1());
        }

        if (dto.getPartner2() != null) {
            valuation.setPartner2(dto.getPartner2());
        }

        if (dto.getOwnerIsCustomer() != null) {
            valuation.setOwnerIsCustomer(dto.getOwnerIsCustomer());
        }

        if (dto.getOwnerPhone() != null) {
            valuation.setOwnerPhone(dto.getOwnerPhone());
        }

        if (dto.getOwnerRelationship() != null) {
            valuation.setOwnerRelationship(dto.getOwnerRelationship());
        }

        if (dto.getCollateralLocation() != null) {
            valuation.setCollateralLocation(dto.getCollateralLocation());
        }

        if (dto.getForcedSaleValue() != null) {
            valuation.setForcedSaleValue(
                    BigDecimal.valueOf(dto.getForcedSaleValue())
            );
        }

        return valuationRepository.save(valuation);
    }

    // ================= DELETE =================
    public void delete(Long id) {
        ValuationForm valuation = findById(id);
        valuationRepository.delete(valuation);
    }
}
