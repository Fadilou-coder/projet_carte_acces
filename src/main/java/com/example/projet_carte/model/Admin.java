package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Admin extends Personne {

    protected String username;
    protected String password;

    @ManyToOne
    private Structure structure;

    public Admin(String prenom, String nom, String email, String phone,
                 String adresse, String cni, String username, String password){
        super(prenom, nom, email, phone, adresse, cni );
        this.username = username;
        this.password = password;

    }

}
