package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO {

    private Long id;
    private String nomeCargo;

    public static CargoDTO create(Cargo cargo){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cargo, CargoDTO.class);
    }
}
