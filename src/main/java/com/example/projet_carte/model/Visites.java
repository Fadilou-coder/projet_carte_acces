package com.example.projet_carte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visites {

    @Id
    private Long id;

    private LocalDate dateEntree;

    private LocalDate dateSortie;

    @ManyToOne
    private Visiteur visiteur;

    @ManyToOne
    private Apprenant apprenant;

}
