package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Setor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetorDTO {

    private Long id;
    private String nomeSetor;
    private String descricaoSetor;
    private String localizacaoSetor;

    public static SetorDTO create(Setor setor){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(setor, SetorDTO.class);
    }
}
