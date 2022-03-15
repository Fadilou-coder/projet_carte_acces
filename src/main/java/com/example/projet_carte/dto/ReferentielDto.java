package com.example.projet_carte.dto;
        import com.example.projet_carte.model.Referentiel;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import lombok.Builder;
        import lombok.Data;;
        import java.util.Collection;

@Data
@Builder
public class ReferentielDto {

    private Long id;
    private String libelle;

    @JsonIgnore
    private Collection<ApprenantDto> apprenant;

    public static ReferentielDto fromEntity(Referentiel referentiel) {
        if(referentiel == null) return null;

        return ReferentielDto.builder()
                .id(referentiel.getId())
                .libelle(referentiel.getLibelle())
                .build();

    }

    public static Referentiel toEntity(ReferentielDto referentielDto){
        if(referentielDto == null) return null;

        Referentiel referentiel = new Referentiel();

        referentiel.setId(referentielDto.getId());
        referentiel.setLibelle(referentielDto.getLibelle());
        return referentiel;
    }
}
