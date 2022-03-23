package com.example.projet_carte.repository;

        import com.example.projet_carte.model.Referentiel;
        import org.springframework.data.jpa.repository.JpaRepository;
        import java.util.List;
        import java.util.Optional;


public interface ReferentielRepository extends JpaRepository<Referentiel, Long> {
        List<Referentiel> findAll();

        Optional<Referentiel> findByLibelle(String libelle);
}
