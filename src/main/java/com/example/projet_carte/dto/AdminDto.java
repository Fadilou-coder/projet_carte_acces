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
    protected String cni;
    protected String username;
    protected String password;
    private StructureDto structure;

    public static AdminDto fromEntity(Admin admin) {

        if(admin == null) return null;

        return AdminDto.builder()
                .id(admin.getId())
                .prenom(admin.getPrenom())
                .nom(admin.getNom())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .addresse(admin.getAdresse())
                .cni(admin.getCni())
                .username(admin.getUsername())
                .password(admin.getPassword())
                .structure(StructureDto.fromEntity(admin.getStructure()))
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
        admin.setCni(adminDto.getCni());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setStructure(StructureDto.toEntity(adminDto.getStructure()));

        return admin;
    }
}
