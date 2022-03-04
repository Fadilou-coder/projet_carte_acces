package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Structure {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String nomStructure;

    public Structure(String nomStructure) {
        this.nomStructure = nomStructure;
    }

    @OneToMany(mappedBy = "structure")
    private Collection<Admin> admins;

    private boolean archive = false;


}
