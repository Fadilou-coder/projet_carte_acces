package com.example.projet_carte.service;

import com.example.projet_carte.dto.VisiteurDto;

import java.util.List;

public interface VisiteurService {
    List<VisiteurDto> findAll();
    VisiteurDto putVisiteur(VisiteurDto visiteurDto, Long id);
}
