package com.example.projet_carte.service.impl;


import com.example.projet_carte.dto.ReferentielDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Referentiel;
import com.example.projet_carte.repository.ReferentielRepository;
import com.example.projet_carte.service.ReferentielService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReferentielServiceImpl implements ReferentielService {
   private ReferentielRepository referentielRepository;

    @Override
    public List<ReferentielDto> findAll() {
        return referentielRepository.findAll().stream().map(ReferentielDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public ReferentielDto save(ReferentielDto referentielDto) {
        if (Objects.equals(referentielDto.getLibelle(), ""))
            throw new InvalidEntityException("Veuillez renseignez le libelle du referentiel");

        return ReferentielDto.fromEntity(referentielRepository.save(ReferentielDto.toEntity(referentielDto)));
    }

    @Override
    public ReferentielDto putPromo(ReferentielDto referentielDto, Long id) {
        if (id == null) {
            return null;
        }else {
            Referentiel referentiel = referentielRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException(
                            "Aucun apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                            ErrorCodes.APPRENANT_NOT_FOUND));
            if (!Objects.equals(referentielDto.getLibelle(), ""))
                referentiel.setLibelle(referentielDto.getLibelle());
            referentielRepository.flush();
            return ReferentielDto.fromEntity(referentiel);
        }
    }
}
