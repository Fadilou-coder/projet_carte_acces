package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.VisiteurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Visiteur")
public interface VisiteurApi {

    @GetMapping("/visiteurs")
    List<VisiteurDto> findAll();

    @PutMapping("/visiteur/{id}")
    VisiteurDto putVisiteur(@RequestBody VisiteurDto visiteurDto, @PathVariable Long id);
}
