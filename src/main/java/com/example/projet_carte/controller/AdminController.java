package com.example.projet_carte.controller;

import com.example.projet_carte.controller.api.AdminApi;
import com.example.projet_carte.dto.AdminDto;
import com.example.projet_carte.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController implements AdminApi {
 AdminService adminService;

    @Override
    public List<AdminDto> findAll() {
        return adminService.findAll();
    }

    @Override
    public AdminDto save(AdminDto adminDto) throws IOException {
        return adminService.save(adminDto);
    }

    @Override
    public AdminDto findById(Long id) {
        return adminService.findById(id);
    }

    @Override
    public void delete(Long id) {
         adminService.delete(id);
    }

    @Override
    public AdminDto put(AdminDto adminDto, Long id) throws IOException {
        return adminService.put(adminDto, id);
    }

}
