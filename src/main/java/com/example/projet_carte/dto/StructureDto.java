package com.example.projet_carte.dto;
import com.example.projet_carte.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;;
import java.util.Collection;

@AllArgsConstructor
@Data
public class StructureDto {

    private String nomStructure;

    private Collection<Admin> admins;
}
