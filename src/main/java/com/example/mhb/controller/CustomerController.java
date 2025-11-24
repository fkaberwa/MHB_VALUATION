package com.example.mhb.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.entity.Customer;
import com.example.mhb.dto.CustomerCreationDto;
import com.example.mhb.Mapper.CustomerMapper;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public CustomerCreationDto create(@RequestBody @Valid @NonNull CustomerCreationDto dto) {

        // ❗ Prevent duplicate NIDA
        if (dto.getNida() != null && !dto.getNida().isEmpty()) {
            if (!repo.findByNida(dto.getNida()).isEmpty()) {
                throw new RuntimeException("Customer with this NIDA already exists");
            }
        }

        // ❗ Prevent duplicate Contact
        if (!repo.findByContact(dto.getContact()).isEmpty()) {
            throw new RuntimeException("Customer with this contact already exists");
        }

        Customer entity = CustomerMapper.toEntity(dto);
        Customer saved = repo.save(entity);

        return CustomerMapper.toDto(saved);
    }

    @GetMapping
    public List<CustomerCreationDto> all() {
        return repo.findAll().stream()
                .map(CustomerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<CustomerCreationDto> search(@RequestParam String q) {
        return repo.findByNameContainingIgnoreCase(q).stream()
                .map(CustomerMapper::toDto)
                .collect(Collectors.toList());
    }
}
