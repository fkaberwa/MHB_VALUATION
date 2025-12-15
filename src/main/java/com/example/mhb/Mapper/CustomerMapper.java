package com.example.mhb.Mapper;

import com.example.mhb.dto.customer.CustomerCreateDto;
import com.example.mhb.dto.customer.CustomerResponseDto;
import com.example.mhb.entity.Customer;
import org.springframework.lang.NonNull;

public class CustomerMapper {

	@NonNull
	public static Customer toEntity(CustomerCreateDto dto) {
		Customer c = new Customer();
		c.setName(dto.getName());
		c.setNida(dto.getNida());
		c.setContact(dto.getContact());
		c.setGender(dto.getGender());
		c.setIdType(dto.getNida() != null ? "NIDA" : "NONE");
		return c;
	}

	public static CustomerResponseDto toDto(Customer c) {
		CustomerResponseDto dto = new CustomerResponseDto();
		dto.setId(c.getId());
		dto.setName(c.getName());
		dto.setNida(c.getNida());
		dto.setContact(c.getContact());
		dto.setGender(c.getGender());
		return dto;
	}
}
