package com.example.projet_carte.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
public class Apprenant extends Personne {


    private String code;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String numTuteur;

    @Lob
    private byte[] avatar;

    @OneToMany(mappedBy = "apprenant")
    private Collection<Visites> visites;

    @ManyToOne
    private Referentiel referentiel;

    @ManyToOne
    private Promo promo;

    public Apprenant( String prenom, String nom, String email, String password, String phone,
                      String adresse, String typePiece, String cni, String code, Referentiel referentiel,
                      LocalDate dateNaissance, String lieuNaissance,String numTuteur, Promo promo
    ){
        super(prenom, nom, email, phone, adresse, typePiece, cni, password, "APPRENANT" );
        this.code = code;
        this.referentiel = referentiel;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.numTuteur = numTuteur;
        this.promo = promo;
    }

}
