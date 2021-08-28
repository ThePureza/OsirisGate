package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.SetorDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Setor;
import com.example.osirisgateapi.service.SetorService;
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
@RequestMapping("/api/v1/setores")
@RequiredArgsConstructor
@Api("API de Setores")
public class SetorController {

    private final SetorService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os setores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Setor encontrado"),
            @ApiResponse(code = 404, message = "Setor não encontrado")
    })
    public ResponseEntity get(){
        List<Setor> setores = service.getSetores();
        return ResponseEntity.ok(setores.stream().map(SetorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um setor específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Setor encontrado"),
            @ApiResponse(code = 404, message = "Setor não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do setor") Long id){
        Optional<Setor> setor = service.getSetorById(id);
        if(!setor.isPresent()){
            return new ResponseEntity("Setor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(setor.map(SetorDTO::create));
    }
    @PostMapping()
    @ApiOperation("Criar um setor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Setor salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o setor")
    })
    public ResponseEntity post(SetorDTO dto){
        try{
            Setor setor = converter(dto);
            setor = service.salvar(setor);
            return new ResponseEntity(setor, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um setor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Setor salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar o setor")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do setor") Long id, SetorDTO dto){
        if(!service.getSetorById(id).isPresent()){
            return new ResponseEntity("Setor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Setor setor = converter(dto);
            setor.setId(id);
            service.salvar(setor);
            return ResponseEntity.ok(setor);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um setor")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Setor excluído com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do setor") Long id){
        Optional<Setor> setor = service.getSetorById(id);
        if(!setor.isPresent()){
            return new ResponseEntity("Setor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(setor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Setor converter(SetorDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Setor.class);
    }
}
