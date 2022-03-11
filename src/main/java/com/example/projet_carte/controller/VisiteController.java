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
    public VisiteDto saveVisiteur(VisiteDto visiteDto) {
        return visiteService.saveVisiteVisiteur(visiteDto);
    }

    @Override
    public VisiteDto saveApprenant(VisiteDto visiteDto) {
        return visiteService.saveVisiteApprenant(visiteDto);
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
    public List<VisiteDto> findByDateByVisiteur(String date, String vst) {
        return visiteService.findByDateByVisiteur(date, vst);
    }

    @Override
    public List<VisiteDto> findVisitesApp() {
        return visiteService.findVisitesApp();
    }

    @Override
    public List<VisiteDto> findVisitesVisiteur() {
        return visiteService.findVisitesVisiteur();
    }

    @Override
    public VisiteDto SortieApprenant(VisiteDto visiteDto) {
        return visiteService.SortieApprenant(visiteDto);
    }

    @Override
    public VisiteDto SortieVisiteur(VisiteDto visiteDto) {
        return visiteService.SortieVisiteur(visiteDto);
    }
}
