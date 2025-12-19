package com.example.mhb.mapper;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.dto.valuation.ValuationResponseDto;
import com.example.mhb.entity.ValuationForm;

import java.math.BigDecimal;
import java.time.Instant;

public class ValuationMapper {

    // CREATE
    public static ValuationForm toEntity(ValuationCreateDto dto) {
        ValuationForm v = new ValuationForm();

        v.setCollateralLocation(dto.getCollateralLocation());
        v.setOwnerFullName(dto.getOwnerFullName());
        v.setPartner1(dto.getPartner1());
        v.setPartner2(dto.getPartner2());
        v.setOwnerIsCustomer(dto.getOwnerIsCustomer());
        v.setOwnerPhone(dto.getOwnerPhone());
        v.setOwnerRelationship(dto.getOwnerRelationship());

        if (dto.getForcedSaleValue() != null) {
            v.setForcedSaleValue(BigDecimal.valueOf(dto.getForcedSaleValue()));
        }

        v.setStatus("PENDING");
        v.setCreatedAt(Instant.now());
        return v;
    }

    // READ
    public static ValuationResponseDto toDto(ValuationForm v) {
        ValuationResponseDto dto = new ValuationResponseDto();

        dto.setId(v.getId());
        dto.setCollateralLocation(v.getCollateralLocation());
        dto.setOwnerFullName(v.getOwnerFullName());
        dto.setPartner1(v.getPartner1());
        dto.setPartner2(v.getPartner2());
        dto.setOwnerIsCustomer(v.getOwnerIsCustomer());
        dto.setOwnerPhone(v.getOwnerPhone());
        dto.setOwnerRelationship(v.getOwnerRelationship());
        dto.setForcedSaleValue(v.getForcedSaleValue());
        dto.setStatus(v.getStatus());
        dto.setCreatedAt(v.getCreatedAt());
        dto.setUpdatedAt(v.getUpdatedAt());

        return dto;
    }

    // PARTIAL UPDATE
    public static void partialUpdate(
            ValuationForm v,
            ValuationCreateDto dto
    ) {
        if (dto.getOwnerFullName() != null)
            v.setOwnerFullName(dto.getOwnerFullName());

        if (dto.getPartner1() != null)
            v.setPartner1(dto.getPartner1());

        if (dto.getPartner2() != null)
            v.setPartner2(dto.getPartner2());

        if (dto.getForcedSaleValue() != null)
            v.setForcedSaleValue(BigDecimal.valueOf(dto.getForcedSaleValue()));

        v.setUpdatedAt(Instant.now());
    }
}
