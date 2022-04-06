package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.VisiteurApi;
import com.example.projet_carte.dto.VisiteurDto;
import com.example.projet_carte.service.VisiteurService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class VisiteurController implements VisiteurApi {

    VisiteurService visiteurService;

    @Override
    public List<VisiteurDto> findAll() {
        return visiteurService.findAll();
    }

    @Override
    public VisiteurDto putVisiteur(VisiteurDto visiteurDto, Long id) {
        return visiteurService.putVisiteur(visiteurDto, id);
    }
}
