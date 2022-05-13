package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.VisiteurDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.Visiteur;
import com.example.projet_carte.repository.VisiteurRepository;
import com.example.projet_carte.service.VisiteurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class VisiteurServiceImpl implements VisiteurService {

    VisiteurRepository visiteurRepository;

    @Override
    public List<VisiteurDto> findAll() {
        return visiteurRepository.findAll().stream().map(VisiteurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public VisiteurDto putVisiteur(VisiteurDto visiteurDto, Long id) {
        if (id == null) {
            return null;
        }else {
            Visiteur visiteur = visiteurRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException(
                            "Aucun visiteur avec l'ID = " + id + " ne se trouve dans la BDD",
                            ErrorCodes.VISITEUR_NOT_VALID));
            if (!Objects.equals(visiteurDto.getNom(), ""))
                visiteur.setNom(visiteurDto.getNom());
            if (!Objects.equals(visiteurDto.getPrenom(), ""))
                visiteur.setPrenom(visiteurDto.getPrenom());
            if (!Objects.equals(visiteurDto.getNumPiece(), ""))
                visiteur.setNumPiece(visiteurDto.getNumPiece());
            if (!Objects.equals(visiteurDto.getTypePiece(), ""))
                visiteur.setTypePiece(visiteurDto.getTypePiece());
            if (!Objects.equals(visiteurDto.getNumTelephone(), ""))
                visiteur.setNumTelephone(visiteurDto.getNumTelephone());
            visiteurRepository.flush();
            return VisiteurDto.fromEntity(visiteur);
        }
    }
}
