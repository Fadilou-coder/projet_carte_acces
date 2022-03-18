package com.example.projet_carte.controller.api;

import com.example.projet_carte.dto.SuperAdminDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("SuperAdmin")
public interface SuperAdminApi{

    @GetMapping("/superAdmins")
    List<SuperAdminDto> findAll();

    @GetMapping("/superAdmin/{cni}")
    SuperAdminDto findByCni(@PathVariable String cni);

    @PostMapping("/superAdmin/create")
    SuperAdminDto save(@RequestBody SuperAdminDto superAdminDto);

    @PutMapping("/superAdmin/{id}")
    SuperAdminDto update(@RequestBody SuperAdminDto superAdminDto, @PathVariable Long id);

    @GetMapping("/superAdmin/{id}")
    SuperAdminDto findById(@PathVariable Long id);

    @DeleteMapping("/superAdmin/{id}")
    SuperAdminDto delete(@PathVariable Long id);
}
