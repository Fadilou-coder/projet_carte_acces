package com.example.projet_carte.dataFixtures;

import com.example.projet_carte.model.Structure;
import com.example.projet_carte.repository.StructureRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
@Order(1)
public class StuctureFixture implements CommandLineRunner{

    private StructureRepository structureRepository;

    @Override
    public void run(String... args) {
        structureRepository.save(new Structure("SAGAM"));
    }
}
