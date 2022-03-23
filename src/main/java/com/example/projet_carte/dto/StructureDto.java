package com.example.projet_carte.dto;

import lombok.AllArgsConstructor;
import com.example.projet_carte.model.Structure;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
public class StructureDto {

    private Long id;
    private String nomStructure;
    private Boolean isBlocked;

    @JsonIgnore
    private Collection<AdminDto> admins;

    public static StructureDto fromEntity(Structure structure) {
        if(structure == null) return null;

        return StructureDto.builder()
                .id(structure.getId())
                .nomStructure(structure.getNomStructure())
                .isBlocked(structure.isArchive())
                .build();

    }

    public static Structure toEntity(StructureDto structureDto){
        if(structureDto == null) return null;

            Structure structure = new Structure();
            structure.setId(structureDto.getId());
            structure.setNomStructure(structureDto.getNomStructure());
            structure.setArchive(structureDto.isBlocked);
            if (structureDto.getAdmins() != null){
                structure.setAdmins(structureDto.getAdmins().stream().map(AdminDto::toEntity).collect(Collectors.toList()));
            }
        return structure;
    }
}
