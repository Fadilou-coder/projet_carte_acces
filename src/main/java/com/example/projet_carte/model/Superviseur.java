package com.example.projet_carte.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Superviseur extends Personne {
    public Superviseur(String prenom, String nom, String email, String phone,
                       String adresse, String typePiece, String numPiece, String sexe, String password){
        super(prenom, nom, email, phone, adresse, typePiece, numPiece, sexe, password, "SUPER_ADMIN" );

    }
}
