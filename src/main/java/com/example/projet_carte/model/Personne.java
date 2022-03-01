package com.example.projet_carte.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@NoArgsConstructor
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

    @Column(unique=true)
    protected String cni;

    public Personne(String prenom, String nom, String email, String phone,
                    String adresse, String cni){
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.phone = phone;
        this.adresse = adresse;
        this.cni = cni;
    }

}
