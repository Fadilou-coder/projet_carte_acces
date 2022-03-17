package com.example.projet_carte.service.impl;


import com.example.projet_carte.dto.ReferentielDto;
import com.example.projet_carte.repository.ReferentielRepository;
import com.example.projet_carte.service.ReferentielService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ReferentielServiceImpl implements ReferentielService {
   private ReferentielRepository referentielRepository;

    @Override
    public List<ReferentielDto> findAll() {
        return referentielRepository.findAll().stream().map(ReferentielDto::fromEntity).collect(Collectors.toList());
    }
}
