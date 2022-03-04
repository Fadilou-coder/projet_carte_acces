package com.example.projet_carte.repository;

import com.example.projet_carte.model.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StructureRepository extends JpaRepository<Structure, Long> {

    Optional<Structure> findByIdAndArchiveFalse(Long id);

    Optional<Structure> findByNomStructureAndArchiveFalse(String nomStructure);

    List<Structure> findAllByArchiveFalse();
}
