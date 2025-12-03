package com.example.mhb.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Objects;

import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.entity.Customer;
import com.example.mhb.dto.CustomerCreationDto;
import com.example.mhb.Mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository repo;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public CustomerCreationDto create(@Valid @RequestBody CustomerCreationDto dto) {

        if (dto.getNida() != null && !dto.getNida().isBlank()) {
            if (!repo.findByNida(dto.getNida()).isEmpty()) {
                throw new RuntimeException("Customer with this NIDA already exists.");
            }
        }

        if (dto.getContact() != null && !dto.getContact().isBlank()) {
            if (!repo.findByContact(dto.getContact()).isEmpty()) {
                throw new RuntimeException("Customer with this contact already exists.");
            }
        }

        Customer entity = CustomerMapper.toEntity(dto);
        Customer saved = Objects.requireNonNull(repo.save(entity), "Failed to save customer");
        return CustomerMapper.toDto(saved);
    }

    @GetMapping
    public List<CustomerCreationDto> all() {
        return repo.findAll()
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerCreationDto getById(@PathVariable long id) {
        Customer customer = Objects.requireNonNull(
            repo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id)),
            "Customer should not be null"
        );
        return CustomerMapper.toDto(customer);
    }

    @GetMapping("/search")
    public List<CustomerCreationDto> search(@RequestParam String q) {
        return repo.findByNameContainingIgnoreCase(q)
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public CustomerCreationDto update(@PathVariable long id, @Valid @RequestBody CustomerCreationDto dto) {
        try {
            Customer customer = Objects.requireNonNull(
                repo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id)),
                "Customer should not be null"
            );

            // Check NIDA uniqueness if it's being changed
            if (dto.getNida() != null && !dto.getNida().isBlank() && !dto.getNida().equals(customer.getNida())) {
                if (!repo.findByNida(dto.getNida()).isEmpty()) {
                    throw new RuntimeException("Customer with this NIDA already exists.");
                }
            }

            // Check Contact uniqueness if it's being changed
            if (dto.getContact() != null && !dto.getContact().isBlank() && !dto.getContact().equals(customer.getContact())) {
                if (!repo.findByContact(dto.getContact()).isEmpty()) {
                    throw new RuntimeException("Customer with this contact already exists.");
                }
            }

            customer.setName(dto.getName());
            customer.setNida(dto.getNida());
            customer.setContact(dto.getContact());
            customer.setGender(dto.getGender());
            customer.setIdType(dto.getNida() != null && !dto.getNida().isBlank() ? "NIDA" : "Blank");

            Customer updated = Objects.requireNonNull(repo.save(customer), "Failed to update customer");
            return CustomerMapper.toDto(updated);
        } catch (Exception ex) {
            log.error("Failed to update customer id={} : {}", id, ex.getMessage(), ex);
            // Re-throw as RuntimeException so GlobalExceptionHandler returns a client-friendly response
            throw new RuntimeException("Update failed: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        repo.deleteById(id);
    }
}
