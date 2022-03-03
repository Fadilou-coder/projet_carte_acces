package com.example.projet_carte.repository;

import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.Visites;
import com.example.projet_carte.model.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisiteRepository  extends JpaRepository<Visites, Long> {
    List<Visites> findByDateEntreeBetween(LocalDateTime dateEntree, LocalDateTime dateEntree2);
    List<Visites> findAllByApprenantIsNotNull();
    List<Visites> findAllByVisiteurIsNotNull();
    Optional<Visites> findByDateEntreeBetweenAndApprenantAndVisiteur(LocalDateTime dateEntree, LocalDateTime dateEntree2, Apprenant apprenant, Visiteur visiteur);
}
