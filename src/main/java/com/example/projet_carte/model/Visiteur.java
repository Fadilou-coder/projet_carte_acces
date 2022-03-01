package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visiteur {

    @Id
    private Long id;
    private String prenom;
    private String nom;
    private String cni;
    private String numTelephone;

    @OneToMany(mappedBy = "visiteur")
    private Collection<Visites> visites;

}
