package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Admin extends Personne {

    private String password;
    private String role = "ADMIN";


    @ManyToOne
    private Structure structure;

    public Admin(String prenom, String nom, String email, String phone,
                 String adresse, String cni, String password, Structure structure){
        super(prenom, nom, email, phone, adresse, cni );
        this.password = password;
        this.structure = structure;

    }

}
