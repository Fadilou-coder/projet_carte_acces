package com.example.projet_carte.model;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

public class Visiteur {

    @Id
    private Long id;
    private String prenom;
    private String nom;
    private String cni;
    private String numTelephone;

    @OneToMany(mappedBy = "visiteur")
    private Collection<Visites> visites;
    
}
