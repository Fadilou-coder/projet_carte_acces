package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.SuperviseurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Superviseur")
public interface SuperviseurApi {
    @GetMapping("/superviseurs")
    List<SuperviseurDto> findAll();

    @PostMapping("/superviseur/create")
    SuperviseurDto save(@RequestBody SuperviseurDto superviseurDto);

    @PutMapping("/superviseur/{id}")
    SuperviseurDto update(@RequestBody SuperviseurDto superviseurDto, @PathVariable Long id);

    @GetMapping("/superviseur/{id}")
    SuperviseurDto findById(@PathVariable Long id);

    @DeleteMapping("/superviseur/{id}")
    SuperviseurDto delete(@PathVariable Long id);
}
