package com.example.mhb.controller;

import com.example.mhb.Mapper.ValuationMapper;
import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.dto.valuation.ValuationResponseDto;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.service.ValuationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/valuations")
public class ValuationController {

    private final ValuationService valuationService;

    public ValuationController(ValuationService valuationService) {
        this.valuationService = valuationService;
    }

    @PostMapping
    public ResponseEntity<ValuationResponseDto> create(
            @RequestBody ValuationCreateDto dto
    ) {
        ValuationForm saved = valuationService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ValuationMapper.toDto(saved));
    }
}
