package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Lote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDTO {

    private Long id;
    private Long idSetor;
    private Long idQuadra;
    private String tipoDeLote;

    public static LoteDTO create(Lote lote){
        ModelMapper modelMapper = new ModelMapper();
        LoteDTO dto = modelMapper.map(lote, LoteDTO.class);
        assert dto.getIdSetor().equals(lote.getSetor().getId());
        assert dto.getIdQuadra().equals(lote.getQuadra().getId());
        return modelMapper.map(lote, LoteDTO.class);
    }
}
