package com.example.projet_carte.service;

import com.example.projet_carte.dto.AdminDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    List<AdminDto> findAll();

    AdminDto save(AdminDto adminDto) throws IOException;

    AdminDto findById(Long id);

    AdminDto put(AdminDto adminDto, Long id) throws IOException;

    void delete(Long id);
}
