package com.example.API_PokemonCards.api.DTO;

import com.example.API_PokemonCards.model.entity.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SetDTO {
    private Long id;
    private String nome;
    private String codigo;
    private String simboloImagemUrl;
    private Integer totalCartas;
    private Long idSerie;

    public static SetDTO create(Set set) {
        ModelMapper modelMapper = new ModelMapper();
        SetDTO dto = modelMapper.map(set, SetDTO.class);
        return dto;
    }
}
