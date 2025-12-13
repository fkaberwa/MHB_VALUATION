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
        c.setIdType(dto.getNida() != null && !dto.getNida().isBlank() ? "NIDA" : "Blank");
        return c;
    }

    public static CustomerCreateDto toDto(Customer entity) {
        CustomerCreateDto dto = new CustomerCreateDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNida(entity.getNida());
        dto.setContact(entity.getContact());
        dto.setGender(entity.getGender());
        dto.setIdType(entity.getIdType() != null ? entity.getIdType() : "Blank");
        dto.setVersion(entity.getVersion() != null ? entity.getVersion().toString() : "0");
        return dto;
    }
}
