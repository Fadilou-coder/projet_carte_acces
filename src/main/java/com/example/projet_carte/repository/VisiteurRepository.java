package com.example.projet_carte.repository;

import com.example.projet_carte.model.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
}
