package com.example.projet_carte.service.impl;

import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.SuperAdmin;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.service.ApplicationService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class ApplicationServiceImpl implements ApplicationService {
    AdminRepository adminRepository;
    SuperAdminRepository superAdminRepository;
    PasswordEncoder passwordEncoder;
    @Override
    public Admin findUserByUsernameAdmin(String username) {
        if (adminRepository.findByUsernameAndArchiveFalse(username).isPresent()) {
            return adminRepository.findByUsernameAndArchiveFalse(username).get();
        }
        return null;
    }

    @Override
    public SuperAdmin findUserByUsernameSuperAdmin(String username) {
        if (superAdminRepository.findByUsernameAndArchiveFalse(username).isPresent()) {
            return superAdminRepository.findByUsernameAndArchiveFalse(username).get();
        }
        return null;
    }

}