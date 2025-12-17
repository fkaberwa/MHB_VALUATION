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

    /* ================= CREATE ================= */
    @PostMapping("/create")
    public ResponseEntity<ValuationResponseDto> create(
            @RequestBody ValuationCreateDto dto
    ) {
        ValuationForm saved = valuationService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ValuationMapper.toDto(saved));
    }

    /* ================= READ ================= */
    @GetMapping("/list")
    public List<ValuationResponseDto> findAll() {
        return valuationService.findAll()
                .stream()
                .map(ValuationMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ValuationResponseDto findById(@PathVariable Long id) {
        return ValuationMapper.toDto(
                valuationService.findById(id)
        );
    }

    /* ================= PARTIAL UPDATE ================= */
    @PatchMapping("/update/{id}")
    public ValuationResponseDto partialUpdate(
            @PathVariable Long id,
            @RequestBody ValuationCreateDto dto
    ) {
        return ValuationMapper.toDto(
                valuationService.partialUpdate(id, dto)
        );
    }

    /* ================= DELETE ================= */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        valuationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
