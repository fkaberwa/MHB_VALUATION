package com.example.mhb.Mapper;

import com.example.mhb.dto.CustomerCreationDto;
import com.example.mhb.entity.Customer;
import org.springframework.lang.NonNull;

public class CustomerMapper {

    @NonNull
    public static Customer toEntity(@NonNull CustomerCreationDto dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setNida(dto.getNida());
        c.setContact(dto.getContact());
        c.setGender(dto.getGender());

        // Auto-assign ID TYPE
        if (dto.getNida() != null && !dto.getNida().isEmpty()) {
            c.setIdType("NIDA");
        } else {
            c.setIdType("Blank");
        }

        return c;
    }

    @NonNull
    public static CustomerCreationDto toDto(@NonNull Customer entity) {
        CustomerCreationDto dto = new CustomerCreationDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNida(entity.getNida());
        dto.setContact(entity.getContact());
        dto.setGender(entity.getGender());

        // Always show valid idType
        dto.setIdType(entity.getIdType() != null ? entity.getIdType() : "Blank");

        // Never show null version
        dto.setVersion(entity.getVersion() != null ? entity.getVersion().toString() : "Blank");

        return dto;
    }
}
