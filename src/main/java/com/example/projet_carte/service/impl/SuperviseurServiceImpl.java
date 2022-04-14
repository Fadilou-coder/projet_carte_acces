package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.SuperviseurDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Superviseur;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.repository.SuperviseurRepository;
import com.example.projet_carte.service.SuperviseurService;
import com.example.projet_carte.validator.SuperAdminValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SuperviseurServiceImpl implements SuperviseurService {
    private SuperviseurRepository superviseurRepository;
    private AdminRepository adminRepository;
    private SuperAdminRepository superAdminRepository;

    @Override
    public List<SuperviseurDto> findAll() {
        return superviseurRepository.findAllByArchiveFalse().stream().map(SuperviseurDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public SuperviseurDto findByNumPiece(String numPiece) {
        if (numPiece == null) return null;
        return superviseurRepository.findByNumPiece(numPiece).map(SuperviseurDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec le numPiece = " + numPiece + " ne se trouve dans la BDD",
                        ErrorCodes.SUPERVISEUR_NOT_FOUND)
        );
    }

    @Override
    public SuperviseurDto save(SuperviseurDto superviseurDto) {
        validation(superviseurDto, 0L);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        superviseurDto.setPassword(encoder.encode(superviseurDto.getPassword()));
        return SuperviseurDto.fromEntity(superviseurRepository.save(SuperviseurDto.toEntity(superviseurDto)));
    }

    @Override
    public SuperviseurDto update(SuperviseurDto superviseurDto, Long id) {
        if (id == null) return null;
        Superviseur superviseur = superviseurRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec l'id = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.SUPERVISEUR_NOT_FOUND)
        );
        validation(superviseurDto, id);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        superviseur.setAdresse(superviseurDto.getAddresse());
        superviseur.setPrenom(superviseurDto.getPrenom());
        superviseur.setNom(superviseur.getNom());
        superviseur.setNumPiece(superviseurDto.getNumPiece());
        superviseur.setPhone(superviseurDto.getPhone());
        superviseur.setPassword(encoder.encode(superviseurDto.getPassword()));

        superviseurRepository.flush();

        return superviseurDto;
    }

    @Override
    public SuperviseurDto delete(Long id) {
        if (id == null) return null;
        Superviseur superviseur = superviseurRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec l'id = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.SUPERVISEUR_NOT_FOUND)
        );
        superviseur.setArchive(true);
        superviseurRepository.flush();
        return SuperviseurDto.fromEntity(superviseur);
    }

    @Override
    public SuperviseurDto findById(Long id) {
        if (id == null) return null;
        Superviseur superviseur = superviseurRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec l' id = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.SUPERVISEUR_NOT_FOUND)
        );
        return SuperviseurDto.fromEntity(superviseur);
    }

    private void validation(SuperviseurDto superviseurDto, Long id) {
        List<String> errors = SuperAdminValidator.validate(superviseurDto, null, "superviseur");

        AdminServiceImpl.ArealyExist(id, errors, superAdminRepository, superviseurRepository, adminRepository, superviseurDto.getEmail(), superviseurDto.getPhone());

        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Erreur!!!!!!", ErrorCodes.SUPERVISEUR_NOT_VALID, errors);
        }
    }
}
