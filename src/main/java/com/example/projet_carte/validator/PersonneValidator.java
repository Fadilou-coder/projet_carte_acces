package com.example.projet_carte.validator;

import com.example.projet_carte.dto.ApprenantDto;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonneValidator {

    public static List<String> Appvalidate(ApprenantDto userDto) {
        return getStrings(userDto == null, userDto.getPrenom(),
                userDto.getNom(), userDto.getEmail(), userDto.getPhone(), userDto.getAddresse(), userDto.getNumPiece(),
                userDto.getCode(),  userDto.getDateNaissance(), userDto.getLieuNaissance(), userDto.getNumTuteur());
    }

    private static List<String> getStrings(boolean b, String prenom, String nom, String email, String phone, String addresse, String numPiece,
                                           String code, LocalDate dateNaissance, String lieuNaissance, String numTuteur) {
        List<String> errors = new ArrayList<>();

        if (b) {
            errors.add("Veuillez renseigner le prenom'");
            errors.add("Veuillez renseigner le nom'");
            errors.add("Veuillez renseigner l'email'");
            errors.add("Veuillez renseigner le numéro de piece'");
            errors.add("Veuillez renseigner le code'");
            errors.add("Veuillez renseigner le lieu de naissance'");
            errors.add("Veuillez renseigner l'adresse'");
            errors.add("Veuillez renseigner le numero de telephone'");
            errors.add("Veuillez renseigner la date de naissance'");
            errors.add("Veuillez renseigner le numero du tuteur'");
            return errors;
        }

        if (!StringUtils.hasLength(numPiece)) {
            errors.add("Veuillez renseigner le numéro de Piece'");
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
        if (dateNaissance == null) {
            errors.add("Veuillez renseigner la date de naissance'");
        }

        return errors;
    }
}
