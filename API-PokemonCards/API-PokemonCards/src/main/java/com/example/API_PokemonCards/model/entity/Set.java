package com.example.API_PokemonCards.model.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String codigo;
    private String simboloImagemUrl;
    private Integer totalCartas;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;
}
