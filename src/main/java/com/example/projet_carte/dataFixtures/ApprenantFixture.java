package com.example.projet_carte.dataFixtures;

import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.repository.ApprenantRepository;
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

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 15; i++) {
            apprenantRepository.save(
                    new Apprenant( "prenom"+i,  "nom"+i, "apprenant"+i+"@gmail.com","77000000"+i,"adresse"+i, "125456789010"+i, "2020035"+i,"dev web", LocalDate.parse("1990-02-21"),
                    "Thies", "77149000"+i)
            );
        }
    }
}
