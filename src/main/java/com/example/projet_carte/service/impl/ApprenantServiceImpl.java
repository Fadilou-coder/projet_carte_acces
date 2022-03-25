package com.example.projet_carte.service.impl;

import com.example.projet_carte.EmailSenderService;
import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.PromoDto;
import com.example.projet_carte.dto.ReferentielDto;
import com.example.projet_carte.dto.StructureDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.Personne;
import com.example.projet_carte.model.Referentiel;
import com.example.projet_carte.model.Structure;
import com.example.projet_carte.repository.ApprenantRepository;
import com.example.projet_carte.repository.PromoRepository;
import com.example.projet_carte.repository.ReferentielRepository;
import com.example.projet_carte.repository.UserRepository;
import com.example.projet_carte.service.ApprenantService;
import com.example.projet_carte.validator.PersonneValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.Deflater;

@Slf4j
@Service
@AllArgsConstructor
public class ApprenantServiceImpl implements ApprenantService {
    ApprenantRepository apprenantRepository;
    UserRepository userRepository;
    ReferentielRepository referentielRepository;
    PromoRepository promoRepository;
    EmailSenderService emailSenderService;

    @Override
    public List<ApprenantDto> findAll() {
        return apprenantRepository.findAllByArchiveFalse().stream()
                .map(ApprenantDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprenantDto> findByref(Long id) {
        return apprenantRepository.findByReferentielIdAndArchiveFalse(id).stream()
                .map(ApprenantDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprenantDto> findBypromo(Long id) {
        return apprenantRepository.findByPromoIdAndArchiveFalse(id).stream().map(ApprenantDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<ApprenantDto> findByRefByPromo(Long idRef, Long idPr) {
        return apprenantRepository.findByPromoIdAndReferentielIdAndArchiveFalse(idPr, idRef).stream().map(ApprenantDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public ApprenantDto save(String prenom, String nom, String email, String phone, String adresse, String cni, String referentiel, String promo, String dateNaissance, String lieuNaissance, String numTuteur, MultipartFile avatar) throws IOException {

        Random random = new Random();
        if (referentielRepository.findByLibelle(referentiel).isPresent() && promoRepository.findByLibelle(promo).isPresent()) {
            String code = promoRepository.findByLibelle(promo).get().getAnnee() + (random.nextInt(9999 - 1000)+ 1001);
            while(apprenantRepository.findByCodeAndArchiveFalse(code).isPresent()){
                code = promoRepository.findByLibelle(promo).get().getAnnee() + (random.nextInt(9999 - 1001) + 1001);
            }
            ApprenantDto apprenantDto = new ApprenantDto(
                    null, prenom, nom, email, phone, adresse, cni, code,
                    ReferentielDto.fromEntity(referentielRepository.findByLibelle(referentiel).get()), PromoDto.fromEntity(promoRepository.findByLibelle(promo).get()),
                    LocalDate.parse(dateNaissance), lieuNaissance, numTuteur, avatar.getBytes(), null
            );

            validation(apprenantDto);
            return ApprenantDto.fromEntity(apprenantRepository.save(ApprenantDto.toEntity(apprenantDto)));
        }
        throw new InvalidEntityException("le referentiel ou la promo choisi(e) n'existe pas dans la BDD", ErrorCodes.APPRENANT_NOT_VALID);
    }

    @Override
    public void sendCarte(String prenom, String nom, String email, MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        String body = "Bonjour M.(MMe) " + prenom + " " + nom + " vous trouverez ci dessous votre carte d'access. "+ System.getProperty("line.separator") + System.getProperty("line.separator") + "Cordialement";
        emailSenderService.sendEmailInlineImage("Orange Digital Center", body, email, convFile);
    }

    @Override
    public ApprenantDto findById(Long id) {
        if (id == null) {
            log.error("Apprenant id is null");
            return null;
        }
        return apprenantRepository.findByIdAndArchiveFalse(id).map(ApprenantDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND)
        );
    }

    @Override
    public ApprenantDto put(Long id, String prenom, String nom, String email, String phone, String adresse, String cni, String dateNaissance, String lieuNaissance, String numTuteur, MultipartFile avatar) throws IOException {
        if (id == null) {
            log.error("Apprenant Id is null");
        }
        Apprenant apprenant = apprenantRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND));

        if (!Objects.equals(prenom, "")) {
            apprenant.setPrenom(prenom);
        }

        if (!Objects.equals(nom, "")) {
            apprenant.setNom(nom);
        }
        if (!Objects.equals(email, "")) {
            apprenant.setEmail(email);
        }
        if (!Objects.equals(phone, "")) {
            apprenant.setPhone(phone);
        }
        if (!Objects.equals(adresse, "")) {
            apprenant.setAdresse(adresse);
        }
        if (!Objects.equals(cni, "")) {
            apprenant.setCni(cni);
        }
        if (!Objects.equals(dateNaissance, "")) {
            apprenant.setDateNaissance(LocalDate.parse(dateNaissance));
        }
        if (!Objects.equals(lieuNaissance, "")) {
            apprenant.setLieuNaissance(lieuNaissance);
        }
        if (!Objects.equals(numTuteur, "")) {
            apprenant.setNumTuteur(numTuteur);
        }
        if (!avatar.isEmpty()) {
            apprenant.setAvatar(avatar.getBytes());
        }

        ApprenantDto apprenantDto = ApprenantDto.fromEntity(apprenant);
        validation(apprenantDto);

        apprenantRepository.flush();
        return apprenantDto;
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Apprenant Id is null");
        }

        Apprenant apprenant = apprenantRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun apprenant avec l'Id = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND));
        apprenant.setArchive(true);
        apprenantRepository.flush();
    }

    private void validation(ApprenantDto apprenantDto) {
        List<String> errors = PersonneValidator.Appvalidate(apprenantDto);
        if(userAlreadyExists(apprenantDto.getEmail(), apprenantDto.getId())) {
            throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.APPRENANT_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));
        }

        if(userAlreadyExistsPhone(apprenantDto.getPhone(), apprenantDto.getId())) {
            throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.APPRENANT_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));
        }

        if(userAlreadyExistsCni(apprenantDto.getCni(), apprenantDto.getId())) {
            throw new InvalidEntityException("Un autre utilisateur avec le meme cni existe deja", ErrorCodes.APPRENANT_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme cni existe deja dans la BDD"));
        }

        if(userAlreadyExistsCode(apprenantDto.getCode(), apprenantDto.getId())) {
            throw new InvalidEntityException("Un autre utilisateur avec le meme code existe deja", ErrorCodes.APPRENANT_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme code existe deja dans la BDD"));
        }

        if (!errors.isEmpty()) {
            log.error("Apprenant is not valid {}", apprenantDto);
            throw new InvalidEntityException("L'Apprenant n'est pas valide", ErrorCodes.APPRENANT_NOT_VALID, errors);
        }
    }

    private boolean userAlreadyExists(String email, Long id) {
        Optional<Personne> user;
        if (id == null){
            user = userRepository.findByEmail(email);
        }else {
            user = userRepository.findByEmailAndIdNot(email, id);
        }
        return user.isPresent();
    }
    private boolean userAlreadyExistsPhone(String phone, Long id) {
        Optional<Personne> user;
        if (id == null) {
            user = userRepository.findByPhone(phone);
        }else {
            user = userRepository.findByPhoneAndIdNot(phone, id);
        }
        return user.isPresent();
    }

    private boolean userAlreadyExistsCni(String cni, Long id) {
        Optional<Personne> user;
        if (id == null) {
            user = userRepository.findByCni(cni);
        }else {
            user = userRepository.findByCniAndIdNot(cni, id);
        }
        return user.isPresent();
    }

    private boolean userAlreadyExistsCode(String code, Long id) {
        Optional<Apprenant> user;
        if (id == null) {
            user = apprenantRepository.findByCodeAndArchiveFalse(code);
        }else {
            user = apprenantRepository.findByCodeAndIdNot(code, id);
        }
        return user.isPresent();
    }

}
