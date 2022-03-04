package com.example.projet_carte.validator;

import com.example.projet_carte.dto.ApprenantDto;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonneValidator {

    public static List<String> Appvalidate(ApprenantDto userDto) {
        return getStrings(userDto == null, userDto.getPrenom(),
                userDto.getNom(), userDto.getEmail(), userDto.getPhone(), userDto.getAddresse(), userDto.getCni(),
                userDto.getCode(), userDto.getReferentiel(), userDto.getDateNaissance(), userDto.getLieuNaissance(), userDto.getNumTuteur());
    }

    private static List<String> getStrings(boolean b, String prenom, String nom, String email, String phone, String addresse, String cni,
                                           String code, String referentiel, LocalDate dateNaissance, String lieuNaissance, String numTuteur) {
        List<String> errors = new ArrayList<>();

        if (b) {
            errors.add("Veuillez renseigner le prenom'");
            errors.add("Veuillez renseigner le nom'");
            errors.add("Veuillez renseigner l'email'");
            errors.add("Veuillez renseigner le cni'");
            errors.add("Veuillez renseigner le code'");
            errors.add("Veuillez renseigner le referentiel'");
            errors.add("Veuillez renseigner le lieu de naissance'");
            errors.add("Veuillez renseigner l'adresse'");
            errors.add("Veuillez renseigner le numero de telephone'");
            errors.add("Veuillez renseigner la date de naissance'");
            errors.add("Veuillez renseigner le numero du tuteur'");
            return errors;
        }

        if (!StringUtils.hasLength(cni)) {
            errors.add("Veuillez renseigner le cni'");
        }
        if (!cni.matches("[0-9] [0-9]{3} [0-9]{4} [0-9]{5}")){
            errors.add("CNI non valide");
        }
        if (!StringUtils.hasLength(prenom)) {
            errors.add("Veuillez renseigner le prenom'");
        }
        if (!StringUtils.hasLength(nom)) {
            errors.add("Veuillez renseigner le nom'");
        }
        if (!StringUtils.hasLength(code)) {
            errors.add("Veuillez renseigner le code'");
        }
        if (!StringUtils.hasLength(referentiel)) {
            errors.add("Veuillez renseigner le referentiel'");
        }
        if (!StringUtils.hasLength(lieuNaissance)) {
            errors.add("Veuillez renseigner le lieu de naissance'");
        }
        if (!StringUtils.hasLength(numTuteur)) {
            errors.add("Veuillez renseigner le numero du tuteur'");
        }
        if (!StringUtils.hasLength(addresse)) {
            errors.add("Veuillez renseigner l'adresse'");
        }
        if (!StringUtils.hasLength(phone)) {
            errors.add("Veuillez renseigner le numero de telephone'");
        }
        if (!StringUtils.hasLength(email)) {
            errors.add("Veuillez renseigner le Email'");
        }
        if (!phone.matches("^(33|7[05-8])[0-9]{7}$")){
            errors.add("Numero Telephone non valide");
        }
        if (dateNaissance == null) {
            errors.add("Veuillez renseigner la date de naissance'");
        }

        return errors;
    }
}
