package com.example.projet_carte.service;

import com.example.projet_carte.dto.AdminDto;
import com.example.projet_carte.dto.StructureDto;

import java.io.IOException;
import java.util.List;

public interface StructureService {
    List<StructureDto> findAll();

    StructureDto save(StructureDto structureDto) throws IOException;

    StructureDto findById(Long id);

    List<AdminDto> findAdminByStructure(Long id);

    StructureDto put(StructureDto structureDto, Long id) throws IOException;

    void delete(Long id);

    void debloquerStructure(Long id);
}
