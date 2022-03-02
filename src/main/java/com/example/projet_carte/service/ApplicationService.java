package com.example.projet_carte.service;


import com.example.projet_carte.model.*;

public interface ApplicationService {

    Admin findUserByUsernameAdmin(String username);

    SuperAdmin findUserByUsernameSuperAdmin(String username);
}
