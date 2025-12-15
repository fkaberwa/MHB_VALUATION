package com.example.mhb.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.entity.Customer;
import com.example.mhb.dto.customer.CustomerCreateDto;
import com.example.mhb.mapper.CustomerMapper;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            @Valid @RequestBody CustomerCreateDto dto) {

        if (dto.getNida() != null && !dto.getNida().isBlank()
                && !repo.findByNida(dto.getNida()).isEmpty()) {
            throw new RuntimeException("Customer with this NIDA already exists");
        }

        if (dto.getContact() != null && !dto.getContact().isBlank()
                && !repo.findByContact(dto.getContact()).isEmpty()) {
            throw new RuntimeException("Customer with this contact already exists");
        }

        Customer saved = repo.save(CustomerMapper.toEntity(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "message", "Customer created successfully",
                        "customer", CustomerMapper.toDto(saved)
                )
        );
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable long id) {
        Customer customer = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        return ResponseEntity.ok(customer);
    }
}
