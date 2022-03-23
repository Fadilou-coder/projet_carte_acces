package com.example.projet_carte.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
public class Promo {

    public Promo(String libelle, String annee) {
        this.libelle = libelle;
        this.annee = annee;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String libelle;

    private String annee;

    @OneToMany(mappedBy = "promo")
    private Collection<Apprenant> apprenants;

    private boolean archive = false;
}
