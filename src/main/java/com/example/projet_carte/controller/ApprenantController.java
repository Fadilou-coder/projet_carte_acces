package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.ApprenantApi;
import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.CommentaireDto;
import com.example.projet_carte.dto.ReferentielDto;
import com.example.projet_carte.service.ApprenantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<ApprenantDto> findByref(Long id) {
        return apprenantService.findByref(id);
    }

    @Override
    public List<ApprenantDto> findBypromo(Long id) {
        return apprenantService.findBypromo(id);
    }

    @Override
    public List<ApprenantDto> findByRefByPromo(Long idRef, Long idPr) {
        return apprenantService.findByRefByPromo(idRef, idPr);
    }

    @Override
    public ApprenantDto save(String prenom, String nom, String email, String phone, String adresse, String typePiece, String numPiece, String referentiel, String promo,
                             String dateNaissance, String lieuNaissance, String numTuteur, MultipartFile avatar) throws IOException {
        return  apprenantService.save(prenom, nom, email, phone, adresse, typePiece, numPiece, referentiel, promo,
                dateNaissance, lieuNaissance, numTuteur, avatar);
    }

    @Override
    public List<ApprenantDto> saveFromCsv(MultipartFile file) {
        return apprenantService.saveFromCsv(file);
    }

    @Override
    public void sendCarte(String prenom, String nom, String email, MultipartFile file) throws IOException {
        apprenantService.sendCarte(prenom, nom, email, file);
    }

    @Override
    public CommentaireDto addComment(CommentaireDto commentaire) {
        return apprenantService.addComment(commentaire);
    }

    @Override
    public List<CommentaireDto> findcommentsByApp(Long id) {
        return apprenantService.commentsApp(id);
    }

    @Override
    public ApprenantDto put(Long id, String prenom, String nom, String email, String phone, String adresse, String cni,
                            String dateNaissance, String lieuNaissance, String numTuteur, MultipartFile avatar) throws IOException {
        return apprenantService.put(id, prenom, nom, email, phone, adresse, cni,
                dateNaissance, lieuNaissance, numTuteur, avatar);
    }

    @Override
    public void delete(Long id) {
        apprenantService.delete(id);
    }
}
