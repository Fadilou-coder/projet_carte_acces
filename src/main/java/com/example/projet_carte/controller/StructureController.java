package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.StructureApi;
import com.example.projet_carte.dto.StructureDto;
import com.example.projet_carte.service.StructureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class StructureController implements StructureApi {

    StructureService structureService;

    @Override
    public List<StructureDto> findAll() {
        return structureService.findAll();
    }

    @Override
    public StructureDto save(StructureDto structureDto) throws IOException {
        return structureService.save(structureDto);
    }

    @Override
    public StructureDto findById(Long id) {
        return structureService.findById(id);
    }

    @Override
    public void delete(Long id) {
        structureService.delete(id);
    }

    @Override
    public StructureDto put(StructureDto structureDto, Long id) throws IOException {
        return structureService.put(structureDto, id);
    }

    @Override
    public void debloquerStructure(Long id) {
        structureService.debloquerStructure(id);
    }
}
