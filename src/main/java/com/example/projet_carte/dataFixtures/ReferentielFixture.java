package com.example.projet_carte.dataFixtures;

        import com.example.projet_carte.model.Referentiel;
        import com.example.projet_carte.repository.ReferentielRepository;
        import lombok.AllArgsConstructor;
        import org.springframework.boot.CommandLineRunner;
        import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
        import org.springframework.core.annotation.Order;
        import org.springframework.stereotype.Component;

        import java.util.Arrays;

@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
@Order(2)
public class ReferentielFixture implements CommandLineRunner {

    private ReferentielRepository referentielRepository;

    @Override
    public void run(String... args) {
        referentielRepository.saveAll(Arrays.asList(
                new Referentiel("Dev web"),
                new Referentiel("Referent Digital"),
                new Referentiel("Data Scientist")
       ));
    }
}
