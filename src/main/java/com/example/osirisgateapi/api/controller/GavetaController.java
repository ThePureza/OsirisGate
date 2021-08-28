package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.GavetaDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Gaveta;
import com.example.osirisgateapi.model.entity.Ossuario;
import com.example.osirisgateapi.service.GavetaService;
import com.example.osirisgateapi.service.OssuarioService;
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
@RequestMapping("/api/v1/gavetas")
@RequiredArgsConstructor
@Api("API de Gavetas")
public class GavetaController {

    private final GavetaService service;
    private final OssuarioService ossuarioService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todas as gavetas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gaveta encontrada"),
            @ApiResponse(code = 404, message = "Gaveta não encontrada")
    })
    public ResponseEntity get(){
        List<Gaveta> gavetas = service.getGavetas();
        return ResponseEntity.ok(gavetas.stream().map(GavetaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma gaveta específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Gaveta encontrada"),
            @ApiResponse(code = 404, message = "Gaveta não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da gaveta") Long id){
        Optional<Gaveta> gaveta = service.getGavetaById(id);
        if(!gaveta.isPresent()){
            return new ResponseEntity("Gaveta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(gaveta.map(GavetaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Criar uma gaveta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Gaveta salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a gaveta")
    })
    public ResponseEntity post(GavetaDTO dto){
        try{
            Gaveta gaveta = converter(dto);
            gaveta = service.salvar(gaveta);
            return new ResponseEntity(gaveta, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar uma gaveta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Gaveta salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar a gaveta")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id da gaveta") Long id, GavetaDTO dto){
        if(!service.getGavetaById(id).isPresent()){
            return new ResponseEntity("Gaveta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Gaveta gaveta = converter(dto);
            gaveta.setId(id);
            service.salvar(gaveta);
            return ResponseEntity.ok(gaveta);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma gaveta")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Gaveta excluída com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id da gaveta") Long id){
        Optional<Gaveta> gaveta = service.getGavetaById(id);
        if(!gaveta.isPresent()){
            return new ResponseEntity("Gaveta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(gaveta.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Gaveta converter(GavetaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Gaveta gaveta = modelMapper.map(dto, Gaveta.class);
        if (dto.getIdOssuario() != null){
            Optional<Ossuario> ossuario = ossuarioService.getOssuarioById(dto.getIdOssuario());
            if(!ossuario.isPresent()){
                gaveta.setOssuario(null);
            } else {
                gaveta.setOssuario(ossuario.get());
            }
        }
        return gaveta;
    }
}
