package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.FunerariaDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Funeraria;
import com.example.osirisgateapi.service.FunerariaService;
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
@RequestMapping("/api/v1/funerarias")
@RequiredArgsConstructor
@Api("API de Funerárias")
public class FunerariaController {

    private final FunerariaService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todas as funerárias")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funerária encontrada"),
            @ApiResponse(code = 404, message = "Funerária não encontrada")
    })
    public ResponseEntity get(){
        List<Funeraria> funerarias = service.getFunerarias();
        return ResponseEntity.ok(funerarias.stream().map(FunerariaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma funerária específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Funerária encontrada"),
            @ApiResponse(code = 404, message = "Funerária não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da funerária") Long id){
        Optional<Funeraria> funeraria = service.getFunerariaById(id);
        if(!funeraria.isPresent()){
            return new ResponseEntity("Funerária não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funeraria.map(FunerariaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Criar uma funerária")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Funerária salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a funerária")
    })
    public ResponseEntity post(FunerariaDTO dto){
        try{
            Funeraria funeraria = converter(dto);
            funeraria = service.salvar(funeraria);
            return new ResponseEntity(funeraria, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar uma funerária")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Funerária salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar a funerária")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id da funerária") Long id, FunerariaDTO dto){
        if(!service.getFunerariaById(id).isPresent()){
            return new ResponseEntity("Funerária não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Funeraria funeraria = converter(dto);
            funeraria.setId(id);
            service.salvar(funeraria);
            return ResponseEntity.ok(funeraria);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma funerária")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Funerária excluída com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id da funerária") Long id){
        Optional<Funeraria> funeraria = service.getFunerariaById(id);
        if(!funeraria.isPresent()){
            return new ResponseEntity("Funerária não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funeraria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funeraria converter(FunerariaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Funeraria funeraria =  modelMapper.map(dto, Funeraria.class);
        return modelMapper.map(dto, Funeraria.class);
    }
}
