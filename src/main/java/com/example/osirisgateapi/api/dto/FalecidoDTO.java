package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Falecido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FalecidoDTO {

    private Long id;
    private Long idFamilia;
    private Long idFuneraria;
    private String nomeFalecido;
    private String cpf;
    private String rg;
    private String sexo;
    private String dataNascimento;
    private String nomeMae;
    private String nomePai;
    private String dataMorte;
    private String dataSepultamento;
    private String estadoCivil;
    private String certidaoObito;
    private String medico;
    private Long idCausaDaMorte;
    private String naturalidade;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private Long idServico;
    private String prestadorServico;
    private Long idSetor;
    private Long idQuadra;
    private String observacao;

    public static FalecidoDTO create(Falecido falecido){
        ModelMapper modelMapper = new ModelMapper();
        FalecidoDTO dto = modelMapper.map(falecido, FalecidoDTO.class);
       // assert dto.getIdFamilia().equals(falecido.getFamilia().getId());
        assert dto.getIdFuneraria().equals(falecido.getFuneraria().getId());
        assert dto.getIdCausaDaMorte().equals(falecido.getCausaDaMorte().getId());
        assert dto.getIdServico().equals(falecido.getServico().getId());
        assert dto.getIdSetor().equals(falecido.getSetor().getId());
        assert dto.getIdQuadra().equals(falecido.getQuadra().getId());
        return modelMapper.map(falecido, FalecidoDTO.class);
    }
}
