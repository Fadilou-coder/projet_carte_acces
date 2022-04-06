package com.example.projet_carte.service;

import com.example.projet_carte.dto.SuperviseurDto;

import java.util.List;

public interface SuperviseurService {

    List<SuperviseurDto> findAll();
    SuperviseurDto findByNumPiece(String cni);
    SuperviseurDto save(SuperviseurDto superviseurDto);
    SuperviseurDto update(SuperviseurDto superviseurDto, Long id);
    SuperviseurDto delete(Long id);
    SuperviseurDto findById(Long id);
}
