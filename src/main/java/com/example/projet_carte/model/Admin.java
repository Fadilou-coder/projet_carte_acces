package com.example.projet_carte.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Admin extends Personne {

    public Admin(String prenom, String nom, String email, String phone,
                 String adresse, String typePiece, String numPiece, String password){
        super(prenom, nom, email, phone, adresse, typePiece, numPiece, password, "ADMIN" );
    }
}
