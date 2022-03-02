package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.VisiteApi;
import com.example.projet_carte.dto.VisiteDto;
import com.example.projet_carte.service.VisiteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class VisiteController implements VisiteApi {

    VisiteService visiteService;

    @Override
    public VisiteDto save(VisiteDto visiteDto) {
        return visiteService.save(visiteDto);
    }

    @Override
    public List<VisiteDto> findAll() {
        return visiteService.findAll();
    }

    @Override
    public List<VisiteDto> findByDate(String date) {
        return visiteService.findByDate(date);
    }

    @Override
    public List<VisiteDto> findVisitesApp() {
        return null;
    }

    @Override
    public List<VisiteDto> findVisitesVisiteur() {
        return visiteService.findVisitesVisiteur();
    }
}
