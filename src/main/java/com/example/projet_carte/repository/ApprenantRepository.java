package com.example.projet_carte.repository;

import com.example.projet_carte.model.Apprenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {
    Optional<Apprenant> findByCni(String cni);
}
