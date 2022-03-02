package com.example.projet_carte.repository;

import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.Visites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisiteRepository  extends JpaRepository<Visites, Long> {
    List<Visites> findAllByDateEntree(LocalDate dateEntree);
    List<Visites> findAllByApprenantIsNotNull();
    List<Visites> findAllByVisiteurIsNotNull();
}
