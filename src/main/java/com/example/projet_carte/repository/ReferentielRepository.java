package com.example.projet_carte.repository;

        import com.example.projet_carte.model.Referentiel;
        import org.springframework.data.jpa.repository.JpaRepository;
        import java.util.List;


public interface ReferentielRepository extends JpaRepository<Referentiel, Long> {
        List<Referentiel> findAll();
}
