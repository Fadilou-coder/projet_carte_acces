package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.ApprenantApi;
import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.ReferentielDto;
import com.example.projet_carte.service.ApprenantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ApprenantController implements ApprenantApi {

    ApprenantService apprenantService;

    @Override
    public List<ApprenantDto> findAll() {
        return apprenantService.findAll();
    }

    @Override
    public ApprenantDto findById(Long id) {
        return apprenantService.findById(id);
    }

    @Override
    public ApprenantDto save(String prenom, String nom, String email, String phone, String adresse, String cni, String referentiel,
                             String dateNaissance, String lieuNaissance, String numTuteur, MultipartFile avatar) throws IOException {
        return  apprenantService.save(prenom, nom, email, phone, adresse, cni, referentiel,
                dateNaissance, lieuNaissance, numTuteur, avatar);
    }

    @Override
    public ApprenantDto put(Long id, String prenom, String nom, String email, String phone, String adresse, String cni, String referentiel,
                            String dateNaissance, String lieuNaissance, String numTuteur, MultipartFile avatar) throws IOException {
        return apprenantService.put(id, prenom, nom, email, phone, adresse, cni, referentiel,
                dateNaissance, lieuNaissance, numTuteur, avatar);
    }

    @Override
    public void delete(Long id) {
        apprenantService.delete(id);
    }
}
