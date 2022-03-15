package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.VisiteurDto;
import com.example.projet_carte.repository.VisiteurRepository;
import com.example.projet_carte.service.VisiteurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class VisiteurServiceImpl implements VisiteurService {

    VisiteurRepository visiteurRepository;

    @Override
    public List<VisiteurDto> findAll() {
        return visiteurRepository.findAll().stream().map(VisiteurDto::fromEntity).collect(Collectors.toList());
    }
}
