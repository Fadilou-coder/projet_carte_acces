package com.example.projet_carte.dto;

import com.example.projet_carte.model.Commentaire;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class CommentaireDto {

    private Long id;

    private String commentaire;

    private LocalDate date;

    private ApprenantDto apprenant;

    public static CommentaireDto fromEntity(Commentaire commentaire) {
        return  CommentaireDto.builder()
                .id(commentaire.getId())
                .commentaire(commentaire.getCommentaire())
                .date(commentaire.getDate())
                .apprenant(ApprenantDto.fromEntity(commentaire.getApprenant()))
                .build();
    }

    public static Commentaire toEntity(CommentaireDto commentaireDto) {

        if(commentaireDto == null){
            return null;
        }

        Commentaire commentaire = new Commentaire();

        commentaire.setId(commentaireDto.getId());
        commentaire.setCommentaire(commentaireDto.getCommentaire());
        commentaire.setDate(commentaireDto.getDate());
        commentaire.setApprenant(ApprenantDto.toEntity(commentaireDto.getApprenant()));

        return commentaire;
    }

}
