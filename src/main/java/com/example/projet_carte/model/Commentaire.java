package com.example.projet_carte.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue
    private Long id;

    private String commentaire;

    @ManyToOne
    private Apprenant apprenant;

}
