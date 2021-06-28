package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.CausaDaMorte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CausaDaMorteDTO {
    private Long id;
    private String nomeCausaDaMorte;
    private String descricao;

    public static CausaDaMorteDTO create(CausaDaMorte causaDaMorte){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(causaDaMorte, CausaDaMorteDTO.class);
    }
}
