package com.example.projet_carte.service;

import com.example.projet_carte.dto.ReferentielDto;

import java.util.List;

public interface ReferentielService {

    List<ReferentielDto> findAll();
    ReferentielDto save(ReferentielDto referentielDto);
    ReferentielDto putPromo(ReferentielDto referentielDto, Long id);
}
