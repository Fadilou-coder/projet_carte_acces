package com.example.projet_carte.repository;

import com.example.projet_carte.model.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
    Optional<Visiteur> findByCni(String cni);
    Optional<Visiteur> findVisiteurByNumTelephone(String numTelephone);
}
