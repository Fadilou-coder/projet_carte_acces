package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.SuperAdminApi;
import com.example.projet_carte.dto.SuperAdminDto;
import com.example.projet_carte.service.SuperAdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SuperAdminController implements SuperAdminApi {

    private SuperAdminService superAdminService;

    @Override
    public List<SuperAdminDto> findAll() {
        return superAdminService.findAll();
    }

    @Override
    public SuperAdminDto findByCni(String cni) {
        return superAdminService.findByCni(cni);
    }

    @Override
    public SuperAdminDto save(SuperAdminDto superAdminDto) {
        return superAdminService.save(superAdminDto);
    }

    @Override
    public SuperAdminDto update(SuperAdminDto superAdminDto, Long id) {
        return superAdminService.update(superAdminDto, id);
    }

    @Override
    public SuperAdminDto delete(Long id) {
        return superAdminService.delete(id);
    }
}
