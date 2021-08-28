package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Funeraria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunerariaDTO {

    private Long id;
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private String telefone;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String observacaoFuneraria;

    public static FunerariaDTO create(Funeraria funeraria){
        ModelMapper modelMapper = new ModelMapper();
        FunerariaDTO dto = modelMapper.map(funeraria, FunerariaDTO.class);
        return modelMapper.map(funeraria, FunerariaDTO.class);
    }
}
