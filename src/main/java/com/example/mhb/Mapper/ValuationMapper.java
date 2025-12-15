package com.example.mhb.Mapper;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.dto.valuation.ValuationResponseDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.entity.enums.ValuationStatus;
import org.springframework.lang.NonNull;

public class ValuationMapper {

	@NonNull
	public static ValuationForm toEntity(
			ValuationCreateDto dto,
			Customer customer
	) {
		ValuationForm v = new ValuationForm();
		v.setCustomer(customer);
		v.setCollateralType(dto.getCollateralType());
		v.setOwnershipType(dto.getOwnershipType());
		v.setOwnerFullName(dto.getOwnerFullName());
		v.setMarketValue(dto.getMarketValue());
		v.setForcedSaleValue(dto.getForcedSaleValue());
		v.setStatus(ValuationStatus.PENDING);
		return v;
	}

	public static ValuationResponseDto toDto(ValuationForm v) {
		ValuationResponseDto dto = new ValuationResponseDto();
		dto.setId(v.getId());
		dto.setCustomerId(v.getCustomer().getId());
		dto.setCollateralType(v.getCollateralType());
		dto.setOwnershipType(v.getOwnershipType());
		dto.setOwnerFullName(v.getOwnerFullName());
		dto.setMarketValue(v.getMarketValue());
		dto.setForcedSaleValue(v.getForcedSaleValue());
		dto.setStatus(v.getStatus());
		return dto;
	}
}
