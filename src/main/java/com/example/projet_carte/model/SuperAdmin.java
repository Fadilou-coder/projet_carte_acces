package com.example.projet_carte.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class SuperAdmin extends Personne {

    private String password;
    private String role = "SUPER_ADMIN";

    public SuperAdmin(String prenom, String nom, String email, String phone,
                      String adresse, String cni, String password){
        super(prenom, nom, email, phone, adresse, cni );
        this.password = password;

    }
}
