import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import com.example.API_PokemonCards.model.entity.Serie;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SerieDTO {
    private Long id;
    private String nome;
    private String codigo;

    public static SerieDTO create(Serie serie) {
        ModelMapper modelMapper = new ModelMapper();
        SerieDTO dto = modelMapper.map(serie, SerieDTO.class);
        return dto;
    }
}