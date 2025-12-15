package com.example.mhb.service;

import com.example.mhb.dto.customer.CustomerCreateDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.Mapper.CustomerMapper;
import com.example.mhb.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(CustomerCreateDto dto) {

        if (dto.getNida() != null && repository.existsByNida(dto.getNida())) {
            throw new RuntimeException("Customer with this NIDA already exists");
        }

        if (dto.getContact() != null && repository.existsByContact(dto.getContact())) {
            throw new RuntimeException("Customer with this contact already exists");
        }

        Customer customer = CustomerMapper.toEntity(dto);
        return repository.save(customer);
    }
}
