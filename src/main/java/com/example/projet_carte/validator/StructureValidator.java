package com.example.projet_carte.validator;

import com.example.projet_carte.dto.StructureDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class StructureValidator {
    public static List<String> validate(StructureDto stucture)
    {
        return getString(stucture == null, stucture.getNomStructure());
    }

    public static List<String> getString(boolean isStructure, String nomStructure)
    {
        List<String> errors = new ArrayList<>();

        if(isStructure)
        {
            errors.add("Veuillez renseigner le Nom de la structure");
            return errors;
        }
        if (!StringUtils.hasLength(nomStructure))
        {
            errors.add("Veuillez renseigner le Nom de la structure");
        }
        return errors;
    }
}
