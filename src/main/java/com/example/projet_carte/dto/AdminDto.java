package com.example.projet_carte.dto;

import com.example.projet_carte.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AdminDto {

    private Long id;
    protected String prenom;
    protected String nom;
    protected String email;
    protected String phone;
    protected String addresse;
    protected String typePiece;
    protected String numPiece;
    protected String password;
    protected String sexe;
    private boolean isbloqued;

    public static AdminDto fromEntity(Admin admin) {

        if(admin == null) return null;

        return AdminDto.builder()
                .id(admin.getId())
                .prenom(admin.getPrenom())
                .nom(admin.getNom())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .typePiece(admin.getTypePiece())
                .addresse(admin.getAdresse())
                .numPiece(admin.getNumPiece())
                .password(admin.getPassword())
                .sexe(admin.getSexe())
                .isbloqued(admin.isArchive())
                .build();

    }

    public static Admin toEntity(AdminDto adminDto){
        if(adminDto == null) return null;

        Admin admin =  new Admin();

        admin.setId(adminDto.getId());
        admin.setPrenom(adminDto.getPrenom());
        admin.setNom(adminDto.getNom());
        admin.setEmail(adminDto.getEmail());
        admin.setPhone(adminDto.getPhone());
        admin.setAdresse(adminDto.getAddresse());
        admin.setTypePiece(adminDto.getTypePiece());
        admin.setNumPiece(adminDto.getNumPiece());
        admin.setPassword(adminDto.getPassword());
        admin.setSexe(adminDto.getSexe());
        admin.setArchive(adminDto.isIsbloqued());

        return admin;
    }
}
