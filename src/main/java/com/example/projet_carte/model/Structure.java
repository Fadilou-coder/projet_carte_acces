package com.example.projet_carte.model;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

public class Structure {

    @Id
    Long id;
    private String nomStructure;

    @OneToMany(mappedBy = "structure")
    private Collection<Admin> admins;
    
    
}
