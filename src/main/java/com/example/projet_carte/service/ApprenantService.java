package com.example.projet_carte.service;

import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.CommentaireDto;
import com.example.projet_carte.dto.ReferentielDto;
import com.example.projet_carte.model.Promo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ApprenantService {

    List<ApprenantDto> findAll();

    List<ApprenantDto> findSanctionner();

    List<ApprenantDto> findByref(Long id);

    List<ApprenantDto> findBypromo(Long id);

    List<ApprenantDto> findByRefByPromo(Long idRef, Long idPr);

    ApprenantDto save(
            String prenom,
            String nom,
            String email,
            String phone,
            String adresse,
            String typePiece,
            String sexe,
            String numPiece,
            String referentiel,
            String promo,
            String dateNaissance,
            String lieuNaissance,
            String numTuteur,
            MultipartFile avatar

    ) throws IOException;

    List<ApprenantDto> saveFromCsv(MultipartFile file);

    void sendCarte(String prenom, String nom, String email, MultipartFile file) throws IOException;

      ApprenantDto findById(Long id);
      ApprenantDto findByCode(String code);

    ApprenantDto put(Long id,
                     String prenom,
                     String nom,
                     String email,
                     String phone,
                     String adresse,
                     String typePeice,
                     String numPiece,
                     String dateNaissance,
                     String lieuNaissance,
                     String numTuteur,
                     MultipartFile avatar) throws IOException;

    ApprenantDto putFieldApp(Long id, ApprenantDto apprenantDto);
    ApprenantDto putImageApp(Long id, MultipartFile file) throws IOException;

    Integer findNbrAbscences(Long id, LocalDate dateDebut, LocalDate dateFin);
    Integer findNbrRetard(Long id, LocalDate dateDebut, LocalDate dateFin);

    Integer findNbrAbscencesAllApp(Long id, LocalDate dateDebut, LocalDate dateFin);
    Integer findNbrRetardAllApp(Long id, LocalDate dateDebut, LocalDate dateFin);


    void delete(Long id);

    CommentaireDto addComment(CommentaireDto commentaire);

    List<CommentaireDto> commentsApp(Long id);
}
