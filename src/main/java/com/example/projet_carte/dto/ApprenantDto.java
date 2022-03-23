package com.example.projet_carte.dto;

import com.example.projet_carte.model.Apprenant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Builder
@Data
@AllArgsConstructor
public class ApprenantDto {

    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String phone;
    private String addresse;
    private String cni;
    private String code;
    private ReferentielDto referentiel;
    private PromoDto promo;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String numTuteur;
    private byte[] avatar;
    private Collection<VisiteDto> visites;

    public static ApprenantDto fromEntity(Apprenant apprenant){
        if(apprenant == null){
            return  null;
        }
        return ApprenantDto.builder()
                .id(apprenant.getId())
                .prenom(apprenant.getPrenom())
                .nom(apprenant.getNom())
                .email(apprenant.getEmail())
                .phone(apprenant.getPhone())
                .addresse(apprenant.getAdresse())
                .cni(apprenant.getCni())
                .code(apprenant.getCode())
                .referentiel(ReferentielDto.fromEntity(apprenant.getReferentiel()))
                .promo(PromoDto.fromEntity(apprenant.getPromo()))
                .dateNaissance(apprenant.getDateNaissance())
                .lieuNaissance(apprenant.getLieuNaissance())
                .numTuteur(apprenant.getNumTuteur())
                .avatar(apprenant.getAvatar())
                .build();

    }

    public static Apprenant toEntity(ApprenantDto apprenantDto){
        if(apprenantDto == null){
            return null;
        }
        Apprenant apprenant = new Apprenant();
        apprenant.setId(apprenantDto.getId());
        apprenant.setPrenom(apprenantDto.getPrenom());
        apprenant.setNom(apprenantDto.getNom());
        apprenant.setEmail(apprenantDto.getEmail());
        apprenant.setPhone(apprenantDto.getPhone());
        apprenant.setAdresse(apprenantDto.getAddresse());
        apprenant.setCni(apprenantDto.getCni());
        apprenant.setCode(apprenantDto.getCode());
        apprenant.setReferentiel(ReferentielDto.toEntity(apprenantDto.getReferentiel()));
        apprenant.setPromo(PromoDto.toEntity(apprenantDto.getPromo()));
        apprenant.setDateNaissance(apprenantDto.getDateNaissance());
        apprenant.setLieuNaissance(apprenantDto.getLieuNaissance());
        apprenant.setNumTuteur(apprenantDto.getNumTuteur());
        apprenant.setAvatar(apprenantDto.getAvatar());

        return apprenant;
    }
}
