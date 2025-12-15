package com.example.mhb.mapper;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.dto.valuation.ValuationResponseDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;

public class ValuationMapper {

    public static ValuationForm toEntity(ValuationCreateDto dto, Customer customer) {
        ValuationForm v = new ValuationForm();
        v.setCustomer(customer);
        v.setCollateralType(dto.getCollateralType());
        v.setOwnershipType(dto.getOwnershipType());
        v.setOwnerFullName(dto.getOwnerFullName());
        v.setSpouseOneName(dto.getSpouseOneName());
        v.setSpouseTwoName(dto.getSpouseTwoName());
        v.setGroupOwners(dto.getGroupOwners());
        v.setCompanyName(dto.getCompanyName());
        v.setCompanyPhone(dto.getCompanyPhone());
        v.setCompanyDirectors(dto.getCompanyDirectors());
        v.setForcedSaleValue(dto.getForcedSaleValue());
        v.setMarketValue(dto.getMarketValue());
        v.setStatus(dto.getStatus());
        return v;
    }

    public static ValuationResponseDto toDto(ValuationForm v) {
        ValuationResponseDto dto = new ValuationResponseDto();
        dto.setId(v.getId());
        dto.setCustomerId(v.getCustomer().getId());
        dto.setCollateralType(v.getCollateralType());
        dto.setOwnershipType(v.getOwnershipType());
        dto.setOwnerFullName(v.getOwnerFullName());
        dto.setSpouseOneName(v.getSpouseOneName());
        dto.setSpouseTwoName(v.getSpouseTwoName());
        dto.setGroupOwners(v.getGroupOwners());
        dto.setCompanyName(v.getCompanyName());
        dto.setCompanyPhone(v.getCompanyPhone());
        dto.setCompanyDirectors(v.getCompanyDirectors());
        dto.setForcedSaleValue(v.getForcedSaleValue());
        dto.setMarketValue(v.getMarketValue());
        dto.setStatus(v.getStatus());
        return dto;
    }
}
