package com.example.projet_carte.dto;

import com.example.projet_carte.model.Promo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
public class PromoDto {
    private Long id;
    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @JsonIgnore
    private Collection<ApprenantDto> apprenant;

    public static PromoDto fromEntity(Promo promo){
        if(promo == null) return null;

        return PromoDto.builder()
                .id(promo.getId())
                .libelle(promo.getLibelle())
                .dateDebut(promo.getDateDebut())
                .dateFin(promo.getDateFin())
                .build();
    }

    public static Promo toEntity(PromoDto promoDto){
        if(promoDto == null) return null;

        Promo promo = new Promo();

        promo.setId(promoDto.getId());
        promo.setLibelle(promoDto.getLibelle());
        promo.setDateDebut(promoDto.getDateDebut());
        promo.setDateFin(promoDto.getDateFin());
        return promo;
    }
}
