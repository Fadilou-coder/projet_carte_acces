package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.AdminDto;
import com.example.projet_carte.dto.StructureDto;
import com.example.projet_carte.dto.SuperAdminDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.Structure;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.StructureRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.service.AdminService;
import com.example.projet_carte.validator.AdminValidator;
import com.example.projet_carte.validator.SuperAdminValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    AdminRepository adminRepository;
    StructureRepository structureRepository;
    SuperAdminRepository superAdminRepository;


    @Override
    public List<AdminDto> findAll() {
        return adminRepository.findAllByArchiveFalse().stream()
                .map(AdminDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public AdminDto save(AdminDto adminDto) throws IOException  {

        validation(adminDto, 0L);
       Optional <Structure> structure = structureRepository.findById(adminDto.getStructure().getId());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        adminDto.setPassword(encoder.encode("password"));
        structure.ifPresent(value -> adminDto.setStructure(StructureDto.fromEntity(value)));
        return AdminDto.fromEntity(
                adminRepository.save(
                        AdminDto.toEntity(adminDto)
                )
        );
    }

    @Override
    public AdminDto findById(Long id) {
        if (id == null) {
            log.error("Admin id is null");
            return null;
        }
        return adminRepository.findByIdAndArchiveFalse(id).map(AdminDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Admin avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.ADMIN_NOT_FOUND)
        );
    }

    @Override
    public AdminDto put(AdminDto adminDto, Long id) throws IOException {
        Admin admin = adminRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun admin avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.ADMIN_NOT_FOUND));
        admin.setPrenom(adminDto.getPrenom());
        admin.setNom(adminDto.getNom());
        admin.setEmail(adminDto.getEmail());
        admin.setPhone(adminDto.getPhone());
        admin.setAdresse(adminDto.getAddresse());
        admin.setCni(adminDto.getCni());
        admin.setUsername(adminDto.getUsername());
        if (structureRepository.findByNomStructureAndArchiveFalse(adminDto.getStructure().getNomStructure()).isPresent())
            admin.setStructure(structureRepository.findByNomStructureAndArchiveFalse(adminDto.getStructure().getNomStructure()).get());

        AdminDto adminDto1 = AdminDto.fromEntity(admin);
        validation(adminDto1, id);

        adminRepository.flush();
        return adminDto1;
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Admin id is null");
        }

        Admin admin = adminRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun admin avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.ADMIN_NOT_FOUND));
        admin.setArchive(true);
        adminRepository.flush();
    }

    private void validation(AdminDto adminDto, Long id) {
        List<String> errors = AdminValidator.validateAd(adminDto);

        ArealyExist(id, errors, superAdminRepository, adminDto.getUsername(), adminRepository, adminDto.getCni(), adminDto.getEmail(), adminDto.getPhone());

        if (!errors.isEmpty()) {
            throw new InvalidEntityException("L'Admin n'est pas valide", ErrorCodes.ADMIN_NOT_VALID, errors);
        }

    }

    static void ArealyExist(Long id, List<String> errors, SuperAdminRepository superAdminRepository, String username, AdminRepository adminRepository, String cni, String email, String phone) {
        if (superAdminRepository.findByUsernameAndIdNot(username, id).isPresent() || adminRepository.findByUsernameAndIdNot(username, id).isPresent()){
            errors.add("un utilisateur avec ce username existe deja dans la base de données");
        }

        if (superAdminRepository.findByCniAndIdNot(cni, id).isPresent() || adminRepository.findByCniAndIdNot(cni, id).isPresent()){
            errors.add("un utilisateur avec ce cni existe deja dans la base de données");
        }

        if (superAdminRepository.findByEmailAndIdNot(email, id).isPresent() || adminRepository.findByEmailAndIdNot(email, id).isPresent()){
            errors.add("un utilisateur avec ce email existe deja dans la base de données");
        }

        if (superAdminRepository.findByPhoneAndIdNot(phone, id).isPresent() || adminRepository.findByPhoneAndIdNot(phone, id).isPresent()){
            errors.add("un utilisateur avec ce numero téléphone existe deja dans la base de données");
        }
    }

}
