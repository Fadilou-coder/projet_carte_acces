package com.example.projet_carte.repository;

import com.example.projet_carte.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByApprenantId(Long apprenant_id);
}
