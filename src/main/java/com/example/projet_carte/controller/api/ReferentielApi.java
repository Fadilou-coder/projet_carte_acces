package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.ReferentielDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Api("referentiel")
public interface ReferentielApi {

    @GetMapping("/referentiels")
    List<ReferentielDto> findAll();
}
