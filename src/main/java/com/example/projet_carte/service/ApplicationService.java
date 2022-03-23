package com.example.projet_carte.service;


import com.example.projet_carte.model.*;

public interface ApplicationService {

    Admin findUserByEmailAdmin(String username);

    SuperAdmin findUserByEmailSuperAdmin(String username);
}
