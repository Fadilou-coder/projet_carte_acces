package com.example.projet_carte.validator;

import com.example.projet_carte.dto.VisiteurDto;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class VisiteursValidator {
    public static List<String> validate(VisiteurDto visiteur)
    {
        return getString(visiteur == null, visiteur.getNumPiece(), visiteur.getPrenom(), visiteur.getNom(), visiteur.getNumTelephone());
    }

    public static List<String> getString(boolean isStructure, String cni, String prenom, String nom, String num)
    {
        List<String> errors = new ArrayList<>();

        if(isStructure)
        {
            errors.add("Veuillez renseigner le CNI du visiteur");
            errors.add("Veuillez renseigner le prenom du visiteur");
            errors.add("Veuillez renseigner le nom du visiteur");
            errors.add("Veuillez renseigner le numéro téléphone du visiteur");
            return errors;
        }
        if (!StringUtils.hasLength(cni))
        {
            errors.add("Veuillez renseigner le CNI du visiteur");
        }
        if (!cni.matches("(^[1-2])[0-9]{12}$")){
            errors.add("CNI non valide");
        }
        if (!StringUtils.hasLength(prenom))
        {
            errors.add("Veuillez renseigner le prenom du visiteur");
        }
        if (!StringUtils.hasLength(nom))
        {
            errors.add("Veuillez renseigner le nom du visiteur");
        }
        if (!StringUtils.hasLength(num))
        {
            errors.add("Veuillez renseigner le numéro du visiteur");
        }
        if (!num.matches("^(33|7[05-8])[0-9]{7}$")){
            errors.add("Numero Telephone non valide");
        }
        return errors;
    }
}
