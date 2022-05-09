package com.example.projet_carte.service.impl;

import com.example.projet_carte.EmailSenderService;
import com.example.projet_carte.dto.AdminDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Admin;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.repository.SuperviseurRepository;
import com.example.projet_carte.service.AdminService;
import com.example.projet_carte.validator.AdminValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    AdminRepository adminRepository;
    SuperAdminRepository superAdminRepository;
    SuperviseurRepository superviseurRepository;
    private EmailSenderService emailSenderService;


    @Override
    public List<AdminDto> findAll() {
        return adminRepository.findAll().stream()
                .map(AdminDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public AdminDto save(AdminDto adminDto) throws IOException  {

        validation(adminDto, 0L);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        adminDto.setPassword(encoder.encode("password"));
        String body = "Bonjour M.(MMe) " + adminDto.getPrenom() + " " + adminDto.getNom() + "vous trouverez ci dessous vos parametres de connexion. " + System.getProperty("line.separator") + System.getProperty("line.separator") +
                "Email: " + adminDto.getEmail() + System.getProperty("line.separator") + " Mot de passe: password" + System.getProperty("line.separator") + System.getProperty("line.separator") +  " Cordialement!";
        emailSenderService.sendSimpleEmail(adminDto.getEmail(), body,"Orange Digital Center");

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
        if (!Objects.equals(adminDto.getPrenom(), ""))
            admin.setPrenom(adminDto.getPrenom());
        if (!Objects.equals(adminDto.getNom(), ""))
            admin.setNom(adminDto.getNom());
        if (!Objects.equals(adminDto.getEmail(), ""))
            admin.setEmail(adminDto.getEmail());
        if (!Objects.equals(adminDto.getPhone(), ""))
            admin.setPhone(adminDto.getPhone());
        if (!Objects.equals(adminDto.getAddresse(), ""))
            admin.setAdresse(adminDto.getAddresse());
        if (!Objects.equals(adminDto.getNumPiece(), ""))
            admin.setNumPiece(adminDto.getNumPiece());

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

    @Override
    public void debloquerAdmin(Long id){
        if (id == null) {
            log.error("Admin id is null");
        }

        Admin admin = adminRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun admin avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.ADMIN_NOT_FOUND));
        admin.setArchive(false);
        adminRepository.flush();
    }

    private void validation(AdminDto adminDto, Long id) {
        List<String> errors = AdminValidator.validateAd(adminDto);

        ArealyExist(id, errors, superAdminRepository, superviseurRepository, adminRepository, adminDto.getEmail(), adminDto.getPhone(), adminDto.getNumPiece());

        if (!errors.isEmpty()) {
            throw new InvalidEntityException("L'Admin n'est pas valide", ErrorCodes.ADMIN_NOT_VALID, errors);
        }

    }

    static void ArealyExist(Long id, List<String> errors, SuperAdminRepository superAdminRepository, SuperviseurRepository superviseurRepository, AdminRepository adminRepository, String email, String phone, String numPiece) {

            if (superAdminRepository.findByEmailAndIdNot(email, id).isPresent() || adminRepository.findByEmailAndIdNot(email, id).isPresent() || superviseurRepository.findByEmailAndIdNot(email, id).isPresent() ) {
                errors.add("un utilisateur avec ce email existe deja dans la base de données");
            }

            if (superAdminRepository.findByPhoneAndIdNot(phone, id).isPresent() || adminRepository.findByPhoneAndIdNot(phone, id).isPresent() || superviseurRepository.findByPhoneAndIdNot(phone, id).isPresent()) {
                errors.add("un utilisateur avec ce numero téléphone existe deja dans la base de données");
            }
            if(superAdminRepository.findByNumPieceAndIdNot(numPiece, id).isPresent() || adminRepository.findByNumPieceAndIdNot(numPiece, id).isPresent() || superviseurRepository.findByNumPieceAndIdNot(numPiece, id).isPresent()){
                errors.add("un utilisateur avec ce numero de piece existe deja dans la base de données");
            }
    }

}
