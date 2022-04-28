package com.example.projet_carte.dataFixtures;

import com.example.projet_carte.model.Visites;
import com.example.projet_carte.model.Visiteur;
import com.example.projet_carte.repository.ApprenantRepository;
import com.example.projet_carte.repository.VisiteRepository;
import com.example.projet_carte.repository.VisiteurRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
@Order(5)
public class VisitesFixtures implements CommandLineRunner {

    VisiteRepository visiteReposity;
    ApprenantRepository apprenantRepository;
    VisiteurRepository visiteurRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 15; i++) {
            visiteReposity.save(
                    new Visites(visiteurRepository.findAll().get(i), null)
            );
        }
        for (int i = 0; i < 15; i++) {
            visiteReposity.save(
                    new Visites(null, apprenantRepository.findAll().get(i))
            );
        }
    }
}
