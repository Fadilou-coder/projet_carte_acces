package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.SuperAdminDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.SuperAdmin;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.service.SuperAdminService;
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
public class SuperAdminServiceImpl implements SuperAdminService {

    private SuperAdminRepository superAdminRepository;
    private AdminRepository adminRepository;

    @Override
    public List<SuperAdminDto> findAll() {
        return superAdminRepository.findAllByArchiveFalse().stream().map(SuperAdminDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public SuperAdminDto findByCni(String cni) {
        if (cni == null) return null;
        return superAdminRepository.findByCni(cni).map(SuperAdminDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec le cni = " + cni + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND)
        );
    }

    @Override
    public SuperAdminDto save(SuperAdminDto superAdminDto) {
        validation(superAdminDto, 0L);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        superAdminDto.setPassword(encoder.encode(superAdminDto.getPassword()));
        return SuperAdminDto.fromEntity(superAdminRepository.save(SuperAdminDto.toEntity(superAdminDto)));
    }

    @Override
    public SuperAdminDto update(SuperAdminDto superAdminDto, Long id) {
        if (id == null) return null;
        SuperAdmin superAdmin = superAdminRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec le cni = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND)
        );
        validation(superAdminDto, id);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        superAdmin.setAdresse(superAdminDto.getAddresse());
        superAdmin.setPrenom(superAdminDto.getPrenom());
        superAdmin.setNom(superAdmin.getNom());
        superAdmin.setCni(superAdminDto.getCni());
        superAdmin.setPhone(superAdminDto.getPhone());
        superAdmin.setPassword(encoder.encode(superAdminDto.getPassword()));

        superAdminRepository.flush();

        return superAdminDto;
    }

    @Override
    public SuperAdminDto delete(Long id) {
        if (id == null) return null;
        SuperAdmin superAdmin = superAdminRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec le cni = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND)
        );
        superAdmin.setArchive(true);
        superAdminRepository.flush();
        return SuperAdminDto.fromEntity(superAdmin);
    }

    @Override
    public SuperAdminDto findById(Long id) {
        if (id == null) return null;
        SuperAdmin superAdmin = superAdminRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec le cni = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND)
        );
        return SuperAdminDto.fromEntity(superAdmin);
    }

    private void validation(SuperAdminDto superAdminDto, Long id) {
        List<String> errors = SuperAdminValidator.validate(superAdminDto);

        AdminServiceImpl.ArealyExist(id, errors, superAdminRepository, superAdminDto.getEmail(), adminRepository, superAdminDto.getCni(), superAdminDto.getEmail(), superAdminDto.getPhone());

        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Erreur!!!!!!", ErrorCodes.ADMIN_NOT_VALID, errors);
        }

    }
}
