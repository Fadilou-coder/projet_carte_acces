package com.example.projet_carte.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue
    private Long id;

    private String commentaire;

    private LocalDate date;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Apprenant apprenant;

}
