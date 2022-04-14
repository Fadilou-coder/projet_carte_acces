package com.example.projet_carte.service;


import com.example.projet_carte.model.*;

public interface ApplicationService {

    Admin findUserByEmailAdmin(String username);

    SuperAdmin findUserByEmailSuperAdmin(String username);

    Superviseur findUserByEmailSuperviseur(String username);

    Apprenant findUserByEmailApprenant(String username);
}
