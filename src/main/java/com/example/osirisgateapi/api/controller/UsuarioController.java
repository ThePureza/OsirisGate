package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.UsuarioDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.*;
import com.example.osirisgateapi.service.CargoService;
import com.example.osirisgateapi.service.UsuarioService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Api("API de Usuários")
public class UsuarioController {

    private final UsuarioService service;
    private final CargoService cargoService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os usuários")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário encontrado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado")
    })
    public ResponseEntity get(){
        List<Usuario> usuarios = service.getUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um usuário específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário encontrado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do usuário") Long id){
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if(!usuario.isPresent()){
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usuario.map(UsuarioDTO::create));
    }

    @PostMapping()
    @ApiOperation("Criar um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o usuário")
    })
    public ResponseEntity post(UsuarioDTO dto){
        try{
            Usuario usuario = converter(dto);
            usuario = service.salvar(usuario);
            return new ResponseEntity(usuario, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar o usuário")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do usuário") Long id, UsuarioDTO dto){
        if(!service.getUsuarioById(id).isPresent()){
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Usuario usuario = converter(dto);
            usuario.setId(id);
            service.salvar(usuario);
            return ResponseEntity.ok(usuario);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário excluído com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do usuário") Long id){
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if(!usuario.isPresent()){
            return new ResponseEntity("Usuário não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(usuario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Usuario converter(UsuarioDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        if (dto.getIdCargo() != null) {
            Optional<Cargo> cargo = cargoService.getCargoById(dto.getIdCargo());
            if (!cargo.isPresent()) {
                usuario.setCargo(null);
            } else {
                usuario.setCargo(cargo.get());
            }
        }
        return usuario;
    }
}

