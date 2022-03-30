package com.example.projet_carte.repository;

import com.example.projet_carte.model.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    List<Promo> findAllByArchiveFalse();
    Optional<Promo> findByLibelle(String libelle);
}
