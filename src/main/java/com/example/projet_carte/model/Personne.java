package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Personne {

    @Id
    @GeneratedValue
    private Long id;

    protected String prenom;

    protected String nom;

    @Column(unique=true)
    protected String email;

    @Column(unique=true)
    protected String phone;

    protected String adresse;

    protected String typePiece;

    protected String numPiece;

    protected String sexe;

    private String password;

    private String role = "ADMIN";

    private boolean archive = false;

    public Personne(String prenom, String nom, String email, String phone,
                    String adresse, String typePiece, String numPiece, String sexe, String password, String role){
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.phone = phone;
        this.adresse = adresse;
        this.numPiece = numPiece;
        this.typePiece = typePiece;
        this.sexe = sexe;
        this.password = password;
        this.role = role;
    }

}
