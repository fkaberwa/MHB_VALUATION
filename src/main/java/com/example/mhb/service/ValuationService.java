package com.example.mhb.service;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.entity.enums.CollateralType;
import com.example.mhb.entity.enums.OwnershipCategory;
import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.repository.ValuationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

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

    /* ================= CREATE ================= */
    public ValuationForm create(ValuationCreateDto dto) {

        if (dto.getNida() == null || dto.getNida().isBlank()) {
            throw new RuntimeException("NIDA is required");
        }

        Customer customer = customerRepository
                .findByNida(dto.getNida())
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setFullName(
                            (dto.getFirstName() + " " +
                             (dto.getMiddleName() == null ? "" : dto.getMiddleName() + " ") +
                             dto.getSurname()).trim()
                    );
                    c.setPhone(dto.getPhone());
                    c.setMaritalStatus(dto.getMaritalStatus());
                    c.setNida(dto.getNida());
                    return customerRepository.save(c);
                });

        ValuationForm v = new ValuationForm();
        v.setCustomer(customer);

        v.setCollateralLocation(dto.getCollateralLocation());

        if (dto.getForcedSaleValue() != null) {
            v.setForcedSaleValue(BigDecimal.valueOf(dto.getForcedSaleValue()));
        }

        if (dto.getCollateralType() != null) {
            v.setCollateralType(
                    CollateralType.valueOf(dto.getCollateralType().toUpperCase())
            );
        }

        if (dto.getOwnershipCategory() != null) {
            v.setOwnershipCategory(
                    OwnershipCategory.valueOf(dto.getOwnershipCategory().toUpperCase())
            );
        }

        v.setOwnerIsCustomer(dto.getOwnerIsCustomer());
        v.setOwnerFullName(dto.getOwnerFullName());
        v.setOwnerPhone(dto.getOwnerPhone());
        v.setOwnerRelationship(dto.getOwnerRelationship());
        v.setPartner1(dto.getPartner1());
        v.setPartner2(dto.getPartner2());

        v.setStatus("PENDING");
        v.setCreatedAt(Instant.now());

        if (dto.getExpiresAt() != null) {
            v.setExpiresAt(
                    dto.getExpiresAt()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
            );
        }

        return valuationRepository.save(v);
    }

    /* ================= READ ================= */
    public List<ValuationForm> findAll() {
        return valuationRepository.findAll();
    }

    public ValuationForm findById(Long id) {
        return valuationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valuation not found"));
    }

    public List<ValuationForm> findByStatus(String status) {
        return valuationRepository.findByStatus(status);
    }

    /* ================= PARTIAL UPDATE ================= */
    public ValuationForm partialUpdate(Long id, ValuationCreateDto dto) {

        ValuationForm v = findById(id);

        if (dto.getCollateralLocation() != null)
            v.setCollateralLocation(dto.getCollateralLocation());

        if (dto.getForcedSaleValue() != null)
            v.setForcedSaleValue(BigDecimal.valueOf(dto.getForcedSaleValue()));

        if (dto.getCollateralType() != null)
            v.setCollateralType(
                    CollateralType.valueOf(dto.getCollateralType().toUpperCase())
            );

        if (dto.getOwnershipCategory() != null)
            v.setOwnershipCategory(
                    OwnershipCategory.valueOf(dto.getOwnershipCategory().toUpperCase())
            );

        if (dto.getOwnerFullName() != null)
            v.setOwnerFullName(dto.getOwnerFullName());

        if (dto.getOwnerPhone() != null)
            v.setOwnerPhone(dto.getOwnerPhone());

        if (dto.getOwnerRelationship() != null)
            v.setOwnerRelationship(dto.getOwnerRelationship());

        if (dto.getPartner1() != null)
            v.setPartner1(dto.getPartner1());

        if (dto.getPartner2() != null)
            v.setPartner2(dto.getPartner2());

        if (dto.getExpiresAt() != null)
            v.setExpiresAt(
                    dto.getExpiresAt()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
            );

        v.setUpdatedAt(Instant.now());
        return valuationRepository.save(v);
    }

    /* ================= STATUS UPDATE ================= */
    public ValuationForm updateStatus(Long id, String status) {

        ValuationForm v = findById(id);

        if (!status.equals("APPROVED") && !status.equals("REJECTED")) {
            throw new RuntimeException("Invalid status");
        }

        v.setStatus(status);
        v.setUpdatedAt(Instant.now());

        return valuationRepository.save(v);
    }

    /* ================= DELETE ================= */
    public void delete(Long id) {
        valuationRepository.deleteById(id);
    }
}
