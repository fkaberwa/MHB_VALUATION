package com.example.mhb.controller;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.entity.Customer;
import com.example.mhb.repository.ValuationRepository;
import com.example.mhb.repository.CustomerRepository;

@RestController
@RequestMapping("/api/valuation-forms")
public class ValuationController {

    private final ValuationRepository repo;
    private final CustomerRepository customerRepo;

    public ValuationController(ValuationRepository repo, CustomerRepository customerRepo) {
        this.repo = repo;
        this.customerRepo = customerRepo;
    }

    // CREATE
    @PostMapping
    public ValuationForm create(
            @RequestBody @NonNull ValuationForm form,
            @RequestParam(required = true) @NonNull Long customerId) {

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        form.setCustomer(customer);
        form.setOwnerName(customer.getName());
        return repo.save(form);
    }

    // READ ALL
    @GetMapping
    public List<ValuationForm> getAll() {
        return repo.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ValuationForm getById(@PathVariable @NonNull Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Valuation form not found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ValuationForm update(@PathVariable @NonNull Long id, @RequestBody @NonNull ValuationForm updatedForm) {
        ValuationForm form = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Valuation form not found"));

        form.setPropertyType(updatedForm.getPropertyType());
        form.setOwnerName(updatedForm.getOwnerName());
        form.setLocation(updatedForm.getLocation());
        form.setEstimatedValue(updatedForm.getEstimatedValue());
        form.setStatus(updatedForm.getStatus());

        return repo.save(form);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable @NonNull Long id) {
        repo.deleteById(id);
        return Map.of("message", "Valuation form deleted successfully");
    }

    // SEARCH
    @GetMapping("/search")
    public List<ValuationForm> searchByOwnerName(@RequestParam @NonNull String q) {
        return repo.findByOwnerNameContainingIgnoreCase(q);
    }
}
