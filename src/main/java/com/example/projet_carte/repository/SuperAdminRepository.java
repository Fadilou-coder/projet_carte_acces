package com.example.projet_carte.repository;

import com.example.projet_carte.model.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    Optional<SuperAdmin> findByEmailAndArchiveFalse(String username);
    Optional<SuperAdmin> findByEmailAndIdNot(String username, Long id);
    Optional<SuperAdmin> findByNumPiece(String numPiece);
    Optional<SuperAdmin> findByNumPieceAndIdNot(String numPiece, Long id);
    List<SuperAdmin> findAllByArchiveFalse();
    Optional<SuperAdmin> findByPhoneAndIdNot(String phone, Long id);
}
