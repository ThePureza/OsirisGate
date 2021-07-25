package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Ossuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssuarioDTO {

    private Long id;
    private String nomeOssuario;
    private String descricaoOssuario;
    private String localizacaoOssuario;

    public static OssuarioDTO create(Ossuario ossuario){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ossuario, OssuarioDTO.class);
    }
}
