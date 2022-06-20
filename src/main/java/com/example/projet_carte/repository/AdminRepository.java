package com.example.projet_carte.repository;

import com.example.projet_carte.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findAll();
    Optional<Admin> findByIdAndArchiveFalse(Long id);
    Optional<Admin> findById(Long id);

    Optional<Admin> findByEmailAndArchiveFalse(String email);

    Optional<Admin> findByEmailAndIdNot(String email, Long id);
    Optional<Admin> findByPhoneAndIdNot(String num, Long id);
    Optional<Admin> findByNumPieceAndIdNot(String numPiece, Long id);
    Optional<Admin> findByNumPiece(String cni);
}
