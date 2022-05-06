package com.example.projet_carte.dto;

import com.example.projet_carte.model.Superviseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SuperviseurDto {
    private Long id;
    protected String prenom;
    protected String nom;
    protected String email;
    protected String phone;
    protected String addresse;
    protected String numPiece;
    protected String password;
    protected String sexe;


    public static SuperviseurDto fromEntity(Superviseur superviseur) {

        if(superviseur == null) return null;

        return SuperviseurDto.builder()
                .id(superviseur.getId())
                .prenom(superviseur.getPrenom())
                .nom(superviseur.getNom())
                .email(superviseur.getEmail())
                .phone(superviseur.getPhone())
                .addresse(superviseur.getAdresse())
                .numPiece(superviseur.getNumPiece())
                .password(superviseur.getPassword())
                .sexe(superviseur.getSexe())
                .build();

    }

    public static Superviseur toEntity(SuperviseurDto superviseurDto){
        if(superviseurDto == null) return null;

        Superviseur superviseur =  new Superviseur();

        superviseur.setId(superviseurDto.getId());
        superviseur.setPrenom(superviseurDto.getPrenom());
        superviseur.setNom(superviseurDto.getNom());
        superviseur.setEmail(superviseurDto.getEmail());
        superviseur.setPhone(superviseurDto.getPhone());
        superviseur.setAdresse(superviseurDto.getAddresse());
        superviseur.setSexe(superviseurDto.getSexe());
        superviseur.setNumPiece(superviseurDto.getNumPiece());
        superviseur.setPassword(superviseurDto.getPassword());

        return superviseur;
    }
}
