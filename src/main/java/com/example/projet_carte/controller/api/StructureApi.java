package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.StructureDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api("structure")
public interface StructureApi {
    @GetMapping("/structures")
    List<StructureDto> findAll();

    @PostMapping("/structures/create")
    StructureDto save(@RequestBody StructureDto structureDto) throws IOException;

    @GetMapping("/structures/{id}")
    StructureDto findById(@PathVariable Long id);

    @DeleteMapping("/structures/{id}")
    void delete(@PathVariable Long id);

    @PutMapping("/structures/{id}")
    StructureDto put(@RequestBody StructureDto structureDto, @PathVariable Long id) throws IOException;

    @PutMapping("/structures/debloquer/{id}")
    void debloquerStructure(@PathVariable Long id);
}
