package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Structure {

    @Id
    Long id;
    private String nomStructure;

    @OneToMany(mappedBy = "structure")
    private Collection<Admin> admins;


}
