package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.VisiteDto;
import com.example.projet_carte.dto.VisiteurDto;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Visiteur;
import com.example.projet_carte.repository.VisiteRepository;
import com.example.projet_carte.repository.VisiteurRepository;
import com.example.projet_carte.service.VisiteService;
import com.example.projet_carte.validator.VisiteursValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class VisiteServiceImpl implements VisiteService {

    VisiteurRepository visiteurRepository;
    VisiteRepository visiteRepository;

    @Override
    public VisiteDto save(VisiteDto visiteDto) {
        validation(visiteDto.getVisiteur());
        if (visiteurRepository.findByCni(visiteDto.getVisiteur().getCni()).isPresent())
            visiteDto.setVisiteur(VisiteurDto.fromEntity(visiteurRepository.findByCni(visiteDto.getVisiteur().getCni()).get()));
        else
            visiteDto.setVisiteur(VisiteurDto.fromEntity(visiteurRepository.save(
                    new Visiteur(visiteDto.getVisiteur().getPrenom(),
                            visiteDto.getVisiteur().getNom(),
                            visiteDto.getVisiteur().getCni(),
                            visiteDto.getVisiteur().getNumTelephone())
            )));
        return VisiteDto.fromEntity(visiteRepository.save(VisiteDto.toEntity(visiteDto)));
    }

    @Override
    public List<VisiteDto> findAll() {
        return visiteRepository.findAll().stream()
                .map(VisiteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisiteDto> findByDate(String date) {
        return visiteRepository.findAllByDateEntree(LocalDate.parse(date)).stream().map(VisiteDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<VisiteDto> findVisitesApp() {
        return visiteRepository.findAllByApprenantIsNotNull().stream().map(VisiteDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<VisiteDto> findVisitesVisiteur() {
        return visiteRepository.findAllByVisiteurIsNotNull().stream().map(VisiteDto::fromEntity).collect(Collectors.toList());
    }


    private void validation(VisiteurDto visiteur) {
        List<String> errors = VisiteursValidator.validate(visiteur);

        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Erreur!!!!!!", ErrorCodes.VISITEUR_NOT_VALID, errors);
        }
    }

}
