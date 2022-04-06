package com.example.projet_carte.repository;

import com.example.projet_carte.model.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {

    List<Apprenant> findAllByArchiveFalse();

    Optional<Apprenant> findByIdAndArchiveFalse(Long id);

    Optional<Apprenant> findByCodeAndArchiveFalse(String code);
    List<Apprenant> findByReferentielIdAndArchiveFalse(Long referentiel_id);
    List<Apprenant> findByPromoIdAndArchiveFalse(Long promo_id);
    List<Apprenant> findByPromoIdAndReferentielIdAndArchiveFalse(Long promo_id, Long referentiel_id);

    Optional<Apprenant> findByCodeAndIdNot(String code, Long id);
    Optional<Apprenant> findByNumPiece(String cni);

    Optional<Apprenant> findByEmailAndArchiveFalse(String email);
}
