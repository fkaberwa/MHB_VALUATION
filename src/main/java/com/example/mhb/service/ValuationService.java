package com.example.mhb.service;

import com.example.mhb.dto.valuation.ValuationCreateDto;
import com.example.mhb.entity.ValuationForm;
import com.example.mhb.mapper.ValuationMapper;
import com.example.mhb.repository.ValuationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValuationService {

    private final ValuationRepository valuationRepository;

    public ValuationService(ValuationRepository valuationRepository) {
        this.valuationRepository = valuationRepository;
    }

    public ValuationForm create(ValuationCreateDto dto) {
        ValuationForm v = ValuationMapper.toEntity(dto);
        return valuationRepository.save(v);
    }

    public List<ValuationForm> findAll() {
        return valuationRepository.findAll();
    }

    public ValuationForm findById(Long id) {
        return valuationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public ValuationForm partialUpdate(Long id, ValuationCreateDto dto) {
        ValuationForm v = findById(id);
        ValuationMapper.partialUpdate(v, dto);
        return valuationRepository.save(v);
    }

    public void delete(Long id) {
        valuationRepository.deleteById(id);
    }
}
