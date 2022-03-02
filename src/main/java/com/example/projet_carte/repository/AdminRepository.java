package com.example.projet_carte.repository;

import com.example.projet_carte.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsernameAndArchiveFalse(String username);
    Optional<Admin> findByUsernameAndIdNot(String username, Long id);
}
