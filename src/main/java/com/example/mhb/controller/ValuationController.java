package com.example.mhb.controller;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.dto.valuation.ValuationResponseDto;
import com.example.mhb.service.ValuationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/valuations")
public class ValuationController {

    private final ValuationService service;

    public ValuationController(ValuationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ValuationResponseDto> create(@RequestBody ValuationCreateDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }
}
