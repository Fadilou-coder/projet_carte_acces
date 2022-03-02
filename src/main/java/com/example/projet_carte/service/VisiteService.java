package com.example.projet_carte.service;

import com.example.projet_carte.dto.VisiteDto;

import java.util.List;

public interface VisiteService {

    VisiteDto save(VisiteDto visiteDto);
    List<VisiteDto> findAll();
    List<VisiteDto> findByDate(String date);
    List<VisiteDto> findVisitesApp();
    List<VisiteDto> findVisitesVisiteur();
}
