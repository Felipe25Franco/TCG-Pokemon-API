package com.example.animeAllStar_back.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


import com.example.animeAllStar_back.model.entity.Usuario;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class UsuarioDTO {
    private Long id;
    private String login;
    private String cpf;
    private String senha;
    private String senhaRepeticao;
    private boolean admin;

    public static UsuarioDTO create(Usuario usuario) {
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);

        return dto;
    }
}
