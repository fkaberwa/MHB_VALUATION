package com.example.mhb.mapper;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.dto.valuation.ValuationResponseDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.entity.enums.CollateralType;
import com.example.mhb.entity.enums.OwnershipCategory;

import io.micrometer.common.lang.NonNull;

import java.math.BigDecimal;

public class ValuationMapper {
    @NonNull
    public static ValuationForm toEntity(
            ValuationCreateDto dto,
            Customer customer
    ) {
        ValuationForm v = new ValuationForm();
        v.setCustomer(customer);

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

        v.setOwnerFullName(dto.getOwnerFullName());
        v.setPartner1(dto.getPartner1());
        v.setPartner2(dto.getPartner2());
        v.setOwnerIsCustomer(dto.getOwnerIsCustomer());
        v.setOwnerPhone(dto.getOwnerPhone());
        v.setOwnerRelationship(dto.getOwnerRelationship());
        v.setCollateralLocation(dto.getCollateralLocation());

        if (dto.getForcedSaleValue() != null) {
            v.setForcedSaleValue(BigDecimal.valueOf(dto.getForcedSaleValue()));
        }

        v.setStatus("DRAFT");
        return v;
    }

    public static ValuationResponseDto toDto(ValuationForm v) {
        ValuationResponseDto dto = new ValuationResponseDto();

        dto.setId(v.getId());

        // ✅ FIX: null-safe customer access
        Long customerId =
                v.getCustomer() != null ? v.getCustomer().getId() : null;
        dto.setCustomerId(customerId);

        dto.setCollateralType(
                v.getCollateralType() != null ? v.getCollateralType().name() : null
        );
        dto.setOwnershipCategory(
                v.getOwnershipCategory() != null ? v.getOwnershipCategory().name() : null
        );

        dto.setOwnerFullName(v.getOwnerFullName());
        dto.setPartner1(v.getPartner1());
        dto.setPartner2(v.getPartner2());
        dto.setOwnerIsCustomer(v.getOwnerIsCustomer());
        dto.setOwnerPhone(v.getOwnerPhone());
        dto.setOwnerRelationship(v.getOwnerRelationship());
        dto.setCollateralLocation(v.getCollateralLocation());
        dto.setForcedSaleValue(v.getForcedSaleValue());
        dto.setStatus(v.getStatus());

        return dto;
    }
}



























// package com.example.mhb.mapper;

// import com.example.mhb.dto.valuation.ValuationCreateDto;
// import com.example.mhb.dto.valuation.ValuationResponseDto;
// import com.example.mhb.entity.Customer;
// import com.example.mhb.entity.ValuationForm;
// import com.example.mhb.entity.enums.CollateralType;
// import com.example.mhb.entity.enums.OwnershipCategory;

// import java.math.BigDecimal;

// public class ValuationMapper {

//     public static ValuationForm toEntity(
//             ValuationCreateDto dto,
//             Customer customer
//     ) {
//         ValuationForm v = new ValuationForm();
//         v.setCustomer(customer);

//         if (dto.getCollateralType() != null) {
//             v.setCollateralType(
//                     CollateralType.valueOf(dto.getCollateralType().toUpperCase())
//             );
//         }

//         if (dto.getOwnershipCategory() != null) {
//             v.setOwnershipCategory(
//                     OwnershipCategory.valueOf(dto.getOwnershipCategory().toUpperCase())
//             );
//         }

//         v.setOwnerFullName(dto.getOwnerFullName());
//         v.setPartner1(dto.getPartner1());
//         v.setPartner2(dto.getPartner2());
//         v.setOwnerIsCustomer(dto.getOwnerIsCustomer());
//         v.setOwnerPhone(dto.getOwnerPhone());
//         v.setOwnerRelationship(dto.getOwnerRelationship());
//         v.setCollateralLocation(dto.getCollateralLocation());

//         if (dto.getForcedSaleValue() != null) {
//             v.setForcedSaleValue(BigDecimal.valueOf(dto.getForcedSaleValue()));
//         }

//         v.setStatus("DRAFT");
//         return v;
//     }

//     public static ValuationResponseDto toDto(ValuationForm v) {
//         ValuationResponseDto dto = new ValuationResponseDto();
//         dto.setId(v.getId());
//         dto.setCustomerId(v.getCustomer().getId());
//         dto.setCollateralType(v.getCollateralType().name());
//         dto.setOwnershipCategory(v.getOwnershipCategory().name());
//         dto.setOwnerFullName(v.getOwnerFullName());
//         dto.setPartner1(v.getPartner1());
//         dto.setPartner2(v.getPartner2());
//         dto.setOwnerIsCustomer(v.getOwnerIsCustomer());
//         dto.setOwnerPhone(v.getOwnerPhone());
//         dto.setOwnerRelationship(v.getOwnerRelationship());
//         dto.setCollateralLocation(v.getCollateralLocation());
//         dto.setForcedSaleValue(v.getForcedSaleValue());
//         dto.setStatus(v.getStatus());
//         return dto;
//     }
// }
