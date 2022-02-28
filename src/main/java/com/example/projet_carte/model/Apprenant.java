package com.example.projet_carte.model;

import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;

public class Apprenant extends Personne {
    
    private String code;
    private String referentiel;
    private Date dateNaissance;
    private String lieuNaissance;
    private String NumTuteur;

    @OneToMany(mappedBy = "apprenant")
    private Collection<Visites> visites;
}
