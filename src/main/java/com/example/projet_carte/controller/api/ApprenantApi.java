package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.ReferentielDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@Api("apprenant")
public interface ApprenantApi {

    @GetMapping("/apprenants")
    List<ApprenantDto> findAll();

    @GetMapping("/apprenants/{id}")
    ApprenantDto findById(@PathVariable Long id);

    @PostMapping("/apprenants/create")
    ApprenantDto save(
            @RequestParam("prenom") String prenom ,
            @RequestParam("nom") String nom,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("adresse") String adresse,
            @RequestParam("cni") String cni,
            @RequestParam("referentiel") String referentiel,
            @RequestParam("dateNaissance") String dateNaissance,
            @RequestParam("lieuNaissance") String lieuNaissance,
            @RequestParam("numTuteur") String numTuteur,
            @RequestParam("avatar") MultipartFile avatar
    ) throws IOException;

    @PutMapping("/apprenants/{id}")
    ApprenantDto put(@PathVariable Long id,
                     @RequestParam("prenom") String prenom ,
                     @RequestParam("nom") String nom,
                     @RequestParam("email") String email,
                     @RequestParam("phone") String phone,
                     @RequestParam("adresse") String adresse,
                     @RequestParam("cni") String cni,
                     @RequestParam("dateNaissance") String dateNaissance,
                     @RequestParam("lieuNaissance") String lieuNaissance,
                     @RequestParam("numTuteur") String numTuteur,
                     @RequestParam("avatar") MultipartFile avatar
    ) throws IOException;


    @RequestMapping(value = "/apprenants/{id}", method = DELETE)
    @ResponseBody
    void delete(@PathVariable Long id);

}
