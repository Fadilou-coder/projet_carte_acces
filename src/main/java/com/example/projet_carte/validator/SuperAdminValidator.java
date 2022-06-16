package com.example.projet_carte.validator;

import com.example.projet_carte.dto.SuperAdminDto;
import com.example.projet_carte.dto.SuperviseurDto;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class SuperAdminValidator {

    public static List<String> validate(SuperviseurDto superviseurDto, SuperAdminDto superAdminDto, String admin)
    {
        if (Objects.equals(admin, "superAdmin"))
            return getString(superAdminDto == null, superAdminDto.getNumPiece(), superAdminDto.getPrenom(), superAdminDto.getNom(), superAdminDto.getPhone(), superAdminDto.getEmail(), superAdminDto.getPassword(), superAdminDto.getAddresse(), superAdminDto.getId());
        else
            return getString(superviseurDto == null, superviseurDto.getNumPiece(), superviseurDto.getPrenom(), superviseurDto.getNom(), superviseurDto.getPhone(), superviseurDto.getEmail(), superviseurDto.getPassword(), superviseurDto.getAddresse(), superviseurDto.getId());
    }

    public static List<String> getString(boolean isPersonne, String cni, String prenom, String nom, String num, String email, String password, String adresse, Long id)
    {
        List<String> errors = new ArrayList<>();

        if(isPersonne)
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
        if (!cni.matches("(^[1-2])[0-9]{12}$")){
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
