package com.example.projet_carte.repository;

import com.example.projet_carte.model.Superviseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuperviseurRepository extends JpaRepository<Superviseur, Long> {
    Optional<Superviseur> findByEmailAndArchiveFalse(String username);

    Optional<Superviseur> findByEmailAndIdNot(String username, Long id);

    Optional<Superviseur> findByNumPiece(String numPiece);

    Optional<Superviseur> findByNumPieceAndIdNot(String numPiece, Long id);

    List<Superviseur> findAllByArchiveFalse();

    Optional<Superviseur> findByPhoneAndIdNot(String phone, Long id);
}