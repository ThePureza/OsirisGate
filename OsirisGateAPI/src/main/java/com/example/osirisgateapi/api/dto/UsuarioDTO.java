package com.example.osirisgateapi.api.dto;

import com.example.osirisgateapi.model.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nomeUsuario;
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
    private Long idCargo;

    public static UsuarioDTO create(Usuario usuario){
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        assert dto.getIdCargo().equals(usuario.getCargo().getId());
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
