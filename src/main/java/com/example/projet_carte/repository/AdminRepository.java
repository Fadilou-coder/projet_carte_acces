package com.example.projet_carte.repository;

import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findAllByArchiveFalse();
    Optional<Admin> findByIdAndArchiveFalse(Long id);

    Optional<Admin> findByUsernameAndArchiveFalse(String username);
    Optional<Admin> findByUsernameAndIdNot(String username, Long id);

    Optional<Admin> findByEmailAndIdNot(String email, Long id);
    Optional<Admin> findByPhoneAndIdNot(String num, Long id);
    Optional<Admin> findByCniAndIdNot(String cni, Long id);

    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByPhone(String num);
    Optional<Admin> findByCni(String cni);
    Optional<Admin> findByUsername(String username);
}
