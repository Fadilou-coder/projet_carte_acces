package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visites {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = true)
    private LocalDateTime dateEntree = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime dateSortie;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Visiteur visiteur;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Apprenant apprenant;

    public Visites(Visiteur visiteur, Apprenant apprenant) {
        this.visiteur = visiteur;
        this.apprenant = apprenant;
    }
}
