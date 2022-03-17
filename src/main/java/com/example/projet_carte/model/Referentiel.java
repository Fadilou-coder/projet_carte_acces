package com.example.projet_carte.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Data
@NoArgsConstructor
public class Referentiel {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)

    private String libelle;

    public Referentiel(String libelle) {
        this.libelle = libelle;
    }

    @OneToMany(mappedBy = "referentiel")
    private Collection<Apprenant> apprenants;
}
