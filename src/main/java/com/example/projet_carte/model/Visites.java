package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visites {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dateEntree = LocalDate.now();

    private LocalDate dateSortie;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Visiteur visiteur;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Apprenant apprenant;

}
