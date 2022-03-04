package com.example.projet_carte.validator;

import com.example.projet_carte.dto.SuperAdminDto;
import com.example.projet_carte.repository.SuperAdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class SuperAdminValidator {

    private SuperAdminRepository superAdminRepository;

    public static List<String> validate(SuperAdminDto superAdminDto)
    {
        return getString(superAdminDto == null, superAdminDto.getCni(), superAdminDto.getPrenom(), superAdminDto.getNom(), superAdminDto.getPhone(), superAdminDto.getEmail(), superAdminDto.getPassword(), superAdminDto.getUsername(), superAdminDto.getAddresse(), superAdminDto.getId());
    }

    public static List<String> getString(boolean isStructure, String cni, String prenom, String nom, String num, String email, String password, String username, String adresse, Long id)
    {
        List<String> errors = new ArrayList<>();

        if(isStructure)
        {
            errors.add("Veuillez renseigner le CNI du super admin");
            errors.add("Veuillez renseigner le prenom du super admin");
            errors.add("Veuillez renseigner le nom du super admin");
            errors.add("Veuillez renseigner le numéro téléphone du super admin");
            errors.add("Veuillez renseigner le username du super admin");
            errors.add("Veuillez renseigner l'email du super admin");
            errors.add("Veuillez renseigner le mot de passe du super admin");
            errors.add("Veuillez renseigner le username du super admin");
            errors.add("Veuillez renseigner l'adresse du super admin");

            return errors;
        }
        if (!StringUtils.hasLength(cni))
        {
            errors.add("Veuillez renseigner le CNI du super admin");
        }
        if (!cni.matches("[0-9] [0-9]{3} [0-9]{4} [0-9]{5}")){
            errors.add("CNI non valide");
        }
        if (!StringUtils.hasLength(prenom))
        {
            errors.add("Veuillez renseigner le prenom du super admin");
        }
        if (!StringUtils.hasLength(nom))
        {
            errors.add("Veuillez renseigner le nom du super admin");
        }
        if (!StringUtils.hasLength(num))
        {
            errors.add("Veuillez renseigner le numéro du super admin");
        }
        if (!num.matches("^(33|7[05-8])[0-9]{7}$")){
            errors.add("Numero Telephone non valide");
        }
        if (!StringUtils.hasLength(email))
        {
            errors.add("Veuillez renseigner l'email du super admin");
        }
        if (!StringUtils.hasLength(username))
        {
            errors.add("Veuillez renseigner le username du super admin");
        }
        if (!StringUtils.hasLength(password))
        {
            errors.add("Veuillez renseigner le password du super admin");
        }
        if (!StringUtils.hasLength(adresse))
        {
            errors.add("Veuillez renseigner l'adresse du super admin");
        }

        return errors;
    }
}
