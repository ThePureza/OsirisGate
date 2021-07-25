package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Gaveta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GavetaDTO {

    private Long id;
    private String numeroGaveta;
    private String descricaoGaveta;
    private Long idOssuario;
    private String nomeOssuario;

    public static GavetaDTO create(Gaveta gaveta){
        ModelMapper modelMapper = new ModelMapper();
        GavetaDTO dto = modelMapper.map(gaveta, GavetaDTO.class);
        assert dto.getIdOssuario().equals(gaveta.getOssuario().getId());
        assert dto.getNomeOssuario().equals(gaveta.getOssuario().getNomeOssuario());
        return modelMapper.map(gaveta, GavetaDTO.class);
    }
}
