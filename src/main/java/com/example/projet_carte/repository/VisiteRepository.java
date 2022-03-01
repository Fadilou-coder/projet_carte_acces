package com.example.projet_carte.repository;

import com.example.projet_carte.model.Visites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisiteRepository  extends JpaRepository<Visites, Long> {
}
