package com.example.projet_carte.service.impl;

import com.example.projet_carte.model.Admin;
import com.example.projet_carte.model.Apprenant;
import com.example.projet_carte.model.SuperAdmin;
import com.example.projet_carte.model.Superviseur;
import com.example.projet_carte.repository.AdminRepository;
import com.example.projet_carte.repository.ApprenantRepository;
import com.example.projet_carte.repository.SuperAdminRepository;
import com.example.projet_carte.repository.SuperviseurRepository;
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
    SuperviseurRepository superviseurRepository;
    ApprenantRepository apprenantRepository;
    PasswordEncoder passwordEncoder;
    @Override
    public Admin findUserByEmailAdmin(String username) {
        if (adminRepository.findByEmailAndArchiveFalse(username).isPresent()) {
            return adminRepository.findByEmailAndArchiveFalse(username).get();
        }
        return null;
    }

    @Override
    public SuperAdmin findUserByEmailSuperAdmin(String username) {
        if (superAdminRepository.findByEmailAndArchiveFalse(username).isPresent()) {
            return superAdminRepository.findByEmailAndArchiveFalse(username).get();
        }
        return null;
    }

    @Override
    public Superviseur findUserByEmailSuperviseur(String username) {
        if (superviseurRepository.findByEmailAndArchiveFalse(username).isPresent()){
            return superviseurRepository.findByEmailAndArchiveFalse(username).get();
        }
        return null;
    }

    @Override
    public Apprenant findUserByEmailApprenant(String username) {
        if (apprenantRepository.findByEmailAndArchiveFalse(username).isPresent()){
            return apprenantRepository.findByEmailAndArchiveFalse(username).get();
        }
        return null;
    }

}