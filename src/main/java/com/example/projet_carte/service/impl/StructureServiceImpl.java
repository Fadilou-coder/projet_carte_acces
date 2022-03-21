package com.example.projet_carte.service.impl;

import com.example.projet_carte.dto.StructureDto;
import com.example.projet_carte.exception.EntityNotFoundException;
import com.example.projet_carte.exception.InvalidEntityException;
import com.example.projet_carte.model.Structure;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.StructureRepository;
import com.example.projet_carte.service.StructureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class StructureServiceImpl implements StructureService {

    private StructureRepository structureRepository;
    private AdminRepository adminRepository;

    @Override
    public List<StructureDto> findAll() {
        return structureRepository.findAllByArchiveFalse().stream().map(StructureDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public StructureDto save(StructureDto structureDto) throws IOException {
        if (Objects.equals(structureDto.getNomStructure(), "") && structureDto.getNomStructure() == null) {
            throw new InvalidEntityException("Veuillez renseigner le nom de la structure");
        }
        return StructureDto.fromEntity(structureRepository.save(StructureDto.toEntity(structureDto)));
    }

    @Override
    public StructureDto findById(Long id) {
        if (id == null) return null;
        return structureRepository.findByIdAndArchiveFalse(id).map(StructureDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Structure avec l'ID = " + id + " ne se trouve dans la BDD")
        );
    }

    @Override
    public StructureDto put(StructureDto structureDto, Long id) throws IOException {
        if (id == null) return null;
        Structure structure = structureRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun Structure avec l'ID = " + id + " ne se trouve dans la BDD"));
        if (!Objects.equals(structureDto.getNomStructure(), "") && structureDto.getNomStructure() == null){
            structure.setNomStructure(structureDto.getNomStructure());
        }
        structureRepository.flush();
        return structureDto;
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            Structure structure = structureRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                    new EntityNotFoundException(
                            "Aucun Structure avec l'ID = " + id + " ne se trouve dans la BDD"));

            structure.setArchive(true);
            structure.getAdmins().forEach(admin -> {
                admin.setArchive(true);
            });
            structureRepository.flush();
            adminRepository.flush();
        }
    }

    @Override
    public void debloquerStructure(Long id) {
        if (id != null) {
            Structure structure = structureRepository.findByIdAndArchiveFalse(id).orElseThrow(() ->
                    new EntityNotFoundException(
                            "Aucun Structure avec l'ID = " + id + " ne se trouve dans la BDD"));

            structure.setArchive(false);
            structure.getAdmins().forEach(admin -> {
                admin.setArchive(false);
            });
            structureRepository.flush();
            adminRepository.flush();
        }
    }
}
