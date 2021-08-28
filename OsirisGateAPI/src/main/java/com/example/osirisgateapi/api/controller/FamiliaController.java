package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.FamiliaDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Familia;
import com.example.osirisgateapi.service.FamiliaService;
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
@RequestMapping("/api/v1/familias")
@RequiredArgsConstructor
@Api("API de Famílias")
public class FamiliaController {

    private final FamiliaService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todas as famílias")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Família encontrada"),
            @ApiResponse(code = 404, message = "Família não encontrada")
    })
    public ResponseEntity get(){
        List<Familia> familias = service.getFamilias();
        return ResponseEntity.ok(familias.stream().map(FamiliaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma família específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Família encontrada"),
            @ApiResponse(code = 404, message = "Família não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da família") Long id){
        Optional<Familia> familia = service.getFamiliaById(id);
        if(!familia.isPresent()){
            return new ResponseEntity("Família não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(familia.map(FamiliaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Criar uma família")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Família salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a família")
    })
    public ResponseEntity post(FamiliaDTO dto){
        try{
            Familia familia = converter(dto);
            familia = service.salvar(familia);
            return new ResponseEntity(familia, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar uma família")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Família salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar a família")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do cargo") Long id, FamiliaDTO dto){
        if(!service.getFamiliaById(id).isPresent()){
            return new ResponseEntity("Família não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Familia familia = converter(dto);
            familia.setId(id);
            service.salvar(familia);
            return ResponseEntity.ok(familia);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma família")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Família excluída com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id da família") Long id){
        Optional<Familia> familia = service.getFamiliaById(id);
        if(!familia.isPresent()){
            return new ResponseEntity("Família não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(familia.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Familia converter(FamiliaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Familia.class);
    }
}
