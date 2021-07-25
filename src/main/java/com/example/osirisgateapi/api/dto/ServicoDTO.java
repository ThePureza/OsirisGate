package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDTO {

    private Long id;
    private String nomeServico;
    private String descricaoServico;

    public static ServicoDTO create(Servico servico){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(servico, ServicoDTO.class);
    }
}
