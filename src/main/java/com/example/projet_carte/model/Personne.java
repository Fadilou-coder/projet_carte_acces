package com.example.projet_carte.model;

import javax.persistence.Id;

public class Personne {

    @Id
    private Long id;
    protected String prenom;
    protected String nom;
    protected String email;
    protected String phone;
    protected String address;
    protected String cni;
    
}
