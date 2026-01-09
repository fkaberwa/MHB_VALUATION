package com.example.mhb.controller;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.dto.valuation.ValuationResponseDto;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.mapper.ValuationMapper;
import com.example.mhb.service.ValuationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/valuations")
public class ValuationController {

    private final ValuationService valuationService;

    public ValuationController(ValuationService valuationService) {
        this.valuationService = valuationService;
    }

    /* ================= CREATE (ADMIN) ================= */
    @PostMapping("/create")
    public ResponseEntity<ValuationResponseDto> create(
            @RequestBody ValuationCreateDto dto
    ) {
        ValuationForm saved = valuationService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ValuationMapper.toDto(saved));
    }

    /* ================= READ ALL (ADMIN + APPROVER) ================= */
    @GetMapping("/list")
public Map<String, Object> findAll() {
    List<ValuationResponseDto> list = valuationService.findAll()
        .stream()
        .map(ValuationMapper::toDto)
        .toList();

    return Map.of(
        "data", list,
        "total", list.size()
    );
}

    /* ================= READ ONE (ADMIN + APPROVER) ================= */
    @GetMapping("/view/{id}")
    public ValuationResponseDto findById(@PathVariable Long id) {
        return ValuationMapper.toDto(
                valuationService.findById(id)
        );
    }

   
    /* ================= DELETE (ADMIN) ================= */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        valuationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /* ================= PENDING (APPROVER) ================= */
    @GetMapping("/pending")
    public List<ValuationResponseDto> pendingApprovals() {
        return valuationService.findByStatus("PENDING")
                .stream()
                .map(ValuationMapper::toDto)
                .toList();
    }

    /* ================= APPROVE (APPROVER) ================= */
    @PutMapping("/approve/{id}")
    public ValuationResponseDto approve(@PathVariable Long id) {
        return ValuationMapper.toDto(
                valuationService.updateStatus(id, "APPROVED")
        );
    }

    /* ================= REJECT (APPROVER) ================= */
    @PutMapping("/reject/{id}")
    public ValuationResponseDto reject(@PathVariable Long id) {
        return ValuationMapper.toDto(
                valuationService.updateStatus(id, "REJECTED")
        );
    }
}
