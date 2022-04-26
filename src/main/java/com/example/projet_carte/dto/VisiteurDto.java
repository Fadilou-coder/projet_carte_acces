package com.example.projet_carte.dto;
import com.example.projet_carte.model.Visiteur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
public class VisiteurDto {

    private Long id;

    private String prenom;

    private String nom;

    private String typePiece;

    private String numPiece;

    private String sexe;

    private String numTelephone;

    private Collection<VisiteDto> visites;

    public static VisiteurDto fromEntity(Visiteur visiteur) {
        if(visiteur == null) return null;

        return VisiteurDto.builder()
                .id(visiteur.getId())
                .prenom(visiteur.getPrenom())
                .nom(visiteur.getNom())
                .typePiece(visiteur.getTypePiece())
                .numPiece(visiteur.getNumPiece())
                .sexe(visiteur.getSexe())
                .numTelephone(visiteur.getNumTelephone())
                .build();

    }

    public static Visiteur toEntity(VisiteurDto visiteurDto){
        if(visiteurDto ==null) return null;

        Visiteur visiteur = new Visiteur();

        visiteur.setId(visiteurDto.getId());
        visiteur.setPrenom(visiteurDto.getPrenom());
        visiteur.setNom(visiteurDto.getNom());
        visiteur.setTypePiece(visiteurDto.getTypePiece());
        visiteur.setNumPiece(visiteurDto.getNumPiece());
        visiteur.setSexe(visiteurDto.getSexe());
        visiteur.setNumTelephone(visiteurDto.getNumTelephone());
        if (visiteurDto.getVisites() != null)
            visiteur.setVisites(visiteurDto.getVisites().stream().map(VisiteDto::toEntity).collect(Collectors.toList()));

        return visiteur;
    }

}
