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
    @Column(unique=true)
    private String cni;
    @Column(unique=true)
    private String numTelephone;

    @OneToMany(mappedBy = "visiteur")
    private Collection<Visites> visites;

}
