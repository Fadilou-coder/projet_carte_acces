package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.ReferentielDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("referentiel")
public interface ReferentielApi {

    @GetMapping("/referentiels")
    List<ReferentielDto> findAll();

    @PostMapping("/referentiels/create")
    ReferentielDto save(@RequestBody ReferentielDto referentielDto);

    @PutMapping("/referentiel/{id}")
    ReferentielDto putPromo(@RequestBody ReferentielDto referentielDto, @PathVariable Long id);
}
