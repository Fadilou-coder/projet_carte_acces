package com.example.projet_carte.validator;

import com.example.projet_carte.dto.AdminDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdminValidator {

    public static List<String> validateAd(AdminDto adminDto) {
        return getStrings(adminDto == null, adminDto.getPrenom(), adminDto.getNom(),adminDto.getEmail(),adminDto.getPhone(),
                adminDto.getAddresse(), adminDto.getNumPiece());
    }


    private static List<String> getStrings(boolean b, String prenom, String nom, String email, String phone,
                                           String adresse, String cni) {
        List<String> errors = new ArrayList<>();

        if (b) {
            errors.add("Veuillez renseigner le prenom'");
            errors.add("Veuillez renseigner le nom'");
            errors.add("Veuillez renseigner l'email'");;
            errors.add("Veuillez renseigner le numero de telephone'");
            errors.add("Veuillez renseigner l'adresse'");
            errors.add("Veuillez renseigner la carte d'identité'");
            errors.add("Veuillez renseigner le structure'");
            return errors;
        }

        if (!StringUtils.hasLength(phone)) {
            errors.add("Veuillez renseigner le numero de telephone'");
        }
        if (!phone.matches("^(33|7[05-8])[0-9]{7}$")){
            errors.add("Numero Telephone non valide");
        }
        if (!StringUtils.hasLength(prenom)) {
            errors.add("Veuillez renseigner le prenom'");
        }
        if (!StringUtils.hasLength(nom)) {
            errors.add("Veuillez renseigner le nom'");
        }
        if (!StringUtils.hasLength(email)) {
            errors.add("Veuillez renseigner l'email'");
        }
        if (!email.matches("^[a-z0-9.-]+@[a-z0-9.-]{2,}\\.[a-z]{2,4}$")){
            errors.add("email non valide");
        }
        if(!StringUtils.hasLength(adresse)){
            errors.add("Veuillez renseigner l'adresse");
        }
        if (!StringUtils.hasLength(cni)) {
            errors.add("Veuillez renseigner le cni'");
        }
        if (!cni.matches("(^[1-2])[0-9]{12}$")){
            errors.add("CNI non valide");
        }
        return errors;
    }
}
