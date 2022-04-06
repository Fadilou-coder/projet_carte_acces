package com.example.projet_carte.service.impl;

import com.example.projet_carte.EmailSenderService;
import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.PromoDto;
import com.example.projet_carte.dto.ReferentielDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.ErrorCodes;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.Personne;
import com.example.projet_carte.repository.ApprenantRepository;
import com.example.projet_carte.repository.PromoRepository;
import com.example.projet_carte.repository.ReferentielRepository;
import com.example.projet_carte.repository.UserRepository;
import com.example.projet_carte.service.ApprenantService;
import com.example.projet_carte.validator.PersonneValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
            String code = promoRepository.findByLibelle(promo).get().getDateDebut().toString().substring(0, 4) + (random.nextInt(9999 - 1000)+ 1001);
            while(apprenantRepository.findByCodeAndArchiveFalse(code).isPresent()){
                code = promoRepository.findByLibelle(promo).get().getDateDebut().toString().substring(0, 4) + (random.nextInt(9999 - 1001) + 1001);
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
    public List<ApprenantDto> saveFromCsv(MultipartFile file) {
        String TYPE = "text/csv";
        String[] HEADERs = { "Prénom", "Nom", "Email", "Téléphone", "Adresse", "NumPiece", "Référentiel", "Promo", "DateNaissance",  "LieuNaissance", "NumTuteur"};
        List<ApprenantDto> apps = new ArrayList<>();
        if (TYPE.equals(file.getContentType())) {
            try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(fileReader,
                         CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
                Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                for (CSVRecord csvRecord : csvRecords) {
                    ApprenantDto apprenantDto = new ApprenantDto(
                            null,
                            csvRecord.get("prenom"),
                            csvRecord.get("nom"),
                            csvRecord.get("email"),
                            csvRecord.get("phone"),
                            csvRecord.get("adresse"),
                            csvRecord.get("numPiece"),
                            null,
                            ReferentielDto.fromEntity(referentielRepository.findByLibelle(csvRecord.get("referentiel")).get()),
                            PromoDto.fromEntity(promoRepository.findByLibelle(csvRecord.get("promo")).get()),
                            LocalDate.parse(csvRecord.get("dateNaissance")),
                            csvRecord.get("lieuNaissance"),
                            csvRecord.get("numTuteur"),
                            null,
                            new ArrayList<>()
                    );
                    apps.add(apprenantDto);
                }
                return apprenantRepository.saveAll(
                        apps.stream().map(ApprenantDto::toEntity).collect(Collectors.toList())
                ).stream().map(ApprenantDto::fromEntity).collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
            }
        }else {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet = workbook.getSheetAt(0);

                for(int i=0; i<sheet.getPhysicalNumberOfRows();i++) {
                    XSSFRow row = sheet.getRow(i);
                    if (row.getPhysicalNumberOfCells() == 11){
                        if (i == 0){
                            for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
                                if (!Objects.equals(HEADERs[j], row.getCell(j).toString()))
                                    throw new InvalidEntityException("le format du fichier n'est pas bonne", ErrorCodes.APPRENANT_NOT_VALID);
                            }
                        }else {
                            Random random = new Random();
                            if (referentielRepository.findByLibelle(row.getCell(6).toString()).isPresent() && promoRepository.findByLibelle(row.getCell(7).toString()).isPresent()) {
                                String code = promoRepository.findByLibelle(row.getCell(7).toString()).get().getDateDebut().toString().substring(0, 4) + (random.nextInt(9999 - 1000) + 1001);
                                while (apprenantRepository.findByCodeAndArchiveFalse(code).isPresent()) {
                                    code = promoRepository.findByLibelle(row.getCell(7).toString()).get().getDateDebut().toString().substring(0, 4) + (random.nextInt(9999 - 1001) + 1001);
                                }
                                ApprenantDto apprenantDto = new ApprenantDto(
                                        null,
                                        row.getCell(0).toString(),
                                        row.getCell(1).toString(),
                                        row.getCell(2).toString(),
                                        row.getCell(3).toString(),
                                        row.getCell(4).toString(),
                                        row.getCell(5).toString(),
                                        code,
                                        referentielRepository.findByLibelle(row.getCell(6).toString()).isPresent() ?
                                                ReferentielDto.fromEntity(referentielRepository.findByLibelle(row.getCell(6).toString()).get()) : null,
                                        promoRepository.findByLibelle(row.getCell(7).toString()).isPresent() ?
                                                PromoDto.fromEntity(promoRepository.findByLibelle(row.getCell(7).toString()).get()) : null,
                                        row.getCell(8).getLocalDateTimeCellValue().toLocalDate(),
                                        row.getCell(9).toString(),
                                        row.getCell(10).toString(),
                                        null,
                                        new ArrayList<>()
                                );
                                validation(apprenantDto);
                                apps.add(apprenantDto);
                            }else
                                throw new InvalidEntityException("le format du fichier n'est pas bonne : le referentiel ou la promo choisi(e) n'existe pas dans la BDD", ErrorCodes.APPRENANT_NOT_VALID);
                        }

                    }else
                        throw new InvalidEntityException("le format du fichier n'est pas bonne", ErrorCodes.APPRENANT_NOT_VALID);
                }
                return apprenantRepository.saveAll(
                        apps.stream().map(ApprenantDto::toEntity).collect(Collectors.toList())
                ).stream().map(ApprenantDto::fromEntity).collect(Collectors.toList());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        throw new InvalidEntityException("Please upload a csv file!", ErrorCodes.APPRENANT_NOT_VALID);
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
            apprenant.setNumPiece(cni);
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

        if(userAlreadyExistsCni(apprenantDto.getNumPiece(), apprenantDto.getId())) {
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
            user = userRepository.findByNumPiece(cni);
        }else {
            user = userRepository.findByNumPieceAndIdNot(cni, id);
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
