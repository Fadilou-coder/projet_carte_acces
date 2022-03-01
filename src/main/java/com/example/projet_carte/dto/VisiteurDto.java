package com.example.projet_carte.dto;
import com.example.projet_carte.model.Visiteur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder
@Data
@AllArgsConstructor
public class VisiteurDto {

    private Long id;

    private String prenom;

    private String nom;

    private String cni;

    private String numTelephone;

    private Collection<VisiteDto> visites;

    public static VisiteurDto fromEntity(Visiteur visiteur) {
        if(visiteur == null) return null;

        return VisiteurDto.builder()
                .id(visiteur.getId())
                .prenom(visiteur.getPrenom())
                .nom(visiteur.getNom())
                .cni(visiteur.getCni())
                .numTelephone(visiteur.getNumTelephone())
                .build();

    }

    public static Visiteur toEntity(VisiteurDto visiteurDto){
        if(visiteurDto ==null) return null;

        Visiteur visiteur = new Visiteur();

        visiteur.setId(visiteurDto.getId());
        visiteur.setPrenom(visiteurDto.getPrenom());
        visiteur.setNom(visiteurDto.getNom());
        visiteur.setCni(visiteurDto.getCni());
        visiteur.setNumTelephone(visiteurDto.getNumTelephone());

        return visiteur;
    }

}
