package com.example.projet_carte.repository;

import com.example.projet_carte.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Personne, Long> {
    Optional<Personne> findByEmailAndIdNot(String email, Long id);
    Optional<Personne> findByPhoneAndIdNot(String num, Long id);
    Optional<Personne> findByNumPieceAndIdNot(String cni, Long id);

    Optional<Personne> findByEmail(String email);
    Optional<Personne> findByPhone(String num);
    Optional<Personne> findByNumPiece(String cni);
}
