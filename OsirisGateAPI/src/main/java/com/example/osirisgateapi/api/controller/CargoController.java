package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.CargoDTO;
import com.example.osirisgateapi.api.dto.UsuarioDTO;
import com.example.osirisgateapi.model.entity.Cargo;
import com.example.osirisgateapi.model.entity.Usuario;
import com.example.osirisgateapi.service.CargoService;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.service.UsuarioService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cargos")
@RequiredArgsConstructor
@Api("API de Cargos")
public class CargoController {

    private final CargoService service;
    private final UsuarioService usuarioService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os cargos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cargo encontrado"),
            @ApiResponse(code = 404, message = "Cargo não encontrado")
    })
    public ResponseEntity get(){
        List<Cargo> cargos = service.getCargos();
        return ResponseEntity.ok(cargos.stream().map(CargoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cargo específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cargo encontrado"),
            @ApiResponse(code = 404, message = "Cargo não encontrado")
    })
    public ResponseEntity get(@PathVariable("id")  @ApiParam("Id do cargo") Long id){
        Optional<Cargo> cargo = service.getCargoById(id);
        if(!cargo.isPresent()){
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cargo.map(CargoDTO::create));
    }

    @GetMapping("{id}/usuarios")
    @ApiOperation("Obter detalhes de usuários de um cargo específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cargo encontrado"),
            @ApiResponse(code = 404, message = "Cargo não encontrado")
    })
    public ResponseEntity getUsuarios(@PathVariable("id")  @ApiParam("Id do cargo") Long id) {
        Optional<Cargo> cargo = service.getCargoById(id);
        if (!cargo.isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        List<Usuario> usuarios = usuarioService.getUsuariosByCargo(cargo);
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    @ApiOperation("Criar um cargo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cargo salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o cargo")
    })
    public ResponseEntity post(CargoDTO dto){
        try{
            Cargo cargo = converter(dto);
            cargo = service.salvar(cargo);
            return new ResponseEntity(cargo, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um cargo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cargo salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar o cargo")
    })
    public ResponseEntity atualizar(@PathVariable("id")  @ApiParam("Id do cargo") Long id, CargoDTO dto){
        if(!service.getCargoById(id).isPresent()){
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Cargo cargo = converter(dto);
            cargo.setId(id);
            service.salvar(cargo);
            return ResponseEntity.ok(cargo);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um cargo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cargo excluído com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id")  @ApiParam("Id do cargo") Long id){
        Optional<Cargo> cargo = service.getCargoById(id);
        if(!cargo.isPresent()){
            return new ResponseEntity("Cargo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(cargo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cargo converter(CargoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cargo.class);
    }
}
