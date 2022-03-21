package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.AdminDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api("admin")
public interface AdminApi {

    @GetMapping("/admin")
    List<AdminDto> findAll();

    @PostMapping("/admin/create")
    AdminDto save(@RequestBody AdminDto adminDto) throws IOException;

    @GetMapping("/admin/{id}")
    AdminDto findById(@PathVariable  Long id);

    @DeleteMapping("/admin/{id}")
    void delete(@PathVariable Long id);

    @PutMapping("/admin/{id}")
    AdminDto put(@RequestBody AdminDto adminDto, @PathVariable Long id) throws IOException;

    @PutMapping("/admin/debloquer/{id}")
    void debloquerAdmin(@PathVariable Long id);
}
