package com.example.projet_carte.model;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class Visites {

    @Id
    private Long id;

    private LocalDate dateEntree;
    private LocalDate dateSortie;

    @ManyToOne
    private Visiteur visiteur;

    @ManyToOne
    private Apprenant apprenant;
    
}
