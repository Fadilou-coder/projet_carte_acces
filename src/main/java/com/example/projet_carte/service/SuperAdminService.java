package com.example.projet_carte.service;

import com.example.projet_carte.dto.SuperAdminDto;

import java.util.List;

public interface SuperAdminService {
    List<SuperAdminDto> findAll();
    SuperAdminDto findByCni(String cni);
    SuperAdminDto save(SuperAdminDto superAdminDto);
    SuperAdminDto update(SuperAdminDto superAdminDto, Long id);
    SuperAdminDto delete(Long id);
    SuperAdminDto findById(Long id);
}
