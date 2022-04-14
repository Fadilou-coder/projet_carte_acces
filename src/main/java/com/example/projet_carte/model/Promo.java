package com.example.projet_carte.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
public class Promo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String libelle;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @OneToMany(mappedBy = "promo")
    private Collection<Apprenant> apprenants;

    private boolean archive = false;

    public Promo(String libelle, LocalDate dateDebut, LocalDate dateFin) {
        this.libelle = libelle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
}
