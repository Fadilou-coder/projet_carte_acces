package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.SuperviseurApi;
import com.example.projet_carte.dto.SuperviseurDto;
import com.example.projet_carte.service.SuperviseurService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SuperviseurController implements SuperviseurApi {

    SuperviseurService superviseurService;

    @Override
    public List<SuperviseurDto> findAll() {
        return superviseurService.findAll();
    }

    @Override
    public SuperviseurDto save(SuperviseurDto superviseurDto) {
        return superviseurService.save(superviseurDto);
    }

    @Override
    public SuperviseurDto update(SuperviseurDto superviseurDto, Long id) {
        return superviseurService.update(superviseurDto, id);
    }

    @Override
    public SuperviseurDto findById(Long id) {
        return superviseurService.findById(id);
    }

    @Override
    public SuperviseurDto delete(Long id) {
        return superviseurService.delete(id);
    }
}
