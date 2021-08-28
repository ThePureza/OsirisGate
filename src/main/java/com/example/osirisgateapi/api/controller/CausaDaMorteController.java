package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.CausaDaMorteDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.CausaDaMorte;
import com.example.osirisgateapi.service.CausaDaMorteService;
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
@RequestMapping("/api/v1/causaDaMortes")
@RequiredArgsConstructor
@Api("API de Causas da Morte")
public class CausaDaMorteController {

    private final CausaDaMorteService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todas as causas da morte")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Causa da morte encontrada"),
            @ApiResponse(code = 404, message = "Causa da morte não encontrada")
    })
    public ResponseEntity get(){
        List<CausaDaMorte> causaDaMortes = service.getCausaDaMortes();
        return ResponseEntity.ok(causaDaMortes.stream().map(CausaDaMorteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma causa da morte específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Causa da morte encontrada"),
            @ApiResponse(code = 404, message = "Causa da morte não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da causa da morte") Long id){
        Optional<CausaDaMorte> causaDaMorte = service.getCausaDaMorteById(id);
        if(!causaDaMorte.isPresent()){
            return new ResponseEntity("Causa da morte não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(causaDaMorte.map(CausaDaMorteDTO::create));
    }

    @PostMapping()
    @ApiOperation("Criar uma causa da morte")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Causa da morte salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a causa da morte")
    })
    public ResponseEntity post(CausaDaMorteDTO dto){
        try{
            CausaDaMorte causaDaMorte = converter(dto);
            causaDaMorte = service.salvar(causaDaMorte);
            return new ResponseEntity(causaDaMorte, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Criar uma causa da morte")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Causa da morte salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar a causa da morte")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id da causa da morte")Long id, CausaDaMorteDTO dto){
        if(!service.getCausaDaMorteById(id).isPresent()){
            return new ResponseEntity("Causa da morte não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            CausaDaMorte causaDaMorte = converter(dto);
            causaDaMorte.setId(id);
            service.salvar(causaDaMorte);
            return ResponseEntity.ok(causaDaMorte);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma causa da morte")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Causa da morte excluída com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id da causa da morte") Long id){
        Optional<CausaDaMorte> causaDaMorte = service.getCausaDaMorteById(id);
        if(!causaDaMorte.isPresent()){
            return new ResponseEntity("Causa da morte não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(causaDaMorte.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public CausaDaMorte converter(CausaDaMorteDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, CausaDaMorte.class);
    }
}
