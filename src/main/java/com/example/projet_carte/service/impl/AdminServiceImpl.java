package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.AdminDto;
import com.example.projet_carte.dto.StructureDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.Structure;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.StructureRepository;
import com.example.projet_carte.service.AdminService;
import com.example.projet_carte.validator.AdminValidator;
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


    @Override
    public List<AdminDto> findAll() {
        return adminRepository.findAllByArchiveFalse().stream()
                .map(AdminDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public AdminDto save(AdminDto adminDto) throws IOException  {

        List<String> errors = validation(adminDto);
        if (!errors.isEmpty()) {
            log.error("Admin is not valid {}", adminDto);
            throw new InvalidEntityException("L'Admin n'est pas valide", ErrorCodes.ADMIN_NOT_VALID, errors);
        }
        //Structure structure = structureRepository.findByNomStructure(adminDto.getStructure().toString());
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
        admin.setStructure(structureRepository.findByIdAndArchiveFalse(adminDto.getStructure().getId()).get());

        AdminDto adminDto1 = AdminDto.fromEntity(admin);
        validation(adminDto1);

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

    private List<String> validation(AdminDto adminDto) {
        List<String> errors = AdminValidator.validateAd(adminDto);
        if(userAlreadyExists(adminDto.getEmail(), adminDto.getId())) {
            throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.ADMIN_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));
        }

        if(userAlreadyExistsPhone(adminDto.getPhone(), adminDto.getId())) {
            throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.ADMIN_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));
        }

        if(userAlreadyExistsCni(adminDto.getCni(), adminDto.getId())) {
            throw new InvalidEntityException("Un autre utilisateur avec le meme cni existe deja", ErrorCodes.ADMIN_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme cni existe deja dans la BDD"));
        }

        if(userAlreadyExistsUsername(adminDto.getUsername(), adminDto.getId())) {
            throw new InvalidEntityException("Un autre utilisateur avec le meme username existe deja", ErrorCodes.ADMIN_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme username existe deja dans la BDD"));
        }

        if (!errors.isEmpty()) {
            log.error("Admin is not valid {}", adminDto);
            throw new InvalidEntityException("L'Admin n'est pas valide", ErrorCodes.ADMIN_NOT_VALID, errors);
        }
        return errors;
    }

    private boolean userAlreadyExists(String email, Long id) {
        Optional<Admin> user;
        if (id == null){
            user = adminRepository.findByEmail(email);
        }else {
            user = adminRepository.findByEmailAndIdNot(email, id);
        }
        return user.isPresent();
    }
    private boolean userAlreadyExistsPhone(String phone, Long id) {
        Optional<Admin> user;
        if (id == null) {
            user = adminRepository.findByPhone(phone);
        }else {
            user = adminRepository.findByPhoneAndIdNot(phone, id);
        }
        return user.isPresent();
    }

    private boolean userAlreadyExistsCni(String cni, Long id) {
        Optional<Admin> user;
        if (id == null) {
            user = adminRepository.findByCni(cni);
        }else {
            user = adminRepository.findByCniAndIdNot(cni, id);
        }
        return user.isPresent();
    }

    private boolean userAlreadyExistsUsername(String username, Long id) {
        Optional<Admin> user;
        if (id == null) {
            user = adminRepository.findByUsername(username);
        }else {
            user = adminRepository.findByUsernameAndIdNot(username, id);
        }
        return user.isPresent();
    }

}
