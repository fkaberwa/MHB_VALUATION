package com.example.mhb.controller;

import com.example.mhb.dto.customer.CustomerCreateDto;
import com.example.mhb.dto.customer.CustomerResponseDto;
import com.example.mhb.entity.Customer;
import com.example.mhb.Mapper.CustomerMapper;
import com.example.mhb.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(
            @RequestBody CustomerCreateDto dto
    ) {
        if (dto.getNida() != null && repo.existsByNida(dto.getNida())) {
            throw new RuntimeException("NIDA already exists");
        }

        if (dto.getContact() != null && repo.existsByContact(dto.getContact())) {
            throw new RuntimeException("Contact already exists");
        }

        Customer saved = repo.save(CustomerMapper.toEntity(dto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CustomerMapper.toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> all() {
        List<CustomerResponseDto> list = repo.findAll()
                .stream()
                .map(CustomerMapper::toDto)
                .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> byId(@PathVariable Long id) {
        Customer c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return ResponseEntity.ok(CustomerMapper.toDto(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
