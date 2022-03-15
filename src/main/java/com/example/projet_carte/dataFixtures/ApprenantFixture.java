package com.example.projet_carte.dataFixtures;

import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.repository.ApprenantRepository;
import com.example.projet_carte.repository.ReferentielRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
@Order(3)
public class ApprenantFixture implements CommandLineRunner {

    ApprenantRepository apprenantRepository;
    ReferentielRepository referentielRepository;

    @Override
    public void run(String... args) throws Exception {
        String password = "password";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        for (int i = 0; i < 15; i++) {
            apprenantRepository.save(
                    new Apprenant( "prenom"+i,  "nom"+i, "apprenant"+i+"@gmail.com","77000000"+i,"adresse"+i, "1 254 5678 9010"+i, "2020035"+i, referentielRepository.findAll().get(0), LocalDate.parse("1990-02-21"),
                    "Thies", "77149000"+i)
            );
        }

       /* for (int i = 0; i < 15; i++) {
            apprenantRepository.save(
                    new Apprenant("apprenant"+i, encodedPassword, "prenom"+i,
                            "nom"+i, "apprenant"+i+"@gmail.com",
                            "1 254 5678 9010"+i, "adresse"+i, "77000000"+i, "EN COURS",
                            "DEVWEB2020-12920001", "APPRENANT", LocalDate.parse("1990-02-21"),
                            "motif"+i, "genre"+i, "niveauEntree"+i, "handicap"+i, "orphelin"+i, "etablissementPrecedent"+i
                    )
            );
        }*/
    }
}
