package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visiteur {

    @Id
    @GeneratedValue
    private Long id;
    private String prenom;
    private String nom;
    private String typePiece;
    private String numPiece;
    @Column(unique=true)
    private String numTelephone;

    @OneToMany(mappedBy = "visiteur")
    private Collection<Visites> visites;

    public Visiteur(String prenom, String nom, String typePiece, String numPiece, String numTelephone) {
        this.prenom = prenom;
        this.nom = nom;
        this.typePiece = typePiece;
        this.numPiece = numPiece;
        this.numTelephone = numTelephone;
    }
}
