package com.example.projet_carte.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Apprenant extends Personne {

    @Column(unique=true)
    private String code;
    private String referentiel;
    private Date dateNaissance;
    private String lieuNaissance;
    private String numTuteur;

    @Lob
    private byte[] avatar;

    @OneToMany(mappedBy = "apprenant")
    private Collection<Visites> visites;

    public Apprenant( String prenom, String nom, String email, String phone,
                      String adresse, String cni, String code, String referentiel,
                      Date dateNaissance, String lieuNaissance,String numTuteur
    ){
        super(prenom, nom, email, phone, adresse, cni );
        this.code = code;
        this.referentiel = referentiel;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.numTuteur = numTuteur;
    }
}
