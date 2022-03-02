package com.example.projet_carte.repository;

import com.example.projet_carte.model.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    Optional<SuperAdmin> findByUsernameAndArchiveFalse(String username);
    Optional<SuperAdmin> findByUsernameAndIdNot(String username, Long id);
}
