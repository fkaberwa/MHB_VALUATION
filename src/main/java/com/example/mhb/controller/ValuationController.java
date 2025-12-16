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

@RestController
@RequestMapping("/api/valuations")
public class ValuationController {

    private final ValuationService valuationService;

    public ValuationController(ValuationService valuationService) {
        this.valuationService = valuationService;
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<ValuationResponseDto> create(
            @RequestBody ValuationCreateDto dto
    ) {
        ValuationForm saved = valuationService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ValuationMapper.toDto(saved));
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<ValuationResponseDto>> getAll() {
        List<ValuationResponseDto> list = valuationService.findAll()
                .stream()
                .map(ValuationMapper::toDto)
                .toList();

        return ResponseEntity.ok(list);
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<ValuationResponseDto> getById(
            @PathVariable Long id
    ) {
        ValuationForm valuation = valuationService.findById(id);
        return ResponseEntity.ok(ValuationMapper.toDto(valuation));
    }

    // ================= PARTIAL UPDATE =================
    @PatchMapping("/{id}")
    public ResponseEntity<ValuationResponseDto> partialUpdate(
            @PathVariable Long id,
            @RequestBody ValuationCreateDto dto
    ) {
        ValuationForm updated = valuationService.partialUpdate(id, dto);
        return ResponseEntity.ok(ValuationMapper.toDto(updated));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        valuationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
