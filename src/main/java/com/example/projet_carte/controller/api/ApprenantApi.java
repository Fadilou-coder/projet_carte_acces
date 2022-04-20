package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.CommentaireDto;
import com.example.projet_carte.dto.ReferentielDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@Api("apprenant")
public interface ApprenantApi {

    @GetMapping("/apprenants")
    List<ApprenantDto> findAll();

    @GetMapping("/apprenants/{id}")
    ApprenantDto findById(@PathVariable Long id);

    @GetMapping("/referentiel/{id}/apprenants")
    List<ApprenantDto> findByref(@PathVariable Long id);

    @GetMapping("/promo/{id}/apprenants")
    List<ApprenantDto> findBypromo(@PathVariable Long id);

    @GetMapping("/referentiel/{idRef}/promo/{idPr}/apprenants")
    List<ApprenantDto> findByRefByPromo(@PathVariable Long idRef, @PathVariable Long idPr);

    @PostMapping("/apprenants/create")
    ApprenantDto save(
            @RequestParam("prenom") String prenom ,
            @RequestParam("nom") String nom,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("adresse") String adresse,
            @RequestParam("typePiece") String typePiece,
            @RequestParam("sexe") String sexe,
            @RequestParam("numPiece") String numPiece,
            @RequestParam("referentiel") String referentiel,
            @RequestParam("promo") String promo,
            @RequestParam("dateNaissance") String dateNaissance,
            @RequestParam("lieuNaissance") String lieuNaissance,
            @RequestParam("numTuteur") String numTuteur,
            @RequestParam("avatar") MultipartFile avatar
    ) throws IOException;

    @PostMapping("/apprenants/saveAsCsv")
    List<ApprenantDto> saveFromCsv(@RequestParam("file") MultipartFile file);

    @PostMapping("/apprenants/sendMail")
    void sendCarte(
            @RequestParam("prenom") String prenom ,
            @RequestParam("nom") String nom,
            @RequestParam("email") String email ,
            @RequestParam("file") MultipartFile file
    ) throws IOException;

    @PostMapping("/apprenants/comments")
    CommentaireDto addComment(@RequestBody CommentaireDto commentaire);

    @GetMapping("/apprenants/{id}/comments")
    List<CommentaireDto> findcommentsByApp(@PathVariable Long id);

    @PutMapping("/apprenants/{id}")
    ApprenantDto put(@PathVariable Long id,
                     @RequestParam("prenom") String prenom ,
                     @RequestParam("nom") String nom,
                     @RequestParam("email") String email,
                     @RequestParam("phone") String phone,
                     @RequestParam("adresse") String adresse,
                     @RequestParam("numPiece") String numPiece,
                     @RequestParam("typePiece") String typePiece,
                     @RequestParam("dateNaissance") String dateNaissance,
                     @RequestParam("lieuNaissance") String lieuNaissance,
                     @RequestParam("numTuteur") String numTuteur,
                     @RequestParam("avatar") MultipartFile avatar
    ) throws IOException;

    @PutMapping("/apprenants/field/{id}")
    ApprenantDto putFieldApp(@PathVariable Long id, @RequestBody ApprenantDto apprenantDto);

    @PutMapping("/apprenants/image/{id}")
    ApprenantDto putImageApp(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException;

    @GetMapping("/apprenants/{id}/nbrAbs/{dateDebut}/{dateFin}")
    Integer findNbrAbscences(@PathVariable("id") Long id, @PathVariable("dateDebut") String dateDebut, @PathVariable("dateFin") String dateFin);

    @GetMapping("/apprenants/{id}/nbrRetard/{dateDebut}/{dateFin}")
    Integer findNbrRetard(@PathVariable("id") Long id, @PathVariable("dateDebut") String dateDebut, @PathVariable("dateFin") String dateFin);

    @GetMapping("/promos/{id}/nbrAbsAllApp/{dateDebut}/{dateFin}")
    Integer findNbrAbscencesAllApp(@PathVariable("id") Long id, @PathVariable("dateDebut") String dateDebut, @PathVariable("dateFin") String dateFin);

    @GetMapping("/promos/{id}/nbrRetardAllApp/{dateDebut}/{dateFin}")
    Integer findNbrRetardAllApp(@PathVariable("id") Long id, @PathVariable("dateDebut") String dateDebut, @PathVariable("dateFin") String dateFin);

    @RequestMapping(value = "/apprenants/{id}", method = DELETE)
    @ResponseBody
    void delete(@PathVariable Long id);

}
