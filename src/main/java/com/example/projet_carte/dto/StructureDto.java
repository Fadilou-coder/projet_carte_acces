package com.example.projet_carte.dto;
import com.example.projet_carte.model.Structure;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;;
import java.util.Collection;
import java.util.Optional;

@Data
@Builder
public class StructureDto {

    private Long id;
    private String nomStructure;

    @JsonIgnore
    private Collection<AdminDto> admins;

    public static StructureDto fromEntity(Structure structure) {
        if(structure == null) return null;

        return StructureDto.builder()
                .id(structure.getId())
                .nomStructure(structure.getNomStructure())
                .build();

    }

    public static Structure toEntity(StructureDto structureDto){
        if(structureDto == null) return null;

            Structure structure = new Structure();

            structure.setId(structureDto.getId());
            structure.setNomStructure(structureDto.getNomStructure());
        return structure;
    }
}
