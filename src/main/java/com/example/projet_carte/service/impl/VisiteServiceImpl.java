package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.VisiteDto;
import com.example.projet_carte.dto.VisiteurDto;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Visites;
import com.example.projet_carte.model.Visiteur;
import com.example.projet_carte.repository.ApprenantRepository;
import com.example.projet_carte.repository.VisiteRepository;
import com.example.projet_carte.repository.VisiteurRepository;
import com.example.projet_carte.service.VisiteService;
import com.example.projet_carte.validator.VisiteursValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class VisiteServiceImpl implements VisiteService {

    VisiteurRepository visiteurRepository;
    VisiteRepository visiteRepository;
    ApprenantRepository apprenantRepository;

    @Override
    public VisiteDto saveVisiteVisiteur(VisiteDto visiteDto) {
        if (visiteurRepository.findByNumPiece(visiteDto.getVisiteur().getNumPiece()).isPresent()) {
            if (visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(LocalDate.now().atStartOfDay(),
                    LocalDate.now().plusDays(1).atStartOfDay(), null, visiteurRepository.findByNumPiece(visiteDto.getVisiteur().getNumPiece()).get()).isPresent())
                return null;
        }
        visiteDto.setDateEntree(LocalDateTime.now());
        return getVisiteVisiteur(visiteDto);
    }

    @Override
    public VisiteDto saveVisiteApprenant(VisiteDto visiteDto) {
        if (apprenantRepository.findByCodeAndArchiveFalse(visiteDto.getApprenant().getCode()).isPresent()) {
            if (visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(LocalDate.now().atStartOfDay(),
                    LocalDate.now().plusDays(1).atStartOfDay(), apprenantRepository.findByCodeAndArchiveFalse(visiteDto.getApprenant().getCode()).get(), null).isPresent())
                return null;
            visiteDto.setApprenant(ApprenantDto.fromEntity(apprenantRepository.findByCodeAndArchiveFalse(visiteDto.getApprenant().getCode()).get()));
            visiteDto.setDateEntree(LocalDateTime.now());
            return VisiteDto.fromEntity(visiteRepository.save(VisiteDto.toEntity(visiteDto)));
        }
        throw new InvalidEntityException("Apprenant Invalid", ErrorCodes.APPRENANT_NOT_FOUND, new ArrayList<>());
    }

    @Override
    public List<VisiteDto> findAll() {
        return visiteRepository.findAll().stream()
                .map(VisiteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisiteDto> findByDate(String date) {
        if (date == null) return null;
        return visiteRepository.findByDateEntreeBetween(LocalDate.parse(date).atStartOfDay(), LocalDate.parse(date).plusDays(1).atStartOfDay()).stream().map(VisiteDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<VisiteDto> findByDateByVisiteur(String date, String vst) {
        if (Objects.equals(vst, "apprenant")){
            return visiteRepository.findByDateEntreeBetweenAndApprenantIsNotNull(LocalDate.parse(date).atStartOfDay(),
                    LocalDate.parse(date).plusDays(1).atStartOfDay())
                    .stream()
                    .map(VisiteDto::fromEntity)
                    .collect(Collectors.toList());
        }else if (Objects.equals(vst, "visiteur")){
            return visiteRepository.findByDateEntreeBetweenAndVisiteurIsNotNull(LocalDate.parse(date).atStartOfDay(),
                            LocalDate.parse(date).plusDays(1).atStartOfDay())
                            .stream()
                            .map(VisiteDto::fromEntity)
                            .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<VisiteDto> findVisitesApp() {
        return visiteRepository.findAllByApprenantIsNotNull().stream().map(VisiteDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<VisiteDto> findVisitesVisiteur() {
        return visiteRepository.findAllByVisiteurIsNotNull().stream().map(VisiteDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public VisiteDto SortieApprenant(VisiteDto visiteDto) {
        Visites visite;
        if (apprenantRepository.findByCodeAndArchiveFalse(visiteDto.getApprenant().getCode()).isPresent()) {
            if (visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(LocalDate.now().atStartOfDay(),
                    LocalDate.now().plusDays(1).atStartOfDay(), apprenantRepository.findByCodeAndArchiveFalse(visiteDto.getApprenant().getCode()).get(), null).isPresent()) {
                visite = visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(LocalDate.now().atStartOfDay(),
                        LocalDate.now().plusDays(1).atStartOfDay(), apprenantRepository.findByCodeAndArchiveFalse(visiteDto.getApprenant().getCode()).get(), null).get();
                visite.setDateSortie(LocalDateTime.now());
                visiteRepository.flush();
                return VisiteDto.fromEntity(visite);
            } else{
                visiteDto.setApprenant(ApprenantDto.fromEntity(apprenantRepository.findByCodeAndArchiveFalse(visiteDto.getApprenant().getCode()).get()));
                visiteDto.setDateSortie(LocalDateTime.now());
                return VisiteDto.fromEntity(visiteRepository.save(VisiteDto.toEntity(visiteDto)));
            }
        }else {
            throw new InvalidEntityException("Vous etes pas enregistrer", ErrorCodes.APPRENANT_NOT_FOUND, new ArrayList<>());
        }
    }

    @Override
    public VisiteDto SortieVisiteur(VisiteDto visiteDto) {
        Visites visite;
        if (visiteurRepository.findByNumPiece(visiteDto.getVisiteur().getNumPiece()).isPresent() && visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(LocalDate.now().atStartOfDay(),
                    LocalDate.now().plusDays(1).atStartOfDay(), null, visiteurRepository.findByNumPiece(visiteDto.getVisiteur().getNumPiece()).get()).isPresent()) {
                visite = visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(LocalDate.now().atStartOfDay(),
                        LocalDate.now().plusDays(1).atStartOfDay(), null, visiteurRepository.findByNumPiece(visiteDto.getVisiteur().getNumPiece()).get()).get();
                visite.setDateSortie(LocalDateTime.now());
                visiteRepository.flush();
                return VisiteDto.fromEntity(visite);
        }else {
            visiteDto.setDateSortie(LocalDateTime.now());
            return getVisiteVisiteur(visiteDto);
        }
    }

    private VisiteDto getVisiteVisiteur(VisiteDto visiteDto) {
        validation(visiteDto.getVisiteur());
        if (visiteurRepository.findByNumPiece(visiteDto.getVisiteur().getNumPiece()).isPresent())
            visiteDto.setVisiteur(VisiteurDto.fromEntity(visiteurRepository.findByNumPiece(visiteDto.getVisiteur().getNumPiece()).get()));
        else
            visiteDto.setVisiteur(VisiteurDto.fromEntity(visiteurRepository.save(
                    new Visiteur(visiteDto.getVisiteur().getPrenom(),
                            visiteDto.getVisiteur().getNom(),
                            visiteDto.getVisiteur().getTypePiece(),
                            visiteDto.getVisiteur().getNumPiece(),
                            visiteDto.getVisiteur().getNumTelephone())
            )));
        return VisiteDto.fromEntity(visiteRepository.save(VisiteDto.toEntity(visiteDto)));
    }


    private void validation(VisiteurDto visiteur) {
        List<String> errors = VisiteursValidator.validate(visiteur);

        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Erreur!!!!!!", ErrorCodes.VISITEUR_NOT_VALID, errors);
        }
        if (visiteurRepository.findVisiteurByNumTelephone(visiteur.getNumTelephone()).isPresent()){
            throw new InvalidEntityException("Ce numéro est utiliser par un autre Visieur");
        }
    }

}
