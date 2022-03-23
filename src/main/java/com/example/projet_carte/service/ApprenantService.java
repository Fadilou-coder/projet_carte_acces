package com.example.projet_carte.service;

import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.ReferentielDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ApprenantService {

    List<ApprenantDto> findAll();

    List<ApprenantDto> findByref(Long id);

    List<ApprenantDto> findBypromo(Long id);

    List<ApprenantDto> findByRefByPromo(Long idRef, Long idPr);

    ApprenantDto save(
            String prenom,
            String nom,
            String email,
            String phone,
            String adresse,
            String cni,
            String referentiel,
            String promo,
            String dateNaissance,
            String lieuNaissance,
            String numTuteur,
            MultipartFile avatar

    ) throws IOException;

    void sendCarte(String prenom, String nom, String email, MultipartFile file) throws IOException;

      ApprenantDto findById(Long id);

    ApprenantDto put(Long id,
                     String prenom,
                     String nom,
                     String email,
                     String phone,
                     String adresse,
                     String cni,
                     String dateNaissance,
                     String lieuNaissance,
                     String numTuteur,
                     MultipartFile avatar) throws IOException;

    void delete(Long id);
}
