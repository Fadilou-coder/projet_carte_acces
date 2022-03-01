package com.example.projet_carte.dto;

import com.example.projet_carte.model.Visites;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class VisiteDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEntree;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateSortie;

    private VisiteurDto visiteur;

    private ApprenantDto apprenant;

    public static VisiteDto fromEntity(Visites visites){
        if(visites == null) return null;

        return  VisiteDto.builder()
                .id(visites.getId())
                .dateEntree(visites.getDateEntree())
                .dateSortie(visites.getDateSortie())
                .build();

    }

    public static Visites toEntity(VisiteDto visiteDto){

        if(visiteDto == null) return null;

        Visites visites = new Visites();

        visites.setId(visiteDto.getId());
        visites.setDateEntree(visiteDto.getDateEntree());
        visites.setDateSortie(visiteDto.getDateSortie());

        return visites;

    }
}
