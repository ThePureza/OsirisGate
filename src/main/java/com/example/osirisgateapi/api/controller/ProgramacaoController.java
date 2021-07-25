package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.ProgramacaoDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.*;
import com.example.osirisgateapi.service.FalecidoService;
import com.example.osirisgateapi.service.FamiliaService;
import com.example.osirisgateapi.service.ProgramacaoService;
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
public class ProgramacaoController {

    private final ProgramacaoService service;
    private final FamiliaService familiaService;
    private final FalecidoService falecidoService;

    @GetMapping()
    public ResponseEntity get(){
        List<Programacao> programacoes = service.getProgramacoes();
        return ResponseEntity.ok(programacoes.stream().map(ProgramacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Programacao> programacao = service.getProgramacaoById(id);
        if(!programacao.isPresent()){
            return new ResponseEntity("Programação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(programacao.map(ProgramacaoDTO::create));
    }
    @PostMapping()
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
    public ResponseEntity atualizar(@PathVariable("id") Long id, ProgramacaoDTO dto){
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
