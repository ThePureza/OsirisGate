package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Quadra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuadraDTO {

    private Long id;
    private String numeroQuadra;
    private Long idSetor;

    public static QuadraDTO create(Quadra quadra){
        ModelMapper modelMapper = new ModelMapper();
        QuadraDTO dto = modelMapper.map(quadra, QuadraDTO.class);
        assert dto.getIdSetor().equals(quadra.getSetor().getId());
        return modelMapper.map(quadra, QuadraDTO.class);
    }
}
