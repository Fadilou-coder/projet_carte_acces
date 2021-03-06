package com.example.projet_carte.dto;

import com.example.projet_carte.model.SuperAdmin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SuperAdminDto {


    private Long id;
    protected String prenom;
    protected String nom;
    protected String email;
    protected String phone;
    protected String addresse;
    protected String numPiece;
    protected String sexe;
    protected String password;


    public static SuperAdminDto fromEntity(SuperAdmin superAdmin) {

        if(superAdmin == null) return null;

        return SuperAdminDto.builder()
                .id(superAdmin.getId())
                .prenom(superAdmin.getPrenom())
                .nom(superAdmin.getNom())
                .email(superAdmin.getEmail())
                .phone(superAdmin.getPhone())
                .addresse(superAdmin.getAdresse())
                .numPiece(superAdmin.getNumPiece())
                .sexe(superAdmin.getSexe())
                .password(superAdmin.getPassword())
                .build();

    }

    public static SuperAdmin toEntity(SuperAdminDto superAdminDto){
        if(superAdminDto == null) return null;

        SuperAdmin superAdmin =  new SuperAdmin();

        superAdmin.setId(superAdminDto.getId());
        superAdmin.setPrenom(superAdminDto.getPrenom());
        superAdmin.setNom(superAdminDto.getNom());
        superAdmin.setEmail(superAdminDto.getEmail());
        superAdmin.setPhone(superAdminDto.getPhone());
        superAdmin.setAdresse(superAdminDto.getAddresse());
        superAdmin.setNumPiece(superAdminDto.getNumPiece());
        superAdmin.setSexe(superAdminDto.getSexe());
        superAdmin.setPassword(superAdminDto.getPassword());

        return superAdmin;
    }
}
