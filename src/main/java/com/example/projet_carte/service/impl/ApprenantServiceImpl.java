package com.example.projet_carte.service.impl;

import com.example.projet_carte.EmailSenderService;
import com.example.projet_carte.dto.ApprenantDto;
import com.example.projet_carte.dto.CommentaireDto;
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
import com.example.projet_carte.model.Promo;
import com.example.projet_carte.model.Visites;
import com.example.projet_carte.repository.*;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ApprenantServiceImpl implements ApprenantService {
    ApprenantRepository apprenantRepository;
    UserRepository userRepository;
    ReferentielRepository referentielRepository;
    PromoRepository promoRepository;
    CommentaireRepository commentaireRepository;
    VisiteRepository visiteRepository;
    EmailSenderService emailSenderService;


    @Override
    public List<ApprenantDto> findAll() {
        return apprenantRepository.findAllByArchiveFalse().stream()
                .map(ApprenantDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprenantDto> findSanctionner(Long id) {
        if (id == null) return null;
        promoRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(
                "Aucun apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                ErrorCodes.PROMO_NOT_FOUND));
        AtomicInteger nbrAbs = new AtomicInteger();
        AtomicInteger nbrRtd = new AtomicInteger();
        List<Apprenant> apps = new ArrayList<>();
        apprenantRepository.findByPromoIdAndArchiveFalse(id).forEach(apprenant -> {
            nbrAbs.set(findNbrAbscences(apprenant.getId(), LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1), LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue() + 1, 1)));
            nbrRtd.set(findNbrRetard(apprenant.getId(), LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1), LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue() + 1, 1)));
            if (nbrAbs.get() >= 3 || nbrRtd.get() >= 60)
                apps.add(apprenant);
        });

        return apps.stream().map(ApprenantDto::fromEntity).collect(Collectors.toList());
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
    public ApprenantDto save(String prenom, String nom, String email, String phone, String adresse, String typePiece, String sexe, String numPiece, String referentiel, String promo, String dateNaissance, String lieuNaissance, String numTuteur, MultipartFile avatar) throws IOException {

        Random random = new Random();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (referentielRepository.findByLibelle(referentiel).isPresent() && promoRepository.findByLibelle(promo).isPresent()) {
            String code = promoRepository.findByLibelle(promo).get().getDateDebut().toString().substring(0, 4) + (random.nextInt(9999 - 1000)+ 1001);
            while(apprenantRepository.findByCodeAndArchiveFalse(code).isPresent()){
                code = promoRepository.findByLibelle(promo).get().getDateDebut().toString().substring(0, 4) + (random.nextInt(9999 - 1001) + 1001);
            }
            ApprenantDto apprenantDto = new ApprenantDto(
                    null, prenom, nom, email, encoder.encode("password"), phone, adresse, typePiece, sexe, numPiece, code,
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
        String[] HEADERs = { "Prénom", "Nom", "Email", "Téléphone", "Adresse", "TypePiece", "NumPiece", "Sexe", "Référentiel", "Promo", "DateNaissance",  "LieuNaissance", "NumTuteur"};
        List<ApprenantDto> apps = new ArrayList<>();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (TYPE.equals(file.getContentType())) {
            try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(fileReader,
                         CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
                Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                for (CSVRecord csvRecord : csvRecords) {
                    ApprenantDto apprenantDto = new ApprenantDto(
                            null,
                            csvRecord.get("prenom"),
                            csvRecord.get("nom"),
                            csvRecord.get("email"),
                            encoder.encode("password"),
                            csvRecord.get("phone"),
                            csvRecord.get("adresse"),
                            csvRecord.get("typePiece"),
                            csvRecord.get("sexe"),
                            csvRecord.get("numPiece"),
                            null,
                            referentielRepository.findByLibelle(csvRecord.get("referentiel")).isPresent() ?
                            ReferentielDto.fromEntity(referentielRepository.findByLibelle(csvRecord.get("referentiel")).get()) : null,
                            promoRepository.findByLibelle(csvRecord.get("promo")).isPresent() ?
                            PromoDto.fromEntity(promoRepository.findByLibelle(csvRecord.get("promo")).get()) : null,
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
                    if (row.getPhysicalNumberOfCells() == 13){
                        if (i == 0){
                            for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
                                if (!Objects.equals(HEADERs[j], row.getCell(j).toString()))
                                    throw new InvalidEntityException("le format du fichier n'est pas bonne ", ErrorCodes.APPRENANT_NOT_VALID);
                            }
                        }else {
                            Random random = new Random();
                            if (referentielRepository.findByLibelle(row.getCell(8).toString()).isPresent() && promoRepository.findByLibelle(row.getCell(9).toString()).isPresent()) {
                                String code = promoRepository.findByLibelle(row.getCell(9).toString()).get().getDateDebut().toString().substring(0, 4) + (random.nextInt(9999 - 1000) + 1001);

                                while (apprenantRepository.findByCodeAndArchiveFalse(code).isPresent()) {
                                    code = promoRepository.findByLibelle(row.getCell(9).toString()).get().getDateDebut().toString().substring(0, 4) + (random.nextInt(9999 - 1001) + 1001);
                                }
                                ApprenantDto apprenantDto = new ApprenantDto(
                                        null,
                                        row.getCell(0).toString(),
                                        row.getCell(1).toString(),
                                        row.getCell(2).toString(),
                                        encoder.encode("password"),
                                        row.getCell(3).toString(),
                                        row.getCell(4).toString(),
                                        row.getCell(5).toString(),
                                        row.getCell(7).toString(),
                                        row.getCell(6).toString(),
                                        code,
                                        referentielRepository.findByLibelle(row.getCell(8).toString()).isPresent() ?
                                                ReferentielDto.fromEntity(referentielRepository.findByLibelle(row.getCell(8).toString()).get()) : null,
                                        promoRepository.findByLibelle(row.getCell(9).toString()).isPresent() ?
                                                PromoDto.fromEntity(promoRepository.findByLibelle(row.getCell(9).toString()).get()) : null,
                                        !row.getCell(10).toString().isEmpty() ? LocalDate.parse(row.getCell(10).toString()) : null,
                                        row.getCell(11).toString(),
                                        row.getCell(12).toString(),
                                        null,
                                        new ArrayList<>()
                                );
                                validation(apprenantDto);
                                apps.add(apprenantDto);
                            }else
                                throw new InvalidEntityException("le format du fichier n'est pas bonne : le referentiel ou la promo choisi(e) n'existe pas dans la BDD: "+ row.getCell(8).toString() + " " + row.getCell(9).toString(), ErrorCodes.APPRENANT_NOT_VALID);
                        }

                    }else
                        throw new InvalidEntityException("le format du fichier n'est pas bonne " , ErrorCodes.APPRENANT_NOT_VALID);
                }
                return apprenantRepository.saveAll(
                        apps.stream().map(ApprenantDto::toEntity).collect(Collectors.toList())
                ).stream().map(ApprenantDto::fromEntity).collect(Collectors.toList());
            } catch (IOException e) {
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
        String body = "Bonjour " + prenom + " " + nom + " vous trouverez ci dessous votre carte d'access. "+ System.getProperty("line.separator") + System.getProperty("line.separator") + "Cordialement";
        emailSenderService.sendEmailInlineImage("Orange Digital Center", body, email, convFile);
    }

    @Override
    public ApprenantDto findById(Long id) {
        if (id == null) {
            return null;
        }
        return apprenantRepository.findByIdAndArchiveFalse(id).map(ApprenantDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND)
        );
    }

    @Override
    public ApprenantDto findByCode(String code) {
        if (code == null) {
            return null;
        }
        return apprenantRepository.findByCodeAndArchiveFalse(code).map(ApprenantDto::fromEntity).orElseThrow(() ->
            new EntityNotFoundException(
                "Aucun Apprenant avec le code = " + code + " ne se trouve dans la BDD",
                ErrorCodes.APPRENANT_NOT_FOUND)
        );
    }

    @Override
    public ApprenantDto put(Long id, String prenom, String nom, String email, String phone, String adresse,  String typePiece, String numPiece, String dateNaissance, String lieuNaissance, String numTuteur, MultipartFile avatar) throws IOException {
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
        if (!Objects.equals(numPiece, "")) {
            apprenant.setNumPiece(numPiece);
        }

        if (!Objects.equals(typePiece, "")) {
            apprenant.setTypePiece(typePiece);
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
    public ApprenantDto putFieldApp(Long id, ApprenantDto apprenantDto){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (id == null) {
            return null;
        }
        Apprenant apprenant = apprenantRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND));
        if (!Objects.equals(apprenantDto.getPrenom(), "") && apprenantDto.getPrenom() != null) {
            apprenant.setPrenom(apprenantDto.getPrenom());
        }

        if (!Objects.equals(apprenantDto.getNom(), "") && apprenantDto.getNom() != null) {
            apprenant.setNom(apprenantDto.getNom());
        }
        if (!Objects.equals(apprenantDto.getEmail(), "") && apprenantDto.getEmail() != null) {
            apprenant.setEmail(apprenantDto.getEmail());
        }
        if (!Objects.equals(apprenantDto.getPhone(), "") && apprenantDto.getPhone() != null) {
            apprenant.setPhone(apprenantDto.getPhone());
        }
        if (!Objects.equals(apprenantDto.getAddresse(), "") && apprenantDto.getAddresse() != null) {
            apprenant.setAdresse(apprenantDto.getAddresse());
        }
        if (!Objects.equals(apprenantDto.getNumPiece(), "") && apprenantDto.getNumPiece() != null) {
            apprenant.setNumPiece(apprenantDto.getNumPiece());
        }

        if (!Objects.equals(apprenantDto.getTypePiece(), "") && apprenantDto.getTypePiece() != null) {
            apprenant.setTypePiece(apprenantDto.getTypePiece());
        }

        if (apprenantDto.getDateNaissance() != null) {
            apprenant.setDateNaissance(apprenantDto.getDateNaissance());
        }
        if (!Objects.equals(apprenantDto.getLieuNaissance(), "") && apprenantDto.getLieuNaissance() != null) {
            apprenant.setLieuNaissance(apprenantDto.getLieuNaissance());
        }
        if (!Objects.equals(apprenantDto.getNumTuteur(), "") && apprenantDto.getNumTuteur() != null) {
            apprenant.setNumTuteur(apprenantDto.getNumTuteur());
        }

        if (!Objects.equals(apprenantDto.getPassword(), "") && apprenantDto.getPassword() != null) {
            apprenant.setPassword(encoder.encode(apprenantDto.getPassword()));
        }

        validation(ApprenantDto.fromEntity(apprenant));
        apprenantRepository.flush();
        return ApprenantDto.fromEntity(apprenant);
    }

    @Override
    public ApprenantDto putImageApp(Long id, MultipartFile file) throws IOException {

        if (id == null) {
            return null;
        }
        Apprenant apprenant = apprenantRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND));

        if (!file.isEmpty()) {
            apprenant.setAvatar(file.getBytes());
        }

        ApprenantDto apprenantDto = ApprenantDto.fromEntity(apprenant);
        validation(apprenantDto);

        apprenantRepository.flush();
        return apprenantDto;
    }

    @Override
    public Integer findNbrAbscences(Long id, LocalDate dateDebut, LocalDate dateFin) {
        Integer nbr = 0;
        if (id == null) {
            return null;
        }
        Apprenant apprenant = apprenantRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND));
        if (Duration.between(dateDebut.atStartOfDay(), dateFin.atStartOfDay()).toDays() < 0)
            throw new InvalidEntityException("Verifier les dates choisis", ErrorCodes.APPRENANT_NOT_VALID,
                    Collections.singletonList("La date de debut est plus avanceée que la date fin"));

        if (Duration.between(dateDebut.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() < 0 || (apprenant.getPromo().getDateFin() != null && Duration.between(dateDebut.atStartOfDay(), apprenant.getPromo().getDateFin().atStartOfDay()).toDays() < 0)){
            return 0;
        }

        LocalDate db = dateDebut;
        if (Duration.between(dateDebut.atStartOfDay(), apprenant.getPromo().getDateDebut().atStartOfDay()).toDays() > 0) {
            db = apprenant.getPromo().getDateDebut();
        }

        if (Duration.between(dateFin.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() < 0) dateFin = LocalDate.now().plusDays(1);
    for (LocalDate i = db; i.getDayOfMonth() < dateFin.getDayOfMonth();  i = i.plusDays(1)){
            if (!visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(i.atStartOfDay(),
                    i.plusDays(1).atStartOfDay(), apprenant, null).isPresent())
                nbr++;
            else{
                Visites visites = visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(i.atStartOfDay(),
                        i.plusDays(1).atStartOfDay(), apprenant, null).get();
                if (Duration.between(LocalDateTime.of(i.atStartOfDay().getYear(), i.atStartOfDay().getMonthValue(),
                        i.atStartOfDay().getDayOfMonth(),
                        8, 15, 0), visites.getDateEntree()).toMinutes() > 15)
                    nbr++;
            }
        }
        return nbr;
    }

    @Override
    public Integer findNbrRetard(Long id, LocalDate dateDebut, LocalDate dateFin) {
        int nbr = 0;
        if (id == null) {
            return null;
        }
        Apprenant apprenant = apprenantRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun apprenant avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.APPRENANT_NOT_FOUND));
        if (Duration.between(dateDebut.atStartOfDay(), dateFin.atStartOfDay()).toDays() < 0)
            throw new InvalidEntityException("Verifier les dates choisis", ErrorCodes.APPRENANT_NOT_VALID,
                    Collections.singletonList("La date de debut est plus avanceée que la date fin"));

        if (Duration.between(dateDebut.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() < 0 || (apprenant.getPromo().getDateFin() != null && Duration.between(dateDebut.atStartOfDay(), apprenant.getPromo().getDateFin().atStartOfDay()).toDays() < 0))
            return 0;
        LocalDate db = dateDebut;
        if (Duration.between(dateDebut.atStartOfDay(), apprenant.getPromo().getDateDebut().atStartOfDay()).toDays() > 0) {
            db = apprenant.getPromo().getDateDebut();
        }
        if (Duration.between(dateFin.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() < 0) dateFin = LocalDate.now().plusDays(1);
        for (LocalDate i = db; i.getDayOfMonth() < dateFin.getDayOfMonth();  i = i.plusDays(1)){
            if (visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(i.atStartOfDay(),
                    i.plusDays(1).atStartOfDay(), apprenant, null).isPresent()) {
                Visites visites = visiteRepository.findByDateEntreeBetweenAndApprenantAndVisiteur(i.atStartOfDay(),
                        i.plusDays(1).atStartOfDay(), apprenant, null).get();
                if (Duration.between(LocalDateTime.of(i.atStartOfDay().getYear(), i.atStartOfDay().getMonthValue(),
                        i.atStartOfDay().getDayOfMonth(),
                        8, 15, 0), visites.getDateEntree()).toMinutes() <= 15)
                    nbr = nbr + (int) Duration.between(LocalDateTime.of(i.atStartOfDay().getYear(),
                                    i.atStartOfDay().getMonthValue(),
                                    i.atStartOfDay().getDayOfMonth(),
                                    8, 15, 0), visites.getDateEntree()).toMinutes();
            }
        }
        return nbr;
    }

    @Override
    public Integer findNbrAbscencesAllApp(Long id, LocalDate dateDebut, LocalDate dateFin) {
        if (id == null) {
            return null;
        }
        Promo promo = promoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun promo avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.PROMO_NOT_FOUND));
        AtomicReference<Integer> nbr = new AtomicReference<>(0);
        if (Duration.between(dateFin.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() < 0) dateFin = LocalDate.now().plusDays(1);
        LocalDate finalDateFin = dateFin;
        promo.getApprenants().forEach(apprenant -> nbr.getAndSet(nbr.get() + findNbrAbscences(apprenant.getId(), dateDebut, finalDateFin)));

        return nbr.get();
    }

    @Override
    public Integer findNbrRetardAllApp(Long id, LocalDate dateDebut, LocalDate dateFin) {
        if (id == null) {
            return null;
        }
        Promo promo = promoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun promo avec l'ID = " + id + " ne se trouve dans la BDD",
                        ErrorCodes.PROMO_NOT_FOUND));
        AtomicReference<Integer> nbr = new AtomicReference<>(0);
        if (Duration.between(dateFin.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() < 0) dateFin = LocalDate.now().plusDays(1);
        LocalDate finalDateFin = dateFin;
        promo.getApprenants().forEach(apprenant -> nbr.getAndSet(nbr.get() + findNbrRetard(apprenant.getId(), dateDebut, finalDateFin)));

        return nbr.get();
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

    @Override
    public CommentaireDto addComment(CommentaireDto commentaire) {
        if (commentaire.getCommentaire().isEmpty()){
            throw new InvalidEntityException("Veuillez Saisir quelque chose", ErrorCodes.APPRENANT_NOT_FOUND,
                    Collections.singletonList("Veuillez Saisir quelque chose"));
        }else if (!apprenantRepository.findById(commentaire.getApprenant().getId()).isPresent()){
            throw new InvalidEntityException("l'apprenant choisi n'existe pas dans la base de BDD", ErrorCodes.APPRENANT_NOT_FOUND,
                    Collections.singletonList("l'apprenant choisi n'existe pas dans la base de BDD"));
        }
        commentaire.setDate(LocalDate.now());
        return CommentaireDto.fromEntity(commentaireRepository.save(CommentaireDto.toEntity(commentaire)));
    }

    @Override
    public List<CommentaireDto> commentsApp(Long id) {
        return commentaireRepository.findByApprenantId(id).stream().map(CommentaireDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<CommentaireDto> findAllComments() {
        return commentaireRepository.findAll().stream()
            .map(CommentaireDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<CommentaireDto> commentsByDate(LocalDate date) {
        return commentaireRepository.findByDate(date).stream()
            .map(CommentaireDto::fromEntity)
            .collect(Collectors.toList());
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
            throw new InvalidEntityException("Un autre utilisateur avec le meme num de Piece existe deja", ErrorCodes.APPRENANT_ALREADY_IN_USE,
                    Collections.singletonList("Un autre utilisateur avec le meme num de Piece existe deja dans la BDD"));
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
