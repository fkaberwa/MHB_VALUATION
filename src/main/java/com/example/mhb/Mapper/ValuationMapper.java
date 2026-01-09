package com.example.mhb.mapper;

import com.example.mhb.dto.valuation.ValuationResponseDto;
import com.example.mhb.entity.ValuationForm;

public class ValuationMapper {

    public static ValuationResponseDto toDto(ValuationForm v) {

        ValuationResponseDto dto = new ValuationResponseDto();

        dto.setId(v.getId());

        if (v.getCustomer() != null) {
            dto.setCustomerId(v.getCustomer().getId());
            dto.setCustomerName(v.getCustomer().getFullName());
            dto.setCustomerPhone(v.getCustomer().getPhone());
        }

        dto.setOwnerIsCustomer(v.getOwnerIsCustomer());
        dto.setOwnerFullName(v.getOwnerFullName());
        dto.setOwnerPhone(v.getOwnerPhone());
        dto.setOwnerRelationship(v.getOwnerRelationship());

        dto.setOwnershipCategory(
                v.getOwnershipCategory() != null
                        ? v.getOwnershipCategory().name()
                        : null
        );

        dto.setPartner1(v.getPartner1());
        dto.setPartner2(v.getPartner2());

        dto.setCollateralLocation(v.getCollateralLocation());
        dto.setCollateralType(
                v.getCollateralType() != null
                        ? v.getCollateralType().name()
                        : null
        );

        dto.setForcedSaleValue(v.getForcedSaleValue());
        dto.setStatus(v.getStatus());
        dto.setCreatedAt(v.getCreatedAt());
        dto.setExpiresAt(v.getExpiresAt());
        dto.setUpdatedAt(v.getUpdatedAt());

        return dto;
    }
}
