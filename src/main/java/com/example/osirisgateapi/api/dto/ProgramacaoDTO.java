package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Programacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramacaoDTO {

    private Long id;
    private String dataVelorio;
    private String horaVelorio;
    private String dataSepultamento;
    private String horaSepultamento;
    private String local;
    private Long idFamilia;
    private Long idFalecido;

    public static ProgramacaoDTO create(Programacao programacao){
        ModelMapper modelMapper = new ModelMapper();
        ProgramacaoDTO dto = modelMapper.map(programacao, ProgramacaoDTO.class);
        assert dto.getIdFamilia().equals(programacao.getFamilia().getId());
        assert dto.getIdFalecido().equals(programacao.getFalecido().getId());
        return modelMapper.map(programacao, ProgramacaoDTO.class);
    }
}
