package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.ProgramacaoDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.*;
import com.example.osirisgateapi.service.FalecidoService;
import com.example.osirisgateapi.service.FamiliaService;
import com.example.osirisgateapi.service.ProgramacaoService;
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
@RequestMapping("/api/v1/programacoes")
@RequiredArgsConstructor
@Api("API de Programações")
public class ProgramacaoController {

    private final ProgramacaoService service;
    private final FamiliaService familiaService;
    private final FalecidoService falecidoService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todas as programações")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Programação encontrada"),
            @ApiResponse(code = 404, message = "Programação não encontrada")
    })
    public ResponseEntity get(){
        List<Programacao> programacoes = service.getProgramacoes();
        return ResponseEntity.ok(programacoes.stream().map(ProgramacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma programação específica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Programação encontrada"),
            @ApiResponse(code = 404, message = "Programação não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id da programação") Long id){
        Optional<Programacao> programacao = service.getProgramacaoById(id);
        if(!programacao.isPresent()){
            return new ResponseEntity("Programação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(programacao.map(ProgramacaoDTO::create));
    }
    @PostMapping()
    @ApiOperation("Criar uma programação")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Programação salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a programação")
    })
    public ResponseEntity post(ProgramacaoDTO dto){
        try{
            Programacao programacao = converter(dto);
            programacao = service.salvar(programacao);
            return new ResponseEntity(programacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar uma programação")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Programação salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar a programação")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id da programação") Long id, ProgramacaoDTO dto){
        if(!service.getProgramacaoById(id).isPresent()){
            return new ResponseEntity("Programação não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Programacao programacao = converter(dto);
            programacao.setId(id);
            service.salvar(programacao);
            return ResponseEntity.ok(programacao);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir uma programação")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Programação excluída com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id da programação") Long id){
        Optional<Programacao> programacao = service.getProgramacaoById(id);
        if(!programacao.isPresent()){
            return new ResponseEntity("Programação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(programacao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Programacao converter(ProgramacaoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Programacao programacao =  modelMapper.map(dto, Programacao.class);
        if (dto.getIdFamilia() != null){
            Optional<Familia> familia = familiaService.getFamiliaById(dto.getIdFamilia());
            if(!familia.isPresent()){
                programacao.setFamilia(null);
            } else {
                programacao.setFamilia(familia.get());
            }
        }
        if (dto.getIdFalecido() != null){
            Optional<Falecido> falecido = falecidoService.getFalecidoById(dto.getIdFalecido());
            if(!falecido.isPresent()){
                programacao.setFalecido(null);
            } else {
                programacao.setFalecido(falecido.get());
            }
        }
        return programacao;
    }
}
