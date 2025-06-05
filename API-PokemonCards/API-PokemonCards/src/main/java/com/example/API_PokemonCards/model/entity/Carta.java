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
    private String basico;
    private Integer hp;
    private String tipo;
    private String imageUrl;
    private Integer pokedexNumero;
    private String tipoPokemon;
    private double altura;
    private double peso;
    private String ataque1;
    private String descricaoAtaque1;
    private Integer quantidadeEnergiaAtaque1;
    private Integer danoAtaque1;
    private String ataque2;
    private String descricaoAtaque2;
    private Integer quantidadeEnergiaAtaque2;
    private Integer danoAtaque2;
    private String fraqueza;
    private Integer danoFraqueza;
    private String resistencia;
    private Integer danoResistencia;
    private Integer recuo;
    private String ilustrador;
    private Integer numero;
    private String raridade;
    private String descricao;
    private Integer anoLancamento;
    private String regraExPokemon;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private Set set;

}
