package com.example.mhb.service;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.entity.*;
import com.example.mhb.entity.enums.CollateralType;
import com.example.mhb.entity.enums.OwnershipCategory;
import com.example.mhb.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuditLogRepository auditLogRepository;

    public ValuationService(
            ValuationRepository valuationRepository,
            CustomerRepository customerRepository,
            AuditLogRepository auditLogRepository
    ) {
        this.valuationRepository = valuationRepository;
        this.customerRepository = customerRepository;
        this.auditLogRepository = auditLogRepository;
    }

    /* ================= CREATE ================= */
    public ValuationForm create(ValuationCreateDto dto) {

        if (dto.getNida() == null || dto.getNida().isBlank()) {
            throw new RuntimeException("NIDA is required");
        }

        Customer customer = customerRepository.findByNida(dto.getNida())
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

        // ✅ SAFE AMOUNT PARSING
        v.setForcedSaleValue(parseAmount(dto.getForcedSaleValue()));

        v.setCollateralType(
                CollateralType.valueOf(dto.getCollateralType().toUpperCase())
        );
        v.setOwnershipCategory(
                OwnershipCategory.valueOf(dto.getOwnershipCategory().toUpperCase())
        );

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

        ValuationForm saved = valuationRepository.save(v);

        auditLogRepository.save(buildAudit(
                "ValuationForm",
                saved.getId(),
                "CREATE",
                null,
                "PENDING"
        ));

        return saved;
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

    /* ================= STATUS UPDATE ================= */
    public ValuationForm updateStatus(Long id, String status) {

        if (!status.equals("APPROVED") && !status.equals("REJECTED")) {
            throw new RuntimeException("Invalid status");
        }

        ValuationForm v = findById(id);
        String oldStatus = v.getStatus();

        v.setStatus(status);
        v.setUpdatedAt(Instant.now());

        ValuationForm saved = valuationRepository.save(v);

        auditLogRepository.save(buildAudit(
                "ValuationForm",
                saved.getId(),
                status,
                oldStatus,
                status
        ));

        return saved;
    }

    /* ================= DELETE ================= */
    public void delete(Long id) {
        valuationRepository.deleteById(id);
    }

    /* ================= HELPERS ================= */
    private BigDecimal parseAmount(String amount) {
        try {
            return new BigDecimal(amount.replace(",", ""));
        } catch (Exception e) {
            throw new RuntimeException("Invalid forced sale value");
        }
    }

    private AuditLog buildAudit(
            String entity,
            Long entityId,
            String action,
            String oldValue,
            String newValue
    ) {
        AuditLog log = new AuditLog();
        log.setEntityName(entity);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setPerformedBy(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        log.setCreatedAt(Instant.now());
        return log;
    }
}
