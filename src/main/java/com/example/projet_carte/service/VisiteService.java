package com.example.projet_carte.service;

import com.example.projet_carte.dto.VisiteDto;

import java.util.List;

public interface VisiteService {

    VisiteDto saveVisiteVisiteur(VisiteDto visiteDto);
    VisiteDto saveVisiteApprenant(VisiteDto visiteDto);
    List<VisiteDto> findAll();
    List<VisiteDto> findByDate(String date);
    List<VisiteDto> findVisitesApp();
    List<VisiteDto> findVisitesVisiteur();
    VisiteDto SortieApprenant(VisiteDto visiteDto);
    VisiteDto SortieVisiteur(VisiteDto visiteDto);

}
