package com.example.API_PokemonCards.api.DTO;

import com.example.API_PokemonCards.model.entity.Carta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartaDTO {
    private Long id;
    private String nome;
    private String imageUrl;
    private Integer pokedexNumero;
    private Integer numero;
    private String raridade;
    private Long idSet;

    public static CartaDTO create(Carta carta) {
        ModelMapper modelMapper = new ModelMapper();
        CartaDTO dto = modelMapper.map(carta, CartaDTO.class);
        return dto;
    }
}
