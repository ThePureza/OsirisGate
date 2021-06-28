package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Familia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaDTO {

    private Long id;
    private String nomeFamilia;
    private String cpf;
    private String rg;
    private String sexo;
    private String dataNascimento;
    private String email;
    private String telefone;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String parentesco;

    public static FamiliaDTO create(Familia familia){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(familia, FamiliaDTO.class);
    }
}
