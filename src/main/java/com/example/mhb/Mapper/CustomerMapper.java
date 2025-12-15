package com.example.mhb.mapper;

import com.example.mhb.dto.customer.CustomerCreateDto;
import com.example.mhb.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerCreateDto dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setNida(dto.getNida());
        c.setContact(dto.getContact());
        c.setGender(dto.getGender());
        c.setIdType(dto.getNida() != null && !dto.getNida().isBlank() ? "NIDA" : "NONE");
        return c;
    }

    public static CustomerCreateDto toDto(Customer c) {
        CustomerCreateDto dto = new CustomerCreateDto();
        dto.setName(c.getName());
        dto.setNida(c.getNida());
        dto.setContact(c.getContact());
        dto.setGender(c.getGender());
        return dto;
    }
}
