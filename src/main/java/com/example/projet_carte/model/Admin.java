package com.example.projet_carte.model;

import javax.persistence.ManyToOne;

public class Admin extends Personne {

    protected String username;
    protected String password;

    @ManyToOne
    private Structure structure;

    
}
