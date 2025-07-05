package com.example.API_PokemonCards.model.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String imageUrl;
    private Integer pokedexNumero;
    private Integer numero;
    private String raridade;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private Set set;

}
