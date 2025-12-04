package com.example.mhb.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import com.example.mhb.repository.CustomerRepository;
import com.example.mhb.entity.Customer;
import com.example.mhb.dto.CustomerCreationDto;
import com.example.mhb.Mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository repo;
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    // Removes all @NonNull warnings
    private @NonNull Customer getCustomerOrThrow(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CustomerCreationDto dto) {
        log.info("Creating customer - name={}, nida={}, contact={}", dto.getName(), dto.getNida(), dto.getContact());

        // FIXED: was .isPresent() → now .isEmpty()
        if (dto.getNida() != null && !dto.getNida().isBlank() && !repo.findByNida(dto.getNida()).isEmpty()) {
            throw new RuntimeException("Customer with this NIDA already exists.");
        }
        if (dto.getContact() != null && !dto.getContact().isBlank() && !repo.findByContact(dto.getContact()).isEmpty()) {
            throw new RuntimeException("Customer with this contact already exists.");
        }

        Customer entity = CustomerMapper.toEntity(dto);
        Customer saved = repo.save(entity);

        Map<String, Object> response = Map.of(
            "message", "Customer created successfully",
            "customer", CustomerMapper.toDto(saved)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> all() {
        List<CustomerCreationDto> customers = repo.findAll().stream()
                .map(CustomerMapper::toDto)
                .toList();

        Map<String, Object> response = Map.of(
            "message", "Customers retrieved successfully",
            "count", customers.size(),
            "customers", customers
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable long id) {
        Customer customer = getCustomerOrThrow(id);
        Map<String, Object> response = Map.of(
            "message", "Customer found successfully",
            "customer", CustomerMapper.toDto(customer)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam String q) {
        List<CustomerCreationDto> results = repo.findByNameContainingIgnoreCase(q).stream()
                .map(CustomerMapper::toDto)
                .toList();

        String message = results.isEmpty()
                ? "No customers found matching '" + q + "'"
                : "Found " + results.size() + " customer(s) matching '" + q + "'";

        Map<String, Object> response = Map.of("message", message, "count", results.size(), "customers", results);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable long id, @Valid @RequestBody CustomerCreationDto dto) {
        Customer customer = getCustomerOrThrow(id);

        if (dto.getNida() != null && !dto.getNida().isBlank() && !dto.getNida().equals(customer.getNida())
                && !repo.findByNida(dto.getNida()).isEmpty()) {
            throw new RuntimeException("Customer with this NIDA already exists.");
        }
        if (dto.getContact() != null && !dto.getContact().isBlank() && !dto.getContact().equals(customer.getContact())
                && !repo.findByContact(dto.getContact()).isEmpty()) {
            throw new RuntimeException("Customer with this contact already exists.");
        }

        customer.setName(dto.getName());
        customer.setNida(dto.getNida());
        customer.setContact(dto.getContact());
        customer.setGender (dto.getGender());
        customer.setIdType(dto.getNida() != null && !dto.getNida().isBlank() ? "NIDA" : "Blank");

        Customer updated = repo.save(customer);

        Map<String, Object> response = Map.of(
            "message", "Customer updated successfully",
            "customer", CustomerMapper.toDto(updated)
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> partialUpdate(@PathVariable long id, @RequestBody Map<String, Object> updates) {
        Customer customer = getCustomerOrThrow(id);

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> { if (value != null) customer.setName((String) value); }
                case "nida" -> {
                    String newNida = value == null ? null : value.toString().trim();
                    if (newNida != null && !newNida.isBlank() && !newNida.equals(customer.getNida())
                            && !repo.findByNida(newNida).isEmpty()) {
                        throw new RuntimeException("Customer with this NIDA already exists.");
                    }
                    customer.setNida(newNida);
                    customer.setIdType(newNida != null && !newNida.isBlank() ? "NIDA" : "Blank");
                }
                case "contact" -> {
                    String newContact = value == null ? null : value.toString().trim();
                    if (newContact != null && !newContact.isBlank() && !newContact.equals(customer.getContact())
                            && !repo.findByContact(newContact).isEmpty()) {
                        throw new RuntimeException("Customer with this contact already exists.");
                    }
                    customer.setContact(newContact);
                }
                case "gender" -> { if (value != null) customer.setGender((String) value); }
                default -> throw new RuntimeException("Invalid field: " + key);
            }
        });

        Customer updated = repo.save(customer);
        Map<String, Object> response = Map.of(
            "message", "Customer partially updated successfully",
            "customer", CustomerMapper.toDto(updated)
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Customer deleted successfully"));
    }
}