package com.example.projet_carte.dataFixtures;

import com.example.projet_carte.model.Visiteur;
import com.example.projet_carte.repository.VisiteurRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
@Order(4)
public class VisiteurFixtures implements CommandLineRunner {

    VisiteurRepository visiteurRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 15; i++) {
                visiteurRepository.save(
                        new Visiteur("prenom_viteur"+i+1, "nom_visiteur"+i+1, "100000000000"+i, "77000000"+i)
                );
        }
    }
}
