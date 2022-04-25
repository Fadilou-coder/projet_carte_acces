package com.example.projet_carte.dataFixtures;

import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.Promo;
import com.example.projet_carte.repository.ApprenantRepository;
import com.example.projet_carte.repository.PromoRepository;
import com.example.projet_carte.repository.ReferentielRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
@Order(3)
public class ApprenantFixture implements CommandLineRunner {

    ApprenantRepository apprenantRepository;
    ReferentielRepository referentielRepository;
    PromoRepository promoRepository;

    @Override
    public void run(String... args) throws Exception {

        String password = "password";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        promoRepository.saveAll(
                Arrays.asList(
                        new Promo("Promo 1", LocalDate.parse("2018-01-01"), LocalDate.parse("2018-12-01")),
                        new Promo("Promo 2", LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-01")),
                        new Promo("Promo 3", LocalDate.parse("2020-03-16"), LocalDate.parse("2021-04-01")),
                        new Promo("Promo 4", LocalDate.parse("2021-12-01"), LocalDate.parse("2022-09-01"))
                )
        );

        apprenantRepository.saveAll(Arrays.asList(
                new Apprenant( "Hamidou",  "Ba", "hamidou@gmail.com", encodedPassword, "770000000","adresse", "CNI", "1123123412341", "M", "20180001", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490040", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Khoudia",  "Mbodji", "khoudia@gmail.com", encodedPassword, "770000001","adresse", "CNI", "1123123412342", "F", "20180002", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490039", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Aminata",  "Coulibaly", "aminata@gmail.com", encodedPassword, "770000002","adresse", "CNI", "1123123412343", "F", "20180003", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490038", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Jean",  "Mendy", "jean@gmail.com", encodedPassword, "770000003","adresse", "CNI", "1123123412344", "M", "20180004", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490037", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Ndeye",  "Ba", "ndeya@gmail.com", encodedPassword, "770000004","adresse", "CNI", "1123123412345", "F", "20180005", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490036", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Ibrahima",  "Diop", "ibou@gmail.com", encodedPassword, "770000005","adresse", "CNI", "1123123412346", "M", "20180006", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490029", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Babacar",  "Ngom", "babs@gmail.com", encodedPassword, "770000006","adresse", "CNI", "1123123412347", "M", "20180007", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490035", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Rama",  "Diatta", "diatta@gmail.com", encodedPassword, "770000007","adresse", "CNI", "1123123412348", "F",  "20180008", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490034", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Sidy",  "Dieng", "sidy@gmail.com", encodedPassword, "770000008","adresse", "CNI", "1123123412349", "M", "20180009", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490033", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Fatou",  "Mbaye", "mbaye@gmail.com", encodedPassword, "770000009","adresse", "CNI", "1123123412310", "F",  "20180010", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490032", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Bousso",  "Gueye", "bousso@gmail.com", encodedPassword, "770000010","adresse", "CNI", "1123223412311", "F",  "20180011", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490031", promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Moussa",  "Gueye", "gueye30@gmail.com", encodedPassword, "770000011","adresse", "CNI", "1123123412312", "M", "20190011", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490030", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Papa",  "Camara", "papa28"+"@gmail.com", encodedPassword, "770000012","adresse", "CNI", "1173123412313", "M", "20190012", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490028", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Abou",  "Sarr", "abou27@gmail.com", encodedPassword, "770000013","adresse", "CNI", "1173123412314", "M", "20190013", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490027", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Fatim",  "Sall", "fatim26@gmail.com", encodedPassword, "770000014","adresse", "CNI", "1173123412315", "F",  "20190014", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490026", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Ndeye Fatou",  "Sall", "ndye25@gmail.com", encodedPassword, "770000015","adresse", "CNI", "1173123412316", "F",  "20190015", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490025", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Idrissa",  "Sarr", "idy24@gmail.com", encodedPassword, "770000016","adresse", "CNI", "1173123412317", "M", "20190016", referentielRepository.findByLibelle("Referent Digital").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490024", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Khadim",  "Diouf", "bamba23@gmail.com", encodedPassword, "770000017","adresse", "CNI", "1173123412318", "M", "20190017", referentielRepository.findByLibelle("Referent Digital").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490023", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Fatima",  "Sy", "tyma22@gmail.com", encodedPassword, "770000018","adresse", "CNI", "1173123412319", "F",  "20190018", referentielRepository.findByLibelle("Referent Digital").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490022", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Mamadou",  "Mall", "momo21@gmail.com", encodedPassword, "770000019","adresse", "CNI", "1123123412110", "M", "20190019", referentielRepository.findByLibelle("Data Scientist").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490021", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Abdoulaye",  "Lo", "laye20@gmail.com", encodedPassword, "770000020","adresse", "CNI", "1123123412311", "M", "20190001", referentielRepository.findByLibelle("Data Scientist").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490020", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Ousmane",  "Tall", "ablaye19@gmail.com", encodedPassword, "770000021","adresse", "CNI", "1123103412312", "M", "20190001", referentielRepository.findByLibelle("Data Scientist").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490019", promoRepository.findAllByArchiveFalse().get(1)),
                new Apprenant( "Faly",  "Gueye", "faly18@gmail.com", encodedPassword, "770000022","adresse", "CNI", "1123123412313", "F",  "20180012", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490018",promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Ndiolé",  "Bousso", "ndiole@gmail.com", encodedPassword, "770000023","adresse", "CNI", "1123123412314", "F",  "20180013", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490017",promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Alioune",  "Diallo", "luno@gmail.com", encodedPassword, "770000024","adresse", "CNI", "1123123412315", "M", "20180014", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490016",promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Daouda",  "Gueye", "deve@gmail.com", encodedPassword, "770000026","adresse", "CNI", "1123123412316", "M", "20180015", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490015",promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Fallou",  "Fall", "louf@gmail.com", encodedPassword, "770000027","adresse", "CNI", "1123123412317", "M", "20180016", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490014",promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Fatou",  "Sylla", "fatou13@gmail.com", encodedPassword, "770000028","adresse", "CNI", "1123123412318", "F",  "20180017", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490013",promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Mareme",  "Goudiaby", "mareme12@gmail.com", encodedPassword, "770000029","adresse", "CNI", "1123123412319", "F",  "20180018", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490012",promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Bassirou",  "Hanne", "bassirou11@gmail.com", encodedPassword, "770000030","adresse", "CNI", "1123123412320", "M", "20220001", referentielRepository.findAll().get(0), LocalDate.parse("1990-02-21"),
                        "Thies", "771490011",promoRepository.findAllByArchiveFalse().get(3)),
                new Apprenant( "Ndeye",  "Dieng", "ndeye10@gmail.com", encodedPassword, "770000031","adresse", "CNI", "1123123412321", "F",  "20180019", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490010",promoRepository.findAllByArchiveFalse().get(0)),
                new Apprenant( "Awa",  "Fall", "awa009@gmail.com", encodedPassword, "770000032","adresse", "CNI", "1123123412322", "F",  "20220002", referentielRepository.findAll().get(1), LocalDate.parse("1990-02-21"),
                        "Thies", "771490009",promoRepository.findAllByArchiveFalse().get(3)),
                new Apprenant( "Ngoné",  "Badiane", "ngone008@gmail.com", encodedPassword, "770000033","adresse", "CNI", "1123123412323", "F",  "20220003", referentielRepository.findAll().get(2), LocalDate.parse("1990-02-21"),
                        "Thies", "771490008",promoRepository.findAllByArchiveFalse().get(3)),
                new Apprenant( "Pape Modou",  "Touré", "pmodou@gmail.com", encodedPassword, "770000034","adresse", "CNI", "1123123412324", "M", "20200001", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490007", promoRepository.findAllByArchiveFalse().get(2)),
                new Apprenant( "Moussa",  "Mané", "moussamane@gmail.com", encodedPassword, "770000035","adresse", "CNI", "1123123412325", "M", "20200002", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490006", promoRepository.findAllByArchiveFalse().get(2)),
                new Apprenant( "Oumar",  "Sy", "syoumar@gmail.com", encodedPassword, "770000036","adresse", "CNI", "1123123412326", "M", "20200003", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490005", promoRepository.findAllByArchiveFalse().get(2)),
                new Apprenant( "Khadidiatou",  "Sy", "khady005@gmail.com", encodedPassword, "770000037","adresse", "CNI", "1123123412327", "F", "20200004", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490004", promoRepository.findAllByArchiveFalse().get(2)),
                new Apprenant( "Souleymane",  "fall", "fallsouleymane@gmail.com", encodedPassword, "770000038","adresse", "CNI", "1123123412328", "M", "20200005", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490003", promoRepository.findAllByArchiveFalse().get(2)),
                new Apprenant( "Aliou",  "Sow", "ailoune@gmail.com", encodedPassword, "770000039","adresse", "CNI", "1123123412329", "M", "20200006", referentielRepository.findByLibelle("Dev web").get(), LocalDate.parse("1990-02-21"),
                        "Thies", "771490002", promoRepository.findAllByArchiveFalse().get(2)),
                new Apprenant( "Mohamed",  "Ndao", "ahmed0001@gmail.com", encodedPassword, "770000040","adresse", "CNI", "1123123412330", "M", "20220004", referentielRepository.findAll().get(0), LocalDate.parse("1990-02-21"),
                        "Thies", "771490001", promoRepository.findAllByArchiveFalse().get(3))
        ));
    }
}
